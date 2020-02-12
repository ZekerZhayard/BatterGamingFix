package io.github.zekerzhayard.battergamingfix.asm.transformers.impl;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractInsnTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractMethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class BatterGamingTransformer extends AbstractClassTransformer {
    @Override
    public boolean isTargetClassName(String className) {
        return className.equals("com.netease.mc.mod.battergaming.iiiiIIIIII");
    }

    @Override
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] {
            new AbstractMethodTransformer() {
                @Override
                public boolean isTargetMethod(String methodName, String methodDesc) {
                    return methodName.equals("ALLATORIxDEMO") && methodDesc.equals("(Ljava/lang/String;Lcom/google/gson/JsonObject;)V");
                }

                @Override
                public AbstractInsnTransformer[] getInsnTransformers() {
                    return new AbstractInsnTransformer[] {
                        new AbstractInsnTransformer() {
                            @Override
                            public boolean isTargetInsn(AbstractInsnNode ain) {
                                if (ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                                    MethodInsnNode min = (MethodInsnNode) ain;
                                    return min.owner.equals("java/net/URL") && min.name.equals("openStream") && min.desc.equals("()Ljava/io/InputStream;");
                                }
                                return false;
                            }

                            @Override
                            public void transform(MethodNode mn, AbstractInsnNode ain) {
                                mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/battergamingfix/BatterGamingHook", "encodeURL", "(Ljava/net/URL;)Ljava/net/URL;", false));
                            }
                        }
                    };
                }
            }
        };
    }
}
