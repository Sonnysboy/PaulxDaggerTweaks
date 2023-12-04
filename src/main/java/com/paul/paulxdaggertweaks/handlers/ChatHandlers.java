package com.paul.paulxdaggertweaks.handlers;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "paulxdaggertweaks")
public class ChatHandlers {

	private static final HashMap<String, Tuple<BufferedImage, DynamicTexture>> linksAndImages = new HashMap<>();

	private static final Pattern imageUrlRegex = Pattern
			.compile("(?<link>((http)?s?:?(\\/\\/[^\"']*\\.(?:png|jpg|jpeg|gif|png|svg))))");

	public static void handleTextComponent(ITextComponent component) {

		if (component == null)
			return; // no component hovered

		ClickEvent event;

		if (!((event = component.getStyle().getClickEvent()) != null && event.getAction() == Action.OPEN_URL))
			return;

		String url = event.getValue();

		if (linksAndImages.containsKey(url)) {

			System.out.println("Rendering image for url " + url);

			Tuple<BufferedImage, DynamicTexture> image = linksAndImages.get(url);

			BufferedImage bi = image.getFirst();
			DynamicTexture dt = image.getSecond();

			int width = bi.getWidth();
			int height = bi.getHeight();

			System.out.println("image dimensions: " + width + " x " + height);
			
			
			GlStateManager.pushMatrix();
			
			GlStateManager.color(1f, 1f, 1f, 1f);

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, dt.getGlTextureId());
			
			int w;

			Gui.drawModalRectWithCustomSizedTexture(
					0,
					0, 
					0,
					0, 
					width,
					Minecraft.getMinecraft().displayHeight, width, height);

			
			GlStateManager.popMatrix();

		}

		// this is so fucking scuffed
	}

	@SubscribeEvent
	public static void chatReceivedEvent(ClientChatReceivedEvent e) {

		ITextComponent component = e.getMessage();

		String text = TextFormatting.getTextWithoutFormattingCodes(component.getFormattedText());
		System.out.println(text);
		Matcher m = imageUrlRegex.matcher(text);
		if (m.find()) {

			String url = m.group("link");

			if (!linksAndImages.containsKey(url)) {

				System.out.println("grabbing image from " + url);

				try {
					BufferedImage image = ImageIO.read(new URL(url));
					linksAndImages.put(url, new Tuple<BufferedImage, DynamicTexture>(image, new DynamicTexture(image)));

					System.out.println("grabbed image successfully");
				} catch (Exception $ex) {

					Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
							TextFormatting.RED + "(!) Could not download the image, check logs for errors"));
					$ex.printStackTrace();

				}
			}

		} else {
			System.out.println("not image");
		}
	}

	private static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (xCoord + 0.0F), (double) (yCoord + (float) maxV), (double) -90)
				.tex((double) ((float) (minU + 0) * 0.00390625F), (double) ((float) (minV + maxV) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (xCoord + (float) maxU), (double) (yCoord + (float) maxV), (double) -90)
				.tex((double) ((float) (minU + maxU) * 0.00390625F), (double) ((float) (minV + maxV) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (xCoord + (float) maxU), (double) (yCoord + 0.0F), (double) -90)
				.tex((double) ((float) (minU + maxU) * 0.00390625F), (double) ((float) (minV + 0) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (xCoord + 0.0F), (double) (yCoord + 0.0F), (double) -90)
				.tex((double) ((float) (minU + 0) * 0.00390625F), (double) ((float) (minV + 0) * 0.00390625F))
				.endVertex();
		tessellator.draw();
	}

}
