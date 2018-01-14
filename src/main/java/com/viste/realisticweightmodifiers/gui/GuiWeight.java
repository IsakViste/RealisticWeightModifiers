package com.viste.realisticweightmodifiers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;


public class GuiWeight extends Gui {
	private FontRenderer fontRenderer;
	private ScaledResolution scaled;
	private int width, height;
	
	private void verifyRenderer() {
		if (fontRenderer == null) {
			Minecraft minecraft = Minecraft.getMinecraft();
			fontRenderer = minecraft.fontRenderer;
			
			scaled = new ScaledResolution(minecraft);
			width = scaled.getScaledWidth();
			height = scaled.getScaledHeight();
		}
	}
	
	public void render(String string, int color) {
		verifyRenderer();
		fontRenderer.drawStringWithShadow(string, width - fontRenderer.getStringWidth(string) - 2, height - 10, color);
	}
}