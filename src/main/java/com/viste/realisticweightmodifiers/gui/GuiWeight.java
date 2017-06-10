package com.viste.realisticweightmodifiers.gui;

import com.viste.realisticweightmodifiers.Config;
import com.viste.realisticweightmodifiers.events.CheckInventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiWeight extends Gui {
	String weight = "400" + " / " + Config.playerCapacityCap;
	
	public GuiWeight(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		drawString(mc.fontRendererObj, weight, 2, 2, Integer.parseInt("AAAAAA", 16));
	}
}
