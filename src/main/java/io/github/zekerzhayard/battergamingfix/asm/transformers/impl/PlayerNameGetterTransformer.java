package io.github.zekerzhayard.battergamingfix.asm.transformers.impl;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractInsnTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractMethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class PlayerNameGetterTransformer extends AbstractClassTransformer {
    @Override
    public boolean isTargetClassName(String className) {
        return className.equals("com.netease.mc.mod.battergaming.iIIIiiIIIi");
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
                                        if (ain instanceof FieldInsnNode) {
                                            FieldInsnNode fin = (FieldInsnNode) ain;
                                            return fin.getOpcode() == Opcodes.GETFIELD && fin.owner.equals("net/minecraft/client/Minecraft")
                                                    && fin.name.equals("field_71439_g") && fin.desc.equals("Lnet/minecraft/client/entity/EntityPlayerSP;");
                                        }
                                        return false;
                                    }

                                    @Override
                                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                                        mn.instructions.insert(ain, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "func_110432_I", "()Lnet/minecraft/util/Session;", false));
                                        mn.instructions.remove(ain);
                                    }
                                }, new AbstractInsnTransformer() {
                                    @Override
                                    public boolean isTargetInsn(AbstractInsnNode ain) {
                                        if (ain instanceof MethodInsnNode) {
                                            MethodInsnNode min = (MethodInsnNode) ain;
                                            return min.getOpcode() == Opcodes.INVOKEVIRTUAL && min.owner.equals("net/minecraft/client/entity/EntityPlayerSP")
                                                    && min.name.equals("func_70005_c_") && min.desc.equals("()Ljava/lang/String;");
                                        }
                                        return false;
                                    }

                                    @Override
                                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                                        mn.instructions.insert(ain, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/util/Session", "func_111285_a", "()Ljava/lang/String;", false));
                                        mn.instructions.remove(ain);
                                    }
                                }
                        };
                    }
                }
        };
    }
}
