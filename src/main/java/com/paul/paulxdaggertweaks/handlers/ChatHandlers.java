package com.paul.paulxdaggertweaks.handlers;

import net.minecraft.util.text.ITextComponent;

public class ChatHandlers {

	public static void handleTextComponent(ITextComponent component) {

		if (component == null) 
			return; // no component hovered
		
		String text = component.getUnformattedComponentText();
		
		System.out.println("hovered text: " + text);
//		System.out.println("our own method was called with component " + (null != component ? component : "null")
	
//				+ " and mouse coords (" + mouseX + ", " + mouseY + ")");

	}
}
