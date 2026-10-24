import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

/** Research normalization: repair leftover type references and remove malformed annotation noise. */
public final class NormalizeJar {
    static int removed;
    static boolean noise(String descriptor) {
        if (descriptor.equals("Ljavax/inject/Named;")) { removed++; return true; }
        return false;
    }
    static String original(List<AnnotationNode> visible,List<AnnotationNode> invisible,String fallback) {
        List<AnnotationNode> all=new ArrayList<>();if(visible!=null)all.addAll(visible);if(invisible!=null)all.addAll(invisible);
        for(AnnotationNode a:all)if(a.desc.equals("Lnet/runelite/mapping/ObfuscatedName;"))
            for(int i=0;i<a.values.size();i+=2)if(a.values.get(i).equals("value"))return (String)a.values.get(i+1);
        return fallback;
    }
    static String member(Map<String,ClassNode> nodes,Remapper types,String owner,String name,String desc,boolean field,Set<String> seen) {
        if(!seen.add(owner))return null;ClassNode n=nodes.get(owner);if(n==null)return null;
        if(field) {
            for(FieldNode f:n.fields)if(f.desc.equals(desc)&&f.name.equals(name))return name;
            for(FieldNode f:n.fields)if(f.desc.equals(desc)&&original(f.visibleAnnotations,f.invisibleAnnotations,f.name).equals(name))return f.name;
        } else {
            for(MethodNode m:n.methods)if(m.desc.equals(desc)&&m.name.equals(name))return name;
            for(MethodNode m:n.methods)if(m.desc.equals(desc)&&original(m.visibleAnnotations,m.invisibleAnnotations,m.name).equals(name))return m.name;
        }
        String result=member(nodes,types,n.superName,name,desc,field,seen);if(result!=null)return result;
        for(String i:n.interfaces){result=member(nodes,types,i,name,desc,field,seen);if(result!=null)return result;}
        return null;
    }
    public static void main(String[] args) throws Exception {
        Map<String,String> names=new HashMap<>();
        Map<String,ClassNode> nodes=new HashMap<>();
        try(JarFile input=new JarFile(args[0])) {
            for(JarEntry entry:Collections.list(input.entries())) {
                if(!entry.getName().endsWith(".class"))continue;
                ClassNode node=new ClassNode();new ClassReader(input.getInputStream(entry)).accept(node,ClassReader.SKIP_CODE|ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);
                nodes.put(node.name,node);
                List<AnnotationNode> annotations=new ArrayList<>();
                if(node.visibleAnnotations!=null)annotations.addAll(node.visibleAnnotations);
                if(node.invisibleAnnotations!=null)annotations.addAll(node.invisibleAnnotations);
                for(AnnotationNode a:annotations)if(a.desc.equals("Lnet/runelite/mapping/ObfuscatedName;")) {
                    for(int i=0;i<a.values.size();i+=2)if(a.values.get(i).equals("value"))names.put((String)a.values.get(i+1),node.name);
                }
            }
            try(JarOutputStream out=new JarOutputStream(Files.newOutputStream(Path.of(args[1])))) {
                for(JarEntry entry:Collections.list(input.entries())) {
                    byte[] bytes=input.getInputStream(entry).readAllBytes();
                    String name=entry.getName();
                    if(name.endsWith(".class")) {
                        ClassReader reader=new ClassReader(bytes);ClassWriter writer=new ClassWriter(0);
                        SimpleRemapper types=new SimpleRemapper(names);
                        Remapper repair=new SimpleRemapper(names) {
                            public String mapMethodName(String owner,String name,String desc) {
                                String mapped=member(nodes,types,types.mapType(owner),name,types.mapMethodDesc(desc),false,new HashSet<>());
                                return mapped==null?name:mapped;
                            }
                            public String mapFieldName(String owner,String name,String desc) {
                                String mapped=member(nodes,types,types.mapType(owner),name,types.mapDesc(desc),true,new HashSet<>());
                                return mapped==null?name:mapped;
                            }
                        };
                        ClassVisitor remapper=new ClassRemapper(writer,repair);
                        ClassVisitor strip=new ClassVisitor(Opcodes.ASM9,remapper) {
                            public AnnotationVisitor visitAnnotation(String d,boolean v){return noise(d)?null:super.visitAnnotation(d,v);}
                            public FieldVisitor visitField(int a,String n,String d,String s,Object value){
                                return new FieldVisitor(Opcodes.ASM9,super.visitField(a,n,d,s,value)) {
                                    public AnnotationVisitor visitAnnotation(String d,boolean v){return noise(d)?null:super.visitAnnotation(d,v);}
                                };
                            }
                            public MethodVisitor visitMethod(int a,String n,String d,String s,String[] e){
                                return new MethodVisitor(Opcodes.ASM9,super.visitMethod(a,n,d,s,e)) {
                                    public AnnotationVisitor visitAnnotation(String d,boolean v){return noise(d)?null:super.visitAnnotation(d,v);}
                                    public AnnotationVisitor visitParameterAnnotation(int p,String d,boolean v){return noise(d)?null:super.visitParameterAnnotation(p,d,v);}
                                };
                            }
                        };
                        reader.accept(strip,0);bytes=writer.toByteArray();name=reader.getClassName()+".class";
                    }
                    JarEntry output=new JarEntry(name);output.setTime(0);out.putNextEntry(output);out.write(bytes);out.closeEntry();
                }
            }
        }
        System.out.println("type mappings="+names.size()+" removed javax.inject.Named annotations="+removed);
    }
}
