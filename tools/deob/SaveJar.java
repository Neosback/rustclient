import java.io.File;
import net.runelite.deob.util.JarUtil;

/** Compute frames only after normalization has resolved every renamed type. */
public final class SaveJar {
    public static void main(String[] args) {
        if(args.length!=2)throw new IllegalArgumentException("input.jar output.jar");
        JarUtil.save(JarUtil.load(new File(args[0])),new File(args[1]));
    }
}
