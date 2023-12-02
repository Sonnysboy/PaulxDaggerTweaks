package com.paul.paulxdaggertweaks.asm.patcher.patchers;

import static com.paul.paulxdaggertweaks.asm.utils.ASMUtils.aload;
import static com.paul.paulxdaggertweaks.asm.utils.ASMUtils.insnListOf;
import static com.paul.paulxdaggertweaks.asm.utils.ASMUtils.invokeStatic;

import java.util.ListIterator;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.paul.paulxdaggertweaks.asm.patcher.ClassPatcher;
import com.paul.paulxdaggertweaks.asm.patcher.PatchMethod;

public class GuiChatPatcher extends ClassPatcher implements Opcodes {

	/**
	 * 
	 * Adds a call to our custom handling method:
	 * 
	 * public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	 * drawRect(2, this.height - 14, this.width - 2, this.height - 2,
	 * Integer.MIN_VALUE); this.inputField.drawTextBox(); ITextComponent
	 * itextcomponent =
	 * this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
	 * 
	 * if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() !=
	 * null) { this.handleComponentHover(itextcomponent, mouseX, mouseY); }
	 * 
	 * super.drawScreen(mouseX, mouseY, partialTicks);
	 * 
	 * } ->
	 * 
	 * public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	 * drawRect(2, this.height - 14, this.width - 2, this.height - 2,
	 * Integer.MIN_VALUE); this.inputField.drawTextBox(); ITextComponent
	 * itextcomponent =
	 * this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
	 * 
	 * ChatHandlers.handleTextComponent(itextcomponent);
	 * 
	 * 
	 * if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() !=
	 * null) { this.handleComponentHover(itextcomponent, mouseX, mouseY); }
	 * 
	 * super.drawScreen(mouseX, mouseY, partialTicks); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@PatchMethod(name = { "drawScreen", "func_73863_a" }, desc = "(IIF)V")
	public void patchDrawScreen(MethodNode node) {

		InsnList instructions = node.instructions;

		for (ListIterator<AbstractInsnNode> iter = instructions.iterator(); iter.hasNext();) {

			AbstractInsnNode insn = iter.next();

			if (insn.getOpcode() == ASTORE) {
				VarInsnNode vins = (VarInsnNode) insn;
				if (vins.var == 4) {
					instructions.insert(insn,
							insnListOf(aload(4), invokeStatic("com/paul/paulxdaggertweaks/handlers/ChatHandlers",
									"handleTextComponent", "(Lnet/minecraft/util/text/ITextComponent;)V")));
					break;
				}
			}
		}
		System.out.println("finished patching drawScreen");

	}

}
