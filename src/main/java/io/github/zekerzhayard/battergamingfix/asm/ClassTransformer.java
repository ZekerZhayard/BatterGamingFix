package io.github.zekerzhayard.battergamingfix.asm;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.impl.BatterGamingTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.impl.PacketSenderTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.impl.PlayerNameGetterTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class ClassTransformer implements IClassTransformer {
    private AbstractClassTransformer[] acts = new AbstractClassTransformer[] {
            new BatterGamingTransformer(),
            new PacketSenderTransformer(),
            new PlayerNameGetterTransformer()
    };
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        for (AbstractClassTransformer act : this.acts) {
            if (act.isTargetClassName(transformedName)) {
                System.out.println("Found the class: " + name + " -> " + transformedName);
                ClassNode cn = new ClassNode();
                new ClassReader(basicClass).accept(cn, ClassReader.EXPAND_FRAMES);
                act.transform(cn);
                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);
                return cw.toByteArray();
            }
        }
        return basicClass;
    }
}
