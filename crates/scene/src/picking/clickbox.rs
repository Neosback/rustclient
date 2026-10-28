//! Revision-client menu selection differs deliberately from pixel/depth visibility.
//! client.method1631/2264/2290: model boxes, five-pixel face bounds, all overlapping tags.
use crate::{ModelView,SceneGeometry,SceneVisibility,Result,SceneError};
use rustclient_model::{ModelPoint,WorldTarget};
#[derive(Debug,Default,Clone,Copy,PartialEq,Eq)]
pub enum PickPrecision { #[default] ProjectedFaces, BoundsOnly }
#[derive(Debug,Clone,Copy)]
pub struct PickBounds { center:ModelPoint, half:[i32;3] }
impl PickBounds {
    /// Bounds include model origin; horizontal half extents have the source minimum of 32.
    /// # Errors
    /// Rejects extents outside the supported integer projection domain.
    pub fn new(origin:ModelPoint,points:&[ModelPoint])->Result<Self>{
        let mut low=[origin.x,origin.y,origin.z];let mut high=low;
        for point in points {for (axis,value) in [point.x,point.y,point.z].into_iter().enumerate(){low[axis]=low[axis].min(value);high[axis]=high[axis].max(value);}}
        let center=std::array::from_fn(|i|(i64::from(low[i])+i64::from(high[i]))/2);
        let mut half=[0;3];for i in 0..3 {half[i]=i32::try_from((i64::from(high[i])-i64::from(low[i])+1)/2).map_err(|_|SceneError("clickbox extent overflow".into()))?;}
        half[0]=half[0].max(32);half[2]=half[2].max(32);
        Ok(Self{center:ModelPoint::new(i32::try_from(center[0]).map_err(|_|SceneError("clickbox center overflow".into()))?,i32::try_from(center[1]).map_err(|_|SceneError("clickbox center overflow".into()))?,i32::try_from(center[2]).map_err(|_|SceneError("clickbox center overflow".into()))?),half})
    }
    fn fallback(geometry:&SceneGeometry,span:&std::ops::Range<usize>)->Option<Self>{
        let vertices=geometry.vertices.get(span.clone())?;let first=vertices.first()?.position;
        let origin=ModelPoint::new(i32::from(first[0]),i32::from(first[1]),i32::from(first[2]));
        // Manual/legacy fixtures may omit full source bounds. Production ranges supply them.
        let mut low=[first[0],first[1],first[2]];let mut high=low;
        for vertex in vertices {for axis in 0..3{low[axis]=low[axis].min(vertex.position[axis]);high[axis]=high[axis].max(vertex.position[axis]);}}
        Self::new(origin,&[ModelPoint::new(i32::from(low[0]),i32::from(low[1]),i32::from(low[2])),ModelPoint::new(i32::from(high[0]),i32::from(high[1]),i32::from(high[2]))]).ok()
    }
    #[expect(clippy::cast_precision_loss,reason="bounded fine-unit scene clickboxes")]
    fn depth(self,projection:&PickProjection,precision:PickPrecision)->Option<i32>{
        let [start,end]=projection.ray;
        let center=[self.center.x,self.center.y,self.center.z];let eye=projection.view.eye();
        let mut near=0_f32;let mut far=1_f32;
        for axis in 0..3 {
            let center=(i64::from(center[axis])-i64::from(eye[axis])) as f32;
            let half=self.half[axis] as f32+if precision==PickPrecision::BoundsOnly&&axis!=1{8.}else{0.};
            let delta=end[axis]-start[axis];
            if delta.abs()<1.0E-9 {if start[axis]<center-half||start[axis]>center+half{return None;}}
            else{let a=(center-half-start[axis])/delta;let b=(center+half-start[axis])/delta;near=near.max(a.min(b));far=far.min(a.max(b));if near>far{return None;}}
        }
        let point=std::array::from_fn::<_,3,_>(|i|start[i]+(end[i]-start[i])*near);
        Some(depth_int(point.iter().map(|value|value*value).sum()))
    }
}
#[derive(Clone,Copy)]
pub struct PickProjection { view:ModelView,zoom:i32,center:[i32;2],pointer:[i32;2],ray:[[f32;3];2] }
impl PickProjection {
    #[must_use]
    #[expect(clippy::cast_precision_loss,reason="source bounded screen and fine-unit projection")]
    pub fn new(view:ModelView,zoom:i32,center:[i32;2],pointer:[i32;2])->Option<Self>{
        if zoom<=0{return None;}
        let [ys,yc,ps,pc]=view.coefficients();
        let ray=[50,13312].map(|depth|{
            let x=((i64::from(pointer[0])-i64::from(center[0]))*i64::from(depth)/i64::from(zoom)) as f32;
            let y=((i64::from(pointer[1])-i64::from(center[1]))*i64::from(depth)/i64::from(zoom)) as f32;
            let forward=pc*depth as f32-ps*y;
            [depth_int(x*yc-ys*forward) as f32,depth_int(y*pc+ps*depth as f32) as f32,depth_int(ys*x+yc*forward) as f32]
        });Some(Self{view,zoom,center,pointer,ray})
    }
    fn face(self,points:[ModelPoint;3])->Option<i32>{
        let projected=points.map(|point|self.view.project(point));
        let mut screen=[[0_i64;2];3];
        for (index,point) in projected.iter().enumerate(){
            let depth=depth_int(point[2]);if depth<50{return None;}
            screen[index]=std::array::from_fn(|axis|i64::from(self.center[axis])+i64::from(depth_int(point[axis]))*i64::from(self.zoom)/i64::from(depth));
        }
        for axis in 0..2{
            let mouse=i64::from(self.pointer[axis]);
            if screen.iter().all(|point|mouse+5<point[axis])||screen.iter().all(|point|mouse-5>point[axis]){return None;}
        }
        Some(depth_int(projected.iter().map(|point|point.iter().map(|value|value*value).sum::<f32>()).sum::<f32>()/3.))
    }
}
#[expect(clippy::cast_possible_truncation,reason="Java saturating float-to-int clickbox depth conversion")]
fn depth_int(value:f32)->i32{value as i32}
#[derive(Clone,Copy,Debug)]
pub struct ClickHit {pub target:WorldTarget,pub depth:i32}
/// Bounded immutable model broad phase retained alongside the existing terrain triangle index.
pub(super) struct Clickboxes { boxes:Vec<Option<PickBounds>> }
impl Clickboxes{
    pub fn new(geometry:&SceneGeometry)->Self{Self{boxes:geometry.picks.iter().map(|range|range.bounds.or_else(||PickBounds::fallback(geometry,&range.vertices))).collect()}}
    pub fn bytes(&self)->usize{self.boxes.capacity()*size_of::<Option<PickBounds>>()}
    pub fn query(&self,geometry:&SceneGeometry,projection:PickProjection,visibility:&SceneVisibility)->Vec<ClickHit>{
        let mut hits=Vec::<ClickHit>::new();
        for (range,bounds) in geometry.picks.iter().zip(&self.boxes){
            let Some(vertices)=geometry.vertices.get(range.vertices.clone()).filter(|vertices|!vertices.is_empty())else{continue;};
            if !visibility.shows_vertex(&vertices[0]){continue;}
            let Some(box_depth)=bounds.and_then(|bounds|bounds.depth(&projection,range.precision))else{continue;};
            let depth=if range.precision==PickPrecision::BoundsOnly{box_depth}else{
                let Some(depth)=vertices.chunks_exact(3).filter_map(|triangle|projection.face([triangle[0],triangle[1],triangle[2]].map(|vertex|ModelPoint::new(i32::from(vertex.position[0]),i32::from(vertex.position[1]),i32::from(vertex.position[2]))))).min()else{continue;};depth
            };
            if let Some(hit)=hits.iter_mut().find(|hit|hit.target==range.target){hit.depth=hit.depth.min(depth);}else{hits.push(ClickHit{target:range.target,depth});}
        }
        hits.sort_by_key(|hit|hit.depth);hits
    }
}
#[must_use]
pub fn pick_targets(geometry:&SceneGeometry,projection:PickProjection,visibility:&SceneVisibility)->Vec<ClickHit>{Clickboxes::new(geometry).query(geometry,projection,visibility)}
