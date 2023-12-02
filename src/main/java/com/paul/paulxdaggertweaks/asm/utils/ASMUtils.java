package com.paul.paulxdaggertweaks.asm.utils;

import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

/**
 * 
 * @author prplz Documentation by Tristian
 *
 */
public class ASMUtils {

	/**
	 * Returns an InsnList consisting of the given arguments. Arguments must be
	 * AbstractInsnNode or InsnList.
	 * 
	 * @param args the instructions or instruction lists to add to the returned list
	 * @return an InsnList consisting of the given arguments
	 */
	public static InsnList insnListOf(Object... args) {
		InsnList list = new InsnList();
		for (Object arg : args) {
			if (arg instanceof AbstractInsnNode) {
				list.add((AbstractInsnNode) arg);
			} else if (arg instanceof InsnList) {
				list.add((InsnList) arg);
			} else if (arg instanceof Integer) {
				list.add(new InsnNode((Integer) arg));
			} else {
				throw new IllegalArgumentException("Arguments must be AbstractInsnNode or InsnList");
			}
		}
		return list;
	}

	/**
	 * Returns an InsnList that will only be called if the current boolean on the
	 * stack is true. I.E: {@link invokeStatic} (<some boolean method>)
	 * <code> ifTrue (Opcodes.ICONST_0, Opcodes.IRETURN) </code>
	 * 
	 * @return an InsnList that will only be called if the current boolean on the
	 *         stack is true.
	 */
	public static InsnList ifTrue(Object... args) {
		LabelNode label = new LabelNode();
		return insnListOf(new JumpInsnNode(Opcodes.IFEQ, label), insnListOf(args), label);
	}

	/**
	 * Returns an InsnList that will only be called if the current boolean on the
	 * stack is false. I.E: {@link invokeStatic} (<some boolean method>)
	 * <code> ifFalse(Opcodes.ICONST_0, Opcodes.IRETURN) </code>
	 * 
	 * @return an InsnList that will only be called if the current boolean on the
	 *         stack is false.
	 */
	public static InsnList ifFalse(Object... args) {
		LabelNode label = new LabelNode();
		return insnListOf(new JumpInsnNode(Opcodes.IFNE, label), insnListOf(args), label);
	}

	/**
	 * Returns a MethodInsnNode INVOKESTATIC instruction with the given owner, name,
	 * and desc.
	 * 
	 * @param owner the owner of the method
	 * @param name  the name of the method
	 * @param desc  the descriptor of the method
	 * @return a MethodInsnNode INVOKESTATIC instruction with the given owner, name,
	 *         and desc.
	 */
	public static MethodInsnNode invokeStatic(String owner, String name, String desc) {
		return new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, desc, false);
	}

	/**
	 * Returns a MethodInsnNode INVOKEVIRTUAL instruction with the given owner,
	 * name, and desc.
	 * 
	 * @param owner the owner of the method
	 * @param name  the name of the method
	 * @param desc  the descriptor of the method
	 * @return a MethodInsnNode INVOKEVIRTUAL instruction with the given owner,
	 *         name, and desc.
	 */
	public static MethodInsnNode invokeVirtual(String owner, String name, String desc) {
		return new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, name, desc, false);
	}

	/**
	 * Returns a VarInsnNode ALOAD instruction for the given var index.
	 * 
	 * 
	 * @param opcode the opcode of the load instruction
	 * @param var    the local variable to load
	 * @return a VarInsnNode ALOAD instruction for the given var index.
	 */
	public static VarInsnNode aload(int var) {
		return new VarInsnNode(Opcodes.ALOAD, var);
	}

	/**
	 * Returns a VarInsnNode ILOAD instruction for the given var index
	 * 
	 * @param opcode the opcode of the load instruction
	 * @param var    the local variable to load
	 * @return a VarInsnNode ILOAD instruction for the given var index
	 */
	public static VarInsnNode iload(int var) {
		return new VarInsnNode(Opcodes.ILOAD, var);
	}

	/**
	 * Returns true if the given method node matches the given owner, name, and
	 * desc, taking into account FML's deobfuscation.
	 * 
	 * @param methodInsn the method node to check
	 * @param owner      the owner to check against
	 * @param name       the name to check against
	 * @param desc       the desc to check against
	 * @return true if the given method node matches the given owner, name, and
	 *         desc, taking into account FML's deobfuscation
	 */
	public static boolean deobfMatch(MethodInsnNode methodInsn, String owner, List<String> name, String desc) {
		FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
		return owner.equals(remapper.map(methodInsn.owner))
				&& name.contains(remapper.mapMethodName(methodInsn.owner, methodInsn.name, methodInsn.desc))
				&& desc.equals(remapper.mapMethodDesc(methodInsn.desc));
	}
}