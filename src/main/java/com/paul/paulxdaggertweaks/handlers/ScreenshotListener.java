package com.paul.paulxdaggertweaks.handlers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.paul.paulxdaggertweaks.screenshot.ImageHost;
import com.paul.paulxdaggertweaks.screenshot.ImgurHandler;
import com.paul.paulxdaggertweaks.screenshot.ScreenshotRunnable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "paulxdaggertweaks")
public class ScreenshotListener {

	@SubscribeEvent
	public static void onScreenshot(ScreenshotEvent event) {

		final BufferedImage image = event.getImage();
		File file2 = event.getScreenshotFile();
		new Thread(() -> {
			try {
				ImageIO.write(image, "png", (File) file2);
				final ImageHost imageHost = new ImgurHandler();
				imageHost.upload(image, ImageHost.UPLOAD_METHOD.ANON, new String[0]);
				final String link = imageHost.getLink();
				final TextComponentString linkChat = new TextComponentString(
						TextFormatting.GREEN + "Uploaded screenshot at " + TextFormatting.WHITE.toString()
								+ TextFormatting.UNDERLINE + link);
				linkChat.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
				Minecraft.getMinecraft().player.sendMessage(linkChat);
				ScreenshotRunnable.addToClipboard(link);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		TextComponentString ichatcomponent = new TextComponentString("Saved screenshot to " + file2.getName());
		ichatcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
		ichatcomponent.getStyle().setUnderlined(Boolean.valueOf(true));
		event.setResultMessage(ichatcomponent);
		event.setCanceled(true);

	}

}
