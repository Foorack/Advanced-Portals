package com.sekwah.advancedportals.destinations;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.mysql.jdbc.Util;
import com.sekwah.advancedportals.AdvancedPortalsPlugin;
import com.sekwah.advancedportals.ConfigAccessor;

public class Destination {
	
    private static AdvancedPortalsPlugin plugin;

	public Destination(AdvancedPortalsPlugin plugin) {
    	Destination.plugin = plugin;
    }
	
	public static void create(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());
		
		config.saveConfig();
	}
	
	public static void move(Location location, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", location.getWorld().getName());
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", location.getX());
		config.getConfig().set(name.toLowerCase() + ".pos.Y", location.getY());
		config.getConfig().set(name.toLowerCase() + ".pos.Z", location.getZ());
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", location.getPitch());
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", location.getYaw());
		
		config.saveConfig();
	}
	
	public static void rename(String oldName, String newName){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(newName.toLowerCase() + ".world", config.getConfig().getString(oldName + ".world"));
		
		config.getConfig().set(newName.toLowerCase() + ".pos.X", config.getConfig().getDouble(oldName + ".pos.X"));
		config.getConfig().set(newName.toLowerCase() + ".pos.Y", config.getConfig().getDouble(oldName + ".pos.Y"));
		config.getConfig().set(newName.toLowerCase() + ".pos.Z", config.getConfig().getDouble(oldName + ".pos.Z"));
		
		config.getConfig().set(newName.toLowerCase() + ".pos.pitch", config.getConfig().getDouble(oldName + ".pos.pitch"));
		config.getConfig().set(newName.toLowerCase() + ".pos.yaw", config.getConfig().getDouble(oldName + ".pos.yaw"));
		
		remove(oldName);
		
		config.saveConfig();
	}
	
	public static void remove(String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		
		config.getConfig().set(name.toLowerCase() + ".world", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos.X", null);
		config.getConfig().set(name.toLowerCase() + ".pos.Y", null);
		config.getConfig().set(name.toLowerCase() + ".pos.Z", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos.pitch", null);
		config.getConfig().set(name.toLowerCase() + ".pos.yaw", null);
		
		config.getConfig().set(name.toLowerCase() + ".pos", null);
		
		config.getConfig().set(name.toLowerCase(), null);
		
		config.saveConfig();
	}
	
	public static boolean warp(Player player, String name){
		ConfigAccessor config = new ConfigAccessor(plugin, "Destinations.yml");
		if(config.getConfig().getString(name + ".world") != null){
			Location loc = player.getLocation();
			if(Bukkit.getWorld(config.getConfig().getString(name + ".world")) != null){
				loc.setWorld(Bukkit.getWorld(config.getConfig().getString(name + ".world")));
				
				loc.setX(config.getConfig().getDouble(name + ".pos.X"));
				loc.setY(config.getConfig().getDouble(name + ".pos.Y"));
				loc.setZ(config.getConfig().getDouble(name + ".pos.Z"));
				
				loc.setPitch((float) config.getConfig().getDouble(name + ".pos.pitch"));
				loc.setYaw((float) config.getConfig().getDouble(name + ".pos.yaw"));
				
				Chunk c = loc.getChunk();
				Entity riding = player.getVehicle();
				if (!c.isLoaded()) c.load();
				if(player.getVehicle() != null){
					player.sendMessage("�c[�7AdvancedPortals�c] You cannot currently teleport while riding");
					riding.setPassenger(null);
					riding.teleport(loc);
					player.teleport(loc);
					riding.setPassenger(player);
				}
				else{
					player.teleport(loc);	
				}
				
				
				return true;
			}
			else{
				player.sendMessage("�cThe destination you are trying to warp to seems to be linked to a world that doesn't exist!");
				plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' is linked to the world "
						+ config.getConfig().getString(name + ".world") + " which doesnt seem to exist any more!");
				return false;
			}
		}
		else{
			player.sendMessage("�cThere has been a problem warping you to the selected destination!");
			plugin.getLogger().log(Level.SEVERE, "The destination '" + name + "' has just had a warp "
					+ "attempt and either the data is corrupt or that destination doesn't exist!");
			return false;
		}
		
	}
}