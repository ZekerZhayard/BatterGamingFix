package io.github.zekerzhayard.battergamingfix.asm.transformers.impl;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractInsnTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractMethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class EventHandlerTransformer extends AbstractClassTransformer {
    @Override
    public boolean isTargetClassName(String className) {
        return className.equals("com.netease.mc.mod.battergaming.IiiiiiiiIi");
    }

    @Override
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] {
            new AbstractMethodTransformer() {
                @Override
                public boolean isTargetMethod(String methodName, String methodDesc) {
                    return methodName.equals("ALLATORIxDEMO") && methodDesc.equals("(Ljava/lang/String;)V");
                }

                @Override
                public AbstractInsnTransformer[] getInsnTransformers() {
                    return new AbstractInsnTransformer[] {
                        new AbstractInsnTransformer() {
                            @Override
                            public boolean isTargetInsn(AbstractInsnNode ain) {
                                if (ain.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                                    MethodInsnNode min = (MethodInsnNode) ain;
                                    return min.owner.equals("net/minecraft/network/PacketBuffer") && min.name.equals("func_180714_a") && min.desc.equals("(Ljava/lang/String;)Lnet/minecraft/network/PacketBuffer;");
                                }
                                return false;
                            }

                            @Override
                            public void transform(MethodNode mn, AbstractInsnNode ain) {
                                mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "io/github/zekerzhayard/battergamingfix/BatterGamingHook", "hookPacketBuffer", "(Ljava/lang/String;)Ljava/lang/String;", false));
                            }
                        }
                    };
                }
            }
        };
    }
}
