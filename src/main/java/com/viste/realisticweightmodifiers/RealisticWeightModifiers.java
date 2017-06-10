package com.viste.realisticweightmodifiers;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.viste.realisticweightmodifiers.proxy.IProxy;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MODID, name=Reference.NAME, version=Reference.VERSION)
public class RealisticWeightModifiers {
	
	public File configFile = null;
	
	private static final Logger log = LogManager.getLogger(Reference.MODID);
	
	@Instance(Reference.MODID)
    public static RealisticWeightModifiers instance;
	
	@SidedProxy(clientSide=Reference.CLIENT_PROXY, serverSide=Reference.SERVER_PROXY)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		log.info("PreInitializing");
		configFile = event.getModConfigurationDirectory();
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		log.info("Initializing");
		
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		log.info("PostInitializing");
		
		proxy.postInit();
	}
	
}