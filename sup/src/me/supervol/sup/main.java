package me.supervol.sup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin{
	File ConfigFile;
	FileConfiguration Config;
	public static final String mainDirectory = "plugins/AutoBroadcaster";
	public final Logger log = Logger.getLogger("Minecraft");
	public static main plugin;
	public static int currentline = 0;
	public static int tid = 0;
	public static int running = 1;
	public static long interval = 600;

	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + "version" + pdfFile.getVersion() + "is now disabled!");
	}
	
	public void onEnable() {
		ConfigFile = new File(getDataFolder(), "Config.yml");
		Config = new YamlConfiguration();
		new File(mainDirectory).mkdir();
		 try {
	            firstRun();
	        } catch (Exception e){
	            e.printStackTrace();
	        }
		getConfig().options().header("This is where your configurable options are!");
		saveConfig();
		PluginDescriptionFile pdfFile = this.getDescription();
		this.log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled!");
	    
	
		tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			public void run(){
				try {
					broadcastMessage("plugins/Broadcast/message.txt");
				} catch (IOException e) {
					
				}
			}
				
			}, 0, interval * 10);
	}
	
    private void firstRun() throws Exception {
        if(!ConfigFile.exists()){
        ConfigFile.getParentFile().mkdirs();
        copy(getResource("Config.yml"), ConfigFile);
          }
        }
    private void copy(InputStream in, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
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

		//Finished commands!
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
			if (cmd.getName().equalsIgnoreCase("sb")){
				if(running == 1) {
					Bukkit.getServer().getScheduler().cancelTask(tid);
					Player player = (Player) sender;
					player.sendMessage("Stoped Broadcasts.");
					running = 0;
				}else{ 
					Player player = (Player) sender;
					player.sendMessage("They arn't running!");
				}
			}else if (commandLabel.equalsIgnoreCase("stb")){
				if(running == 1) {
					Player player = (Player) sender;
					player.sendMessage("They are still running!");
				} else { 
					tid = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
						public void run(){
							try {
								broadcastMessage("plugins/Broadcast/message.txt");
							} catch (IOException e) {
								
							}
						}
							
						}, 0, interval * 10);
					Player player = (Player) sender;
					player.sendMessage("Started Broadcasts.");
					running = 1;
				}
			 }
			return false;	
		}
	}				