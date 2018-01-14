package com.viste.realisticweightmodifiers.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viste.realisticweightmodifiers.Config;
import com.viste.realisticweightmodifiers.RealisticWeightModifiers;
import com.viste.realisticweightmodifiers.Reference;
import com.viste.realisticweightmodifiers.gui.GuiWeight;


import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CheckInventory {
	private int playerWeightCap = Config.playerCapacityCap;
	private int playerCurrentWeight;
	
	private static int color_default = 0xAAAAAA;
	private static int color_warning = 0xFFD700;
	private static int color_over_encumbered = 0xDC143C;
	
    public HashMap<String, Integer> weightMap = new HashMap<String, Integer>();
	
	public CheckInventory() {
		// Check / Copy the config files into the config folder
		File configDir = new File(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.CONFIG_PATH));
		File jsonConfigWeights = new File(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.JSON_CONFIG_VALUES_PATH));
		
		// Create config folder
		if(!configDir.exists()) {
			Reference.log.info("(Config Folder) Creating " + Reference.CONFIG_PATH);
			try {
				configDir.mkdirs();
			} catch (SecurityException se) {
				Reference.log.fatal("(Config Folder) Creation Failed");
				Reference.log.fatal(se);
				return;
			}
			Reference.log.info("(Config Folder) Creation Success");
		} else {
			Reference.log.info("(Config Folder) Found " + Reference.CONFIG_PATH);
		}
		
		// Create VALUES & SETS config
		copyFile(jsonConfigWeights, Reference.ASSET_VALUES_PATH);
		
		// JSON File loading
		try {
			Gson gson = new Gson();
			// Weights
			BufferedReader brWeights = new BufferedReader(new FileReader(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.JSON_CONFIG_VALUES_PATH)));
			List<JsonResponse> jsonResponse = gson.fromJson(brWeights, new TypeToken<List<JsonResponse>>(){}.getType());
			for(int i = 0; i < jsonResponse.size() ; i++){
				int a = 0;
				for(int j = 0; j < jsonResponse.get(i).items.size(); j++){
					if(!weightMap.containsKey(jsonResponse.get(i).modid+":"+jsonResponse.get(i).items.get(j).id)){
						a++;
						weightMap.put(jsonResponse.get(i).modid+":"+jsonResponse.get(i).items.get(j).id, jsonResponse.get(i).items.get(j).weight);
					}
				}
				Reference.log.info("(JSON) " + a + " items have been loaded from " + jsonResponse.get(i).modid);
			}
		} catch (Exception e) {
			Reference.log.fatal("(JSON File) Loading Failure");
			Reference.log.fatal(e);
			return;
		}
		Reference.log.info("(JSON File) Loading Success");
		Reference.log.info("(Player Capacity) " + playerWeightCap);
	}
	
	public int getPlayerWeightCap() {
		return playerWeightCap;
	}
	
	public int getPlayerCurrentWeight() {
		return playerCurrentWeight;
	}
	
	public int getWeightGuiColor() {
		if(playerCurrentWeight > playerWeightCap) {
			return color_over_encumbered;
		}
		
		if (playerCurrentWeight > playerWeightCap - playerWeightCap/10) {
			return color_warning;
		}
		
		return color_default;
	}
	
	@SubscribeEvent
	public void onInventoryUpdate(PlayerTickEvent evt) {
		if(!evt.player.capabilities.isCreativeMode) {
			playerCurrentWeight = 0;
			for(int i = 0; i < evt.player.inventory.getSizeInventory(); i++) {
				ItemStack is = evt.player.inventory.getStackInSlot(i);
				if(is != null) {
					if(is.getItem().getRegistryName().getResourceDomain() != null) {
						if(weightMap.containsKey(is.getItem().getRegistryName().getResourceDomain()+":"+is.getItem().getRegistryName().getResourcePath())) {
							if(Config.weighWholeStack) {
								playerCurrentWeight += weightMap.get(is.getItem().getRegistryName().getResourceDomain()+":"+is.getItem().getRegistryName().getResourcePath()) * is.getCount();
							} else {
								playerCurrentWeight += weightMap.get(is.getItem().getRegistryName().getResourceDomain()+":"+is.getItem().getRegistryName().getResourcePath());
							}
						}
					}
				}
			}
			
			if(playerCurrentWeight > playerWeightCap) {
				evt.player.capabilities.setPlayerWalkSpeed(0.1f - ((float)(playerCurrentWeight - playerWeightCap) / Config.playerSpeedMod));
			} else {
				evt.player.capabilities.setPlayerWalkSpeed(0.1f);
			}
		}
	}
	
	
	public String getStringForGUI() {
		return this.getPlayerCurrentWeight() + " / " + this.getPlayerWeightCap();
	}
	
	// Copy from 'asset' into 'file'
	void copyFile(File file, String asset) {
		if(!file.exists()) {
			Reference.log.info("(JSON File) Copying " + asset);
			try {
				file.createNewFile();
				
				InputStream instream = this.getClass().getResourceAsStream(asset);
				FileOutputStream outstream = new FileOutputStream(file);
				
				byte[] buffer = new byte[1024];
				int length;
				
				while ((length = instream.read(buffer)) > 0) {
					outstream.write(buffer, 0, length);
				}
				
				instream.close();
				outstream.close();
			} catch (IOException ioe) {
				Reference.log.fatal("(JSON File) Copy Failed");
				Reference.log.fatal(ioe);
				return;
			}
			Reference.log.info("(JSON File) Copy Success");
		} else {
			Reference.log.info("(JSON File) Found " + asset); 
		}
	}
}

// http://www.minecraft-servers-list.org/id-list/
class JsonResponse {
    public String modid;
    List<Items> items = new ArrayList<Items>();
}

class Items {
	public String id;
	public int weight;
}

