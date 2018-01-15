package com.viste.realisticweightmodifiers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;


public class GuiWeight extends Gui {
	private FontRenderer fontRenderer;
	private ScaledResolution scaled;
	private Minecraft minecraft;
	private int width, height;
	private static int color_default = 0xAAAAAA;
	private static int color_warning = 0xFFD700;
	private static int color_over_encumbered = 0xDC143C;

	public GuiWeight() {
		this.minecraft = Minecraft.getMinecraft();
	}
	
	
	private int renderWeightColour(int weight, int weightCap)
	{
		if(weight > weightCap) {
			return color_over_encumbered;
		}
		
		if (weight > weightCap - weightCap/10) {
			return color_warning;
		}
		
		return color_default;
	}

	public void renderWeight(int weight, int weightCap) {
		
		String weightString = weight + " / " + weightCap;
		int weightColor = this.renderWeightColour(weight, weightCap);
		this.render(weightString, weightColor);
	}
		
	public void render(String string, int color) {
		fontRenderer = this.minecraft.fontRenderer;
		this.scaled = new ScaledResolution(minecraft);
		width = scaled.getScaledWidth();
		height = scaled.getScaledHeight();

		fontRenderer.drawStringWithShadow(string, this.width - fontRenderer.getStringWidth(string) - 2, this.height - 10, color);
	}
}