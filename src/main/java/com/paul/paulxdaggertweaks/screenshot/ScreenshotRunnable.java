package com.paul.paulxdaggertweaks.screenshot;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class ScreenshotRunnable implements Runnable
{
    private String host;
    private BufferedImage screenshot;
    private boolean processing;
    
    public ScreenshotRunnable( final String host, final BufferedImage screenshot) {
        this.screenshot = screenshot;
        this.host = host;
    }
    
    public boolean isProcessing() {
        return this.processing;
    }
    
    public BufferedImage get() {
        return this.processing ? null : this.screenshot;
    }
    
    @Override
    public void run() {
        this.processing = true;
//        ScreenshotHandler.saveScreenshot(this.screenshot);
        if (this.host.equals("imgur")) {
            try {
                final ImageHost imageHost = ImageHost.imageHosts.get("imgur");
                imageHost.upload(this.screenshot,ImageHost.UPLOAD_METHOD.ANON, new String[0]);
                final String link = imageHost.getLink();
                final TextComponentString linkChat = new TextComponentString(TextFormatting.GREEN + "Uploaded screenshot at " + TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + link);
                linkChat.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                Minecraft.getMinecraft().player.sendMessage(linkChat);
                    addToClipboard(link);
                if (imageHost.upload(this.screenshot, ImageHost.UPLOAD_METHOD.ANON, new String[0])) {
//                    ScreenshotVisual.instance.setUploadStatus(ScreenshotVisual.Status.UPLOAD_SUCCESS);
                }
            }
            catch (Exception e) {
            }
        }
        this.processing = false;
    }

	public static void addToClipboard(final String string) {
		final StringSelection selection = new StringSelection(string);
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
	}
}