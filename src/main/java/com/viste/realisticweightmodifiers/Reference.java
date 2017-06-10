package com.viste.realisticweightmodifiers;

public class Reference {
	
	public static final String MODID = "realisticweightmodifiers";
	public static final String NAME = "Realistic Weight Modifiers";
	public static final String VERSION = "1.0.0";

	public static final String CLIENT_PROXY = "com.viste.realisticweightmodifiers.proxy.ClientProxy";
	public static final String SERVER_PROXY = "com.viste.realisticweightmodifiers.proxy.CommonProxy";
	
	public static final String CONFIG_PATH = "/" + MODID + "/";
	
	public static final String JSON_VALUES_FILE = "weight_values.json";
	public static final String ASSET_VALUES_PATH = "/assets/" + MODID + "/" + JSON_VALUES_FILE;	
	public static final String JSON_CONFIG_VALUES_PATH = CONFIG_PATH + JSON_VALUES_FILE;
}
