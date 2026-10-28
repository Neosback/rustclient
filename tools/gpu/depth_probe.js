// Bounded diagnostic for the shared world WGSL and portable deferred opaque depth recipe.
// This does not run the Rust Wasm application or reproduce its CPU face sorter.
export async function checkDeferredDepth(device, module, check) {
 const visibility=GPUShaderStage.VERTEX|GPUShaderStage.FRAGMENT;
 const layout=device.createBindGroupLayout({entries:[
  {binding:0,visibility,buffer:{type:'uniform',minBindingSize:672}},
  {binding:1,visibility:GPUShaderStage.FRAGMENT,texture:{viewDimension:'2d-array'}},
  {binding:2,visibility:GPUShaderStage.FRAGMENT,sampler:{type:'filtering'}},
  {binding:3,visibility:GPUShaderStage.VERTEX,buffer:{type:'uniform',minBindingSize:4096}}
 ]});
 const pipelineLayout=device.createPipelineLayout({bindGroupLayouts:[layout]});
 const pipeline=(writesDepth,writesColor)=>device.createRenderPipelineAsync({
  layout:pipelineLayout,
  vertex:{module,entryPoint:'vs_main',buffers:[{arrayStride:24,attributes:[
   {shaderLocation:0,offset:0,format:'sint16x4'},{shaderLocation:1,offset:8,format:'uint32'},
   {shaderLocation:2,offset:12,format:'uint16x4'},{shaderLocation:3,offset:20,format:'uint32'}]}]},
  fragment:{module,entryPoint:'fs_main',targets:[{format:'rgba8unorm',writeMask:writesColor?GPUColorWrite.ALL:0}]},
  primitive:{cullMode:'back'},depthStencil:{format:'depth32float',depthWriteEnabled:writesDepth,depthCompare:'greater'}
 });
 const [ordinary,colorOnly,depthOnly]=await Promise.all([pipeline(true,true),pipeline(false,true),pipeline(true,false)]);
 const uniform=device.createBuffer({size:672,usage:GPUBufferUsage.UNIFORM|GPUBufferUsage.COPY_DST});
 const bytes=new ArrayBuffer(672),floats=new Float32Array(bytes),ints=new Uint32Array(bytes);
 floats.set([1/1024,0,0,0,0,1/1024,0,0,0,0,1/1024,0,0,0,0,1]);ints[17]=3;floats[18]=1;
 device.queue.writeBuffer(uniform,0,bytes);
 const animations=device.createBuffer({size:4096,usage:GPUBufferUsage.UNIFORM});
 const texture=device.createTexture({size:[1,1,1],format:'rgba8unorm',usage:GPUTextureUsage.TEXTURE_BINDING});
 const sampler=device.createSampler({minFilter:'linear',magFilter:'linear'});
 const bind=device.createBindGroup({layout,entries:[{binding:0,resource:{buffer:uniform}},
  {binding:1,resource:texture.createView({dimension:'2d-array'})},{binding:2,resource:sampler},
  {binding:3,resource:{buffer:animations}}]});
 const red=(7<<7)|64,blue=(42<<10)|(7<<7)|64,green=(21<<10)|(7<<7)|64;
 const raw=new ArrayBuffer(12*24),view=new DataView(raw);
 let vertex=0;
 for(const [z,hsl,priority] of [[400,blue,9],[800,red,0],[600,green,0],[900,green,0]]) {
  for(const [x,y] of [[-900,-900],[900,-900],[0,900]]) {
   const offset=vertex++*24;
   for(const [i,value] of [x,y,z,priority].entries())view.setInt16(offset+i*2,value,true);
   view.setUint32(offset+8,hsl,true);
  }
 }
 const vertices=device.createBuffer({size:raw.byteLength,usage:GPUBufferUsage.VERTEX|GPUBufferUsage.COPY_DST});
 device.queue.writeBuffer(vertices,0,raw);
 const order=new Uint32Array([0,1,2,3,4,5,3,4,5,0,1,2,6,7,8,9,10,11]);
 const indices=device.createBuffer({size:order.byteLength,usage:GPUBufferUsage.INDEX|GPUBufferUsage.COPY_DST});
 device.queue.writeBuffer(indices,0,order);
 async function capture(steps) {
  const target=device.createTexture({size:[32,32],format:'rgba8unorm',usage:GPUTextureUsage.RENDER_ATTACHMENT|GPUTextureUsage.COPY_SRC});
  const depth=device.createTexture({size:[32,32],format:'depth32float',usage:GPUTextureUsage.RENDER_ATTACHMENT});
  const readback=device.createBuffer({size:256*32,usage:GPUBufferUsage.COPY_DST|GPUBufferUsage.MAP_READ});
  const encoder=device.createCommandEncoder(),pass=encoder.beginRenderPass({
   colorAttachments:[{view:target.createView(),loadOp:'clear',storeOp:'store',clearValue:[0,0,0,1]}],
   depthStencilAttachment:{view:depth.createView(),depthLoadOp:'clear',depthStoreOp:'store',depthClearValue:0}
  });
  pass.setBindGroup(0,bind);pass.setVertexBuffer(0,vertices);pass.setIndexBuffer(indices,'uint32');
  for(const [pipeline,first,count] of steps){pass.setPipeline(pipeline);pass.drawIndexed(count,1,first);}
  pass.end();encoder.copyTextureToBuffer({texture:target},{buffer:readback,bytesPerRow:256},[32,32]);device.queue.submit([encoder.finish()]);
  await readback.mapAsync(GPUMapMode.READ);const rgba=[...new Uint8Array(readback.getMappedRange(),16*256+16*4,4)];
  readback.unmap();readback.destroy();depth.destroy();target.destroy();return rgba;
 }
 const original=await capture([[ordinary,0,6]]);
 const deferredSteps=[[colorOnly,6,6],[depthOnly,6,6]];
 const deferred=await capture(deferredSteps);
 check('deferred actor face priority',original[0]>original[2]&&deferred[2]>deferred[0],{ordinary:original,deferred});
 const later=await capture([...deferredSteps,[ordinary,12,3]]);
 check('deferred actor writes nearest depth',later.every((v,i)=>v===deferred[i]),{actual:later,expected:deferred});
 const wall=await capture([[ordinary,15,3],...deferredSteps]);
 check('deferred actor retains wall depth test',wall[1]>wall[0]&&wall[1]>wall[2],{actual:wall});
 const repeated=await capture(deferredSteps);
 check('deferred pipeline repeat',repeated.every((v,i)=>v===deferred[i]));
 for(const resource of [uniform,animations,texture,vertices,indices])resource.destroy();
}
