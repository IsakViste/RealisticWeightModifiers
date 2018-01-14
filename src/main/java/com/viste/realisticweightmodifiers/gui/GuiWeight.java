package com.viste.realisticweightmodifiers.gui;

import com.viste.realisticweightmodifiers.Config;
import com.viste.realisticweightmodifiers.events.CheckInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

/*public class GuiWeight extends Gui {
	//private Minecraft minecraft;
	
	public GuiWeight(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		// string height = 8
		drawString(mc.fontRenderer, "Player Weight", width - mc.fontRenderer.getStringWidth("Player Weight") - 2, height - 10, Integer.parseInt("AAAAAA", 16));
	}
	
	/*public void redraw(String weight) {
		ScaledResolution scaled = new ScaledResolution(this.minecraft);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();

		drawString(this.minecraft.fontRenderer, weight, width - this.minecraft.fontRenderer.getStringWidth(weight) - 2, height - 10, Integer.parseInt("AAAAAA", 16));
	} /
}*/

public class GuiWeight extends Gui {
	private static int color = 0xAAAAAA;
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
	
	public void render(String string) {
		verifyRenderer();
		fontRenderer.drawStringWithShadow(string, width - fontRenderer.getStringWidth(string) - 2, height - 10, color);
	}
}