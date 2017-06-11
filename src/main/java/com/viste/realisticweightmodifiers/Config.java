package com.viste.realisticweightmodifiers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.viste.realisticweightmodifiers.proxy.CommonProxy;

import net.minecraftforge.common.config.Configuration;

public class Config {
	// Categories
	private static final String CATEGORY_GENERAL = "general";
	
	// Values you can access
	public static boolean weighWholeStack = true;
	public static int playerCapacityCap = 500;
	public static int playerSpeedMod = playerCapacityCap / 5 * 10;
	
	public static void readConfig() {
		Configuration cfg = CommonProxy.config;
		try {
			cfg.load();
			initGeneralConfig(cfg);
		} catch (Exception e) {
			Reference.log.error("Problem loading config file!");
			Reference.log.error(e);
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}
	
	private static void initGeneralConfig(Configuration cfg) {
		cfg.addCustomCategoryComment(CATEGORY_GENERAL, "Realistic Weight Modifiers");
		weighWholeStack = cfg.getBoolean("weighWholeStack", CATEGORY_GENERAL, weighWholeStack, "Set to false if you want a stack to count as 1 item (when weighing) as opposed to weighing each item of the stack");
		playerCapacityCap = cfg.getInt("playerCapacityCap", CATEGORY_GENERAL, playerCapacityCap, 0, 999999, "The capacity of the player, when passing this cap the player will start to slow down");
	}
}
