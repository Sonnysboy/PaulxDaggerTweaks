package com.paul.paulxdaggertweaks.asm.patcher.patchers;


import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.paul.paulxdaggertweaks.asm.patcher.ClassPatcher;
import com.paul.paulxdaggertweaks.asm.patcher.PatchMethod;
import static com.paul.paulxdaggertweaks.asm.utils.ASMUtils.*;

public class GUIMainMenuPatcher extends ClassPatcher implements Opcodes {

    @PatchMethod(name = { "initGui", "func_73866_w_" }, desc = "()V")
    public void patchInitGui(MethodNode node) {

	node.instructions.insert(insnListOf(
		new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"),
		new LdcInsnNode("done"), invokeVirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V")));

    }

}
