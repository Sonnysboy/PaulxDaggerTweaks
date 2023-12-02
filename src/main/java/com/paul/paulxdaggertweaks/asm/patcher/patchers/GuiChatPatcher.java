package com.paul.paulxdaggertweaks.asm.patcher.patchers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.paul.paulxdaggertweaks.asm.patcher.ClassPatcher;
import com.paul.paulxdaggertweaks.asm.patcher.PatchMethod;

import static com.paul.paulxdaggertweaks.asm.utils.ASMUtils.*;

public class GuiChatPatcher extends ClassPatcher implements Opcodes {

//	 ok so patching GuiScreen doesnt work for some reason but patching into here works just fine. I love programming

	@PatchMethod(name = { "drawScreen", "func_73863_a" }, desc = "(IIF)V")
	public void patchDrawScreen(MethodNode node) {

		System.out.println("transforming drawScreen of chat");

//		node.instructions.insert(insnListOf(
//				new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"),
//				new LdcInsnNode("done"), invokeVirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V")));
	}

}
