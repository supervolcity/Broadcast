package me.supervol.sup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	public final Logger log = Logger.getLogger("Minecraft");
	public static main plugin;
	public static int currentline = 0;
	public static int tid = 0;
	public static int running = 1;
	public static long interval = 600;
	// test

	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + "version" + pdfFile.getVersion() + "is now disabled!");
	}
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + "version" + pdfFile.getVersion() + "is now enabled!");
	
		tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				try {
					broadcastMessage("plugins/Broadcast/message.txt");
				} catch (IOException e) {
					
				}
			}
				
			}, 0, interval * 10);
	}
	
	public static void broadcastMessage(String fileName) throws IOException {
		FileInputStream fs;
		fs = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < currentline; ++i)
			br.readLine();
		String line = br.readLine();	
		line = line.replaceAll("&f", ChatColor.WHITE + "");
		line = line.replaceAll("&e", ChatColor.YELLOW + "");
		line = line.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
		line = line.replaceAll("&a", ChatColor.DARK_RED + "");
		line = line.replaceAll("&b", ChatColor.DARK_BLUE + "");
		line = line.replaceAll("&c", ChatColor.GRAY + "");
		line = line.replaceAll("&1", ChatColor.GREEN + "");
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[Broadcast]" + ChatColor.WHITE + line);
		LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fileName)));
		lnr.skip(Long.MAX_VALUE);
		int lastline = lnr.getLineNumber();
		if (currentline + 1 == lastline + 1){
			currentline = 0;
		} else {
            currentline++;
            }
		}


		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
			if (cmd.getName().equalsIgnoreCase("bhelp")){
				Player player = (Player)sender;
				player.sendMessage("Broadcast help");
			
		}
			return true;
			
		}

	}