package com.viste.realisticweightmodifiers.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viste.realisticweightmodifiers.RealisticWeightModifiers;
import com.viste.realisticweightmodifiers.Reference;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CheckInventory {
	int inventorySlots = 41; // hotbar + inventory + armor + off-hand
	
	public List<Weights> items = new ArrayList<Weights>();

	private static final Logger log = LogManager.getLogger(Reference.MODID);
	
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
			log.info("(JSON File) Loading");
			
			List<Weights> weights;
			
			log.info("-> (File Read) Reading JSON files");
			try {
				Gson gson = new Gson();
				
				// Tiers
				BufferedReader brWeights = new BufferedReader(new FileReader(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.JSON_CONFIG_VALUES_PATH)));
				Type typeWeights = new TypeToken<List<Weights>>(){}.getType();
				weights = gson.fromJson(brWeights, typeWeights);
			} catch (IOException ioe) {
				log.fatal("-> (File Read) Reading Failure");
				log.fatal(ioe);
				return;
			}
			
			log.info("-> (File Read) Reading Success");
			
			try {
				log.info("-> (Mods) Loading All");
				for(int i = 0; i < weights.size(); i++) {
					
				}
				log.info("-> (Mods) Loading Success");
			} catch (Exception e) {
				log.fatal("-> (Mods) Loading Failure");
				log.fatal(e);
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
		for(int i = 0; i < inventorySlots; i++) {
			
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

class Weights {
	private String modid;
	private List<Items> items;
	
	public Weights(String modid, List<Items> items) {
		this.modid = modid;
		this.items = items;
	}
}

class Items {
	private String id;
	private int weight;
	
	public Items(String id, int weight) {
		this.id = id;
		this.weight = weight;
	}
}
