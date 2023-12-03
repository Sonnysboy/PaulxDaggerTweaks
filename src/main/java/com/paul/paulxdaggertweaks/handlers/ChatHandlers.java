package com.paul.paulxdaggertweaks.handlers;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
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

	private static final HashMap<String, BufferedImage> linksAndImages = new HashMap<>();

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
					linksAndImages.put(url, image);

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

}
