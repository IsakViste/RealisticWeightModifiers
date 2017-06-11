package com.viste.realisticweightmodifiers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference {
	
	public static final String MODID = "realisticweightmodifiers";
	public static final String NAME = "Realistic Weight Modifiers";
	public static final String VERSION = "${version}";

	public static final String CLIENT_PROXY = "com.viste.realisticweightmodifiers.proxy.ClientProxy";
	public static final String SERVER_PROXY = "com.viste.realisticweightmodifiers.proxy.CommonProxy";
	
	public static Logger log = LogManager.getLogger(Reference.MODID);
	
	public static final String CONFIG_PATH = "/" + MODID + "/";
	
	public static final String JSON_VALUES_FILE = "weight_values.json";
	public static final String ASSET_VALUES_PATH = "/assets/" + MODID + "/" + JSON_VALUES_FILE;	
	public static final String JSON_CONFIG_VALUES_PATH = CONFIG_PATH + JSON_VALUES_FILE;
}
