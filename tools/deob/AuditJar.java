import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import com.google.gson.*;
import org.objectweb.asm.*;
import org.objectweb.asm.util.CheckClassAdapter;

/** Structural evidence only; this does not execute the client or prove runtime parity. */
public final class AuditJar {
    static final class Item {
        String name, original, parent, nestHost;
        List<String> interfaces = new ArrayList<>(), nestMembers = new ArrayList<>();
        Set<String> methods = new TreeSet<>(), fields = new TreeSet<>();
        int dynamicConstants, dynamicCalls;
        Map<String,Set<String>> references = new TreeMap<>();
    }
    static final List<Handle> handles = new ArrayList<>();
    static final List<Handle> calls = new ArrayList<>();
    static final List<String> errors = new ArrayList<>();
    static void reference(Item item, String where, String type) {
        if(type==null)return;
        Set<String> refs=item.references.computeIfAbsent(where,k->new TreeSet<>());
        if(type.startsWith("[")||type.startsWith("(")||(type.startsWith("L")&&type.endsWith(";"))) {
            java.util.regex.Matcher m=java.util.regex.Pattern.compile("L([^;]+);").matcher(type);
            while(m.find())refs.add(m.group(1));
        } else refs.add(type);
    }
    static void constant(Object v, Item item) {
        if(v instanceof Type)reference(item,"constant",((Type)v).getDescriptor());
        if(v instanceof Handle) {
            Handle h=(Handle)v;reference(item,"bootstrap",h.getOwner());reference(item,"bootstrap",h.getDesc());
        }
        if (v instanceof Handle) handles.add((Handle)v);
        if (v instanceof ConstantDynamic) {
            item.dynamicConstants++;
            reference(item,"dynamic constant",((ConstantDynamic)v).getDescriptor());
            ConstantDynamic c=(ConstantDynamic)v;
            constant(c.getBootstrapMethod(),item);
            for(int i=0;i<c.getBootstrapMethodArgumentCount();i++) constant(c.getBootstrapMethodArgument(i),item);
        }
    }
    public static void main(String[] args) throws Exception {
        Map<String,Item> items=new TreeMap<>();
        try(JarFile jar=new JarFile(args[0])) {
            for(Enumeration<JarEntry> it=jar.entries();it.hasMoreElements();) {
                JarEntry e=it.nextElement(); if(!e.getName().endsWith(".class")) continue;
                byte[] data=jar.getInputStream(e).readAllBytes(); Item item=new Item();
                ClassReader cr=new ClassReader(data);
                cr.accept(new ClassVisitor(Opcodes.ASM9) {
                    public void visit(int v,int access,String name,String sig,String parent,String[] interfaces) {
                        item.name=name;item.original=name;item.parent=parent;
                        if(interfaces!=null)item.interfaces.addAll(Arrays.asList(interfaces));
                        reference(item,"parent",parent);for(String i:item.interfaces)reference(item,"interface",i);
                    }
                    public AnnotationVisitor visitAnnotation(String desc,boolean visible) {
                        if(!desc.equals("Lnet/runelite/mapping/ObfuscatedName;"))return null;
                        return new AnnotationVisitor(Opcodes.ASM9) {
                            public void visit(String name,Object value) {if(name.equals("value"))item.original=(String)value;}
                        };
                    }
                    public void visitNestHost(String name){item.nestHost=name;}
                    public void visitNestMember(String name){item.nestMembers.add(name);}
                    public FieldVisitor visitField(int access,String name,String desc,String sig,Object value){
                        item.fields.add(name+":"+desc);reference(item,"field "+name,desc);return null;
                    }
                    public MethodVisitor visitMethod(int access,String name,String desc,String sig,String[] exceptions){
                        item.methods.add(name+desc);reference(item,"method "+name,desc);
                        return new MethodVisitor(Opcodes.ASM9) {
                            public void visitLdcInsn(Object value){constant(value,item);}
                            public void visitTypeInsn(int opcode,String type){reference(item,"instruction "+name,type);}
                            public void visitMultiANewArrayInsn(String desc,int dims){reference(item,"array "+name,desc);}
                            public void visitMethodInsn(int op,String owner,String called,String desc,boolean itf){calls.add(new Handle(op==Opcodes.INVOKESTATIC?Opcodes.H_INVOKESTATIC:Opcodes.H_INVOKEVIRTUAL,owner,called,desc,itf));reference(item,"call "+name+" -> "+called,owner);reference(item,"call "+name+" -> "+called,desc);}
                            public void visitFieldInsn(int op,String owner,String field,String desc){calls.add(new Handle(Opcodes.H_GETFIELD,owner,field,desc,false));reference(item,"access "+name+" -> "+field,owner);reference(item,"access "+name+" -> "+field,desc);}
                            public void visitTryCatchBlock(Label s,Label e,Label h,String type){reference(item,"catch "+name,type);}
                            public void visitInvokeDynamicInsn(String name,String desc,Handle bsm,Object... args){
                                item.dynamicCalls++;reference(item,"dynamic call "+name,desc);constant(bsm,item);for(Object v:args)constant(v,item);
                            }
                        };
                    }
                },0);
                try { cr.accept(new CheckClassAdapter(new ClassWriter(0),true),0); }
                catch(Exception ex) {errors.add(item.name+": "+ex.toString().split("\n",2)[0]);}
                if(items.put(item.name,item)!=null)errors.add("Duplicate class "+item.name);
            }
        }
        Set<String> renamed=new HashSet<>();
        for(Item item:items.values())if(!item.original.equals(item.name)&&!items.containsKey(item.original))renamed.add(item.original);
        for(Item item:items.values()) {
            item.references.forEach((where,refs)->{for(String ref:refs)if(renamed.contains(ref))errors.add("Stale type "+ref+" at "+item.name+" "+where);});
            if(item.nestHost!=null) {
                Item host=items.get(item.nestHost);
                if(host==null||!host.nestMembers.contains(item.name))errors.add("Invalid nest host for "+item.name);
            }
            for(String member:item.nestMembers) {
                Item child=items.get(member);
                if(child==null||!item.name.equals(child.nestHost))errors.add("Invalid nest member "+member+" of "+item.name);
            }
        }
        for(Handle h:handles) {
            if(!items.containsKey(h.getOwner()))continue;
            if(!resolves(items,h.getOwner(),h.getName(),h.getDesc(),h.getTag()<=Opcodes.H_PUTSTATIC,new HashSet<>()))
                errors.add("Unresolved internal bootstrap handle: "+h);
        }
        Set<String> suspicious=new TreeSet<>();
        for(Handle h:calls)if(items.containsKey(h.getOwner())&&!resolves(items,h.getOwner(),h.getName(),h.getDesc(),h.getTag()<=Opcodes.H_PUTSTATIC,new HashSet<>())) {
            // Inherited external methods cannot be resolved from this JAR alone.
            if(h.getName().length()<=3&&!h.getName().equals("run")&&!h.getName().equals("add"))suspicious.add(h.toString());
        }
        for(String call:suspicious)errors.add("Unresolved short internal member: "+call);
        Map<String,Object> report=new LinkedHashMap<>();report.put("jar",args[0]);report.put("classes",items.size());
        report.put("methods",items.values().stream().mapToInt(i->i.methods.size()).sum());
        report.put("fields",items.values().stream().mapToInt(i->i.fields.size()).sum());
        report.put("dynamic_constants",items.values().stream().mapToInt(i->i.dynamicConstants).sum());
        report.put("dynamic_calls",items.values().stream().mapToInt(i->i.dynamicCalls).sum());
        report.put("bootstrap_handles",handles.size());report.put("errors",errors);report.put("inventory",items.values());
        Files.writeString(Path.of(args[1]),new GsonBuilder().setPrettyPrinting().create().toJson(report)+"\n");
        System.out.println("classes="+items.size()+" methods="+report.get("methods")+" fields="+report.get("fields")+" errors="+errors.size());
        for(String error:errors.stream().limit(15).toArray(String[]::new))System.err.println(error);
        if(!errors.isEmpty())System.exit(1);
    }
    static boolean resolves(Map<String,Item> items,String owner,String name,String desc,boolean field,Set<String> seen){
        if(!seen.add(owner))return false;
        Item item=items.get(owner);if(item==null)return false;
        if((field?item.fields:item.methods).contains(name+(field?":":"")+desc))return true;
        if(item.parent!=null&&resolves(items,item.parent,name,desc,field,seen))return true;
        for(String iface:item.interfaces)if(resolves(items,iface,name,desc,field,seen))return true;
        return false;
    }
}
