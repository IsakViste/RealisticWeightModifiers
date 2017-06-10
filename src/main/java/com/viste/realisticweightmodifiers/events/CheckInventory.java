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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viste.realisticweightmodifiers.RealisticWeightModifiers;
import com.viste.realisticweightmodifiers.Reference;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CheckInventory {
	public int playerWeightCap = 5000;
	public int playerCurrentWeight;
	
	public List<Weights> weights = new ArrayList<Weights>();

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
			
			List<Mods> mods;
			
			log.info("-> (File Read) Reading JSON files");
			try {
				Gson gson = new Gson();
				
				// Weights
				BufferedReader brWeights = new BufferedReader(new FileReader(new String(RealisticWeightModifiers.instance.configFile.getPath() + Reference.JSON_CONFIG_VALUES_PATH)));
				Type typeWeights = new TypeToken<List<Mods>>(){}.getType();
				mods = gson.fromJson(brWeights, typeWeights);
			} catch (IOException ioe) {
				log.fatal("-> (File Read) Reading Failure");
				log.fatal(ioe);
				return;
			}
			
			log.info("-> (File Read) Reading Success");
			
			try {
				log.info("-> (Weights) Loading All");
				for(int i = 0; i < mods.size(); i++) {
					for(int j = 0; j < mods.get(i).items.size(); j++) {
						String path = mods.get(i).modid + ":" + mods.get(i).items.get(j).id.toLowerCase();
						Item item = Item.getByNameOrId(path);
						int weight = mods.get(i).items.get(j).weight;
						if(item == null) {
							log.warn("-> -> (Weight) " + mods.get(i).items.get(j).id + " was not found!");
						} else {
							weights.add(new Weights(item, weight));
						}
					}
				}
				log.info("-> (Weights) Loading Success");
			} catch (Exception e) {
				log.fatal("-> (Weights) Loading Failure");
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
		playerCurrentWeight = 0;
		InventoryPlayer inventory = new InventoryPlayer(evt.player);
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack is = inventory.getStackInSlot(i);
			if(weights.contains(is.getItem())) {
				playerCurrentWeight += weights.get(weights.indexOf(is.getItem())).getWeightOfItem();
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
class Weights {
	private Item item;
	private int weight;
	
	public Weights(Item item, int weight) {
		this.item = item;
		this.weight = weight;
	}
	
	public int getWeightOfItem() {
		return weight;
	}
}

class Mods {
	public String modid;
	public List<ModsItems> items;
	
	public Mods(String modid, List<ModsItems> items) {
		this.modid = modid;
		this.items = items;
	}
}

class ModsItems {
	public String id;
	public int weight;
	
	public ModsItems(String id, int weight) {
		this.id = id;
		this.weight = weight;
	}
}
