package io.github.zekerzhayard.battergamingfix.asm.transformers.impl;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractInsnTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractMethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class PacketSenderTransformer extends AbstractClassTransformer {
    @Override
    public boolean isTargetClassName(String className) {
        return className.equals("com.netease.mc.mod.battergaming.IIIiIIiIii");
    }

    @Override
    public AbstractMethodTransformer[] getMethodTransformers() {
        return new AbstractMethodTransformer[] {
                new AbstractMethodTransformer() {
                    @Override
                    public boolean isTargetMethod(String methodName, String methodDesc) {
                        return methodName.equals("run") && methodDesc.equals("()V");
                    }

                    @Override
                    public AbstractInsnTransformer[] getInsnTransformers() {
                        return new AbstractInsnTransformer[] {
                                new AbstractInsnTransformer() {
                                    @Override
                                    public boolean isTargetInsn(AbstractInsnNode ain) {
                                        if (ain instanceof MethodInsnNode) {
                                            MethodInsnNode min = (MethodInsnNode) ain;
                                            return min.getOpcode() == Opcodes.INVOKESTATIC && min.owner.equals("com/netease/mc/mod/battergaming/IiiiiiiiIi")
                                                    && min.name.equals("access$000") && min.desc.equals("(Lcom/netease/mc/mod/battergaming/IiiiiiiiIi;Ljava/lang/String;)V");
                                        }
                                        return false;
                                    }

                                    @Override
                                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.DUP));
                                        mn.instructions.insertBefore(ain, new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.DUP_X1));
                                        mn.instructions.insertBefore(ain, new InsnNode(Opcodes.POP));
                                        mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
                                    }
                                }
                        };
                    }
                }
        };
    }
}
