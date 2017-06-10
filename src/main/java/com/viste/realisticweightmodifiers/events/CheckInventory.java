package com.viste.realisticweightmodifiers.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viste.realisticweightmodifiers.RealisticWeightModifiers;
import com.viste.realisticweightmodifiers.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CheckInventory {
	public int playerWeightCap = 5000;
	public int playerCurrentWeight;
	
	private static final Logger log = LogManager.getLogger(Reference.MODID);
	
    public HashMap<String, Integer> weightMap = new HashMap<String, Integer>();
	
	public CheckInventory() {
		// Check / Copy the config files into the config folder
		File configDir = new File(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.CONFIG_PATH));
		File jsonConfigWeights = new File(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.JSON_CONFIG_VALUES_PATH));
		
		// Create config folder
		if(!configDir.exists()) {
			log.info("(Config Folder) Creating " + Reference.CONFIG_PATH);
			try {
				configDir.mkdir();
			} catch (SecurityException se) {
				log.fatal("(Config Folder) Creation Failed");
				log.fatal(se);
				return;
			}
			log.info("(Config Folder) Creation Success");
		} else {
			log.info("(Config Folder) Found " + Reference.CONFIG_PATH);
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
				for(int j = 0; j < jsonResponse.get(i).items.size(); j++){
					if(weightMap.containsKey(jsonResponse.get(i).modid+":"+jsonResponse.get(i).items.get(j).id)){
						weightMap.put(jsonResponse.get(i).modid+":"+jsonResponse.get(i).items.get(j).id, jsonResponse.get(i).items.get(j).weight);
					}
				}
			}
		} catch (Exception e) {
			log.fatal("(JSON File) Loading Failure");
			log.fatal(e);
			return;
		}
		log.info("(JSON File) Loading Success");
	}
	
	@SubscribeEvent
	public void onInventoryUpdate(PlayerTickEvent evt) {
		if(evt.player.inventory.inventoryChanged){
			playerCurrentWeight = 0;
			evt.player.inventory.inventoryChanged = false;
			for(int i = 0; i < evt.player.inventory.getSizeInventory(); i++) {
				ItemStack is = evt.player.inventory.getStackInSlot(i);
				if(is != null) {
					if(is.getItem().getRegistryName().getResourceDomain() != null){					
						if(weightMap.containsKey(is.getItem().getRegistryName().getResourceDomain()+":"+is.getItem().getRegistryName().getResourcePath())){
							playerCurrentWeight += weightMap.get(is.getItem().getRegistryName().getResourceDomain()+":"+is.getItem().getRegistryName().getResourcePath())*is.stackSize;
						}
					}
				}
			}
		}
	}
	
	// Copy from 'asset' into 'file'
	void copyFile(File file, String asset) {
		if(!file.exists()) {
			log.info("(JSON File) Copying " + asset);
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
				log.fatal("(JSON File) Copy Failed");
				log.fatal(ioe);
				return;
			}
			log.info("(JSON File) Copy Success");
		} else {
			log.info("(JSON File) Found " + asset); 
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

