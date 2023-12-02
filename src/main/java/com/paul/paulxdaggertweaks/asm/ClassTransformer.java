package com.paul.paulxdaggertweaks.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.paul.paulxdaggertweaks.asm.patcher.ClassPatcher;
import com.paul.paulxdaggertweaks.asm.patcher.patchers.GUIMainMenuPatcher;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer {

    /**
     * Holds the target classes and their respective patchers. The string should be
     * the full class path of the class
     */
    private final Map<String, ClassPatcher> patchers = new HashMap<>();

    public ClassTransformer() {
//	patchers.put(targetClass, <patcher>);
	patchers.put("net.minecraft.client.gui.GuiMainMenu", new GUIMainMenuPatcher());

    }

    /**
     * @param transformedName the class name.
     */
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
	ClassPatcher patcher = patchers.get(transformedName);
	if (patcher != null) {
	    ClassReader reader = new ClassReader(bytes);
	    ClassNode classNode = new ClassNode();
	    reader.accept(classNode, ClassReader.SKIP_FRAMES);
	    patcher.patch(classNode);
	    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
	    classNode.accept(writer);
	    return writer.toByteArray();
	}
	return bytes;
    }

}
