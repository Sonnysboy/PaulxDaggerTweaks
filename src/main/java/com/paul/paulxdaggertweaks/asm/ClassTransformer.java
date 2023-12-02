package com.paul.paulxdaggertweaks.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.paul.paulxdaggertweaks.asm.patcher.ClassPatcher;
import com.paul.paulxdaggertweaks.asm.patcher.patchers.GuiChatPatcher;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {

	/**
	 * Holds the target classes and their respective patchers. The string should be
	 * the full class path of the class
	 */
	private final Map<String, ClassPatcher> patchers = new HashMap<>();

	public ClassTransformer() {
//	patchers.put(targetClass, <patcher>);
		patchers.put("net.minecraft.client.gui.GuiChat", new GuiChatPatcher());

	}

	/**
	 * @param transformedName the class name.
	 */
	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		ClassPatcher patcher = patchers.get(transformedName);
		if (patcher != null) {
			ClassNode classNode = new ClassNode();
			ClassReader reader = new ClassReader(bytes);
			reader.accept(classNode, 0);

			patcher.patch(classNode);

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

			classNode.accept(writer);

			return writer.toByteArray();
		}
		return bytes;
	}

}
