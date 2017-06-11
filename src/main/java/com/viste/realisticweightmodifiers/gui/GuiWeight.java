package com.viste.realisticweightmodifiers.gui;

import com.viste.realisticweightmodifiers.Config;
import com.viste.realisticweightmodifiers.events.CheckInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiWeight extends Gui {
	String weight = "400" + " / " + Config.playerCapacityCap;
	
	public GuiWeight(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		// string height = 8
		drawString(mc.fontRendererObj, weight, width - mc.fontRendererObj.getStringWidth(weight) - 2, height - 10, Integer.parseInt("AAAAAA", 16));
	}
}
