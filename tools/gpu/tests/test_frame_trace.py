"""Scoring validity contracts; no screenshot/appearance assertions."""
import importlib.util
import json
from pathlib import Path
import tempfile
import unittest

spec = importlib.util.spec_from_file_location('frame_trace', Path(__file__).parents[1] / 'frame_trace.py')
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)

class TraceContracts(unittest.TestCase):
    def report(self, rows):
        with tempfile.TemporaryDirectory() as folder:
            path = Path(folder) / 'trace.jsonl'
            path.write_text(''.join(json.dumps(row) + '\n' for row in rows))
            return module.summarize(path, 0, 1, 60)

    def frames(self, presented=True, available=True):
        rows = [dict(type='renderer', refresh_millihertz=60000, focused=True, occluded=False)]
        for i in range(60):
            rows.append(dict(type='frame', at_ns=(i + 1)*1_000_000_000//61, presented=presented,
                             epoch=1, frame=i, gap_ns=16_666_667, cpu_work_upper_ns=1_000_000,
                             frame_wall_ns=2_000_000, gpu_available=available,
                             vertex_upload_bytes=0, dynamic_upload_bytes=0, ui_raster_passes=0))
            rows.append(dict(type='gpu', epoch=1, frame=i, ns=1_000_000))
        rows += [dict(type='gpu_complete', dropped=0), dict(type='trace_end', valid=True, dropped=0)]
        return rows

    def test_empty_and_unpresented_traces_are_invalid_without_index_errors(self):
        for rows in [[], self.frames(presented=False)]:
            self.assertFalse(self.report(rows)['passed'])
            self.assertEqual(self.report(rows)['gate_status']['trace_complete'], 'invalid')

    def test_valid_trace_and_explicit_unavailable_gpu(self):
        self.assertTrue(self.report(self.frames())['passed'])
        report=self.report(self.frames(available=False))
        self.assertEqual(report['gate_status']['gpu_p99'], 'unavailable')
        self.assertFalse(report['passed'])

    def test_focus_loss_and_overflow_invalidate_scoring(self):
        rows=self.frames()+[dict(type='event', at_ns=500_000_000, description='Focused(false)')]
        self.assertEqual(self.report(rows)['gate_status']['rate'], 'invalid')
        rows=self.frames()
        rows[-1]['dropped']=1
        self.assertFalse(self.report(rows)['gates']['trace_complete'])

    def test_focus_lost_before_warmup_stays_invalid(self):
        rows=self.frames()
        rows[0]['focused']=False
        self.assertFalse(self.report(rows)['gates']['trace_complete'])

    def test_missing_gpu_completion_is_invalid_not_a_performance_failure(self):
        rows=[row for row in self.frames() if row['type']!='gpu_complete']
        self.assertEqual(self.report(rows)['gate_status']['gpu_p99'], 'invalid')

class LocationLatencyContracts(unittest.TestCase):
    def test_group_waits_for_a_frame_carrying_its_publication_and_includes_coalescing(self):
        records=[dict(phase='post_cycle_observation',scene_generation=2,source_generation=1,at_ns=100_000_000),
                 dict(phase='post_cycle_observation',scene_generation=2,source_generation=2,at_ns=125_000_000),
                 dict(phase='published',scene_generation=2,source_generation=2,publication=3,at_ns=140_000_000)]
        frames=[dict(presented=True,scene_generation=2,location_publication=2,at_ns=145_000_000),
                dict(presented=True,scene_generation=2,location_publication=3,at_ns=155_000_000)]
        stages=[dict(stage=7,at_ns=110_000_000,ns=20_000_000)]
        report=module.location_latency(records,frames,stages,0,1)
        self.assertEqual(report['samples'][0]['observations'],2)
        self.assertEqual(report['post_cycle_p99_ms'],55)
        self.assertEqual(report['commit_script_upper_p99_ms'],65)
        self.assertEqual(report['gate'],'fail')

    def test_renderer_reconstruction_does_not_alias_publication_numbers(self):
        records=[];frames=[]
        for epoch,offset in [(1,0),(2,100_000_000)]:
            records += [dict(phase='post_cycle_observation',epoch=epoch,scene_generation=2,source_generation=1,at_ns=offset+10_000_000),dict(phase='published',epoch=epoch,scene_generation=2,source_generation=1,publication=1,at_ns=offset+20_000_000)]
            frames.append(dict(presented=True,epoch=epoch,scene_generation=2,location_publication=1,at_ns=offset+30_000_000))
        report=module.location_latency(records,frames,[],0,1)
        self.assertEqual(len(report['samples']),2)

    def test_same_renderer_scenery_replacement_may_restart_its_local_counter(self):
        records=[];frames=[];stages=[]
        for generation,offset in [(1,0),(2,100_000_000)]:
            records += [dict(phase='post_cycle_observation',epoch=1,scene_generation=2,source_generation=generation,at_ns=offset+10_000_000),dict(phase='published',epoch=1,scene_generation=2,source_generation=generation,publication=1,at_ns=offset+20_000_000)]
            frames.append(dict(presented=True,epoch=1,scene_generation=2,location_publication=1,at_ns=offset+30_000_000))
            stages.append(dict(stage=7,at_ns=offset+15_000_000,ns=10_000_000))
        report=module.location_latency(records,frames,stages,0,1)
        self.assertEqual(len(report['samples']),2)
        self.assertEqual(report['unresolved_observations'],0)
        self.assertEqual(report['gate'],'pass')

    def test_unpublished_observation_cannot_be_hidden_by_an_earlier_success(self):
        records=[dict(phase='post_cycle_observation',scene_generation=2,source_generation=1,at_ns=10_000_000),
                 dict(phase='published',scene_generation=2,source_generation=1,publication=1,at_ns=20_000_000),
                 dict(phase='post_cycle_observation',scene_generation=2,source_generation=2,at_ns=40_000_000)]
        frames=[dict(presented=True,scene_generation=2,location_publication=1,at_ns=30_000_000)]
        stages=[dict(stage=7,at_ns=15_000_000,ns=10_000_000)]
        report=module.location_latency(records,frames,stages,0,1)
        self.assertEqual(report['unresolved_observations'],1)
        self.assertEqual(report['gate'],'invalid')

    def test_no_updates_does_not_pass_the_latency_gate(self):
        self.assertEqual(module.location_latency([],[],[],0,1)['gate'],'unavailable')

if __name__=='__main__':
    unittest.main()
