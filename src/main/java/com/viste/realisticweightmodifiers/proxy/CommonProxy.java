package com.viste.realisticweightmodifiers.proxy;

import com.viste.realisticweightmodifiers.events.CheckInventory;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxy implements IProxy {
	
	public void preInit() {
		FMLCommonHandler.instance().bus().register(new CheckInventory());
	}

	public void init() {
		
	}

	public void postInit() {
		
	}

}