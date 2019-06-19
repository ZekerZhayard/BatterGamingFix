package io.github.zekerzhayard.battergamingfix.asm.transformers.impl;

import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractClassTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractInsnTransformer;
import io.github.zekerzhayard.battergamingfix.asm.transformers.AbstractMethodTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

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
                                    private int count = 0;

                                    @Override
                                    public boolean isTargetInsn(AbstractInsnNode ain) {
                                        if (ain instanceof VarInsnNode) {
                                            VarInsnNode vin = (VarInsnNode) ain;
                                            if (vin.getOpcode() == Opcodes.ALOAD && vin.var == 1) {
                                                this.count++;
                                                return this.count == 2;
                                            }
                                        }
                                        return false;
                                    }

                                    @Override
                                    public void transform(MethodNode mn, AbstractInsnNode ain) {
                                        mn.instructions.insert(ain, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/net/URLEncoder", "encode", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", false));
                                        mn.instructions.insert(ain, new LdcInsnNode("UTF-8"));
                                    }
                                }
                        };
                    }
                }
        };
    }
}
