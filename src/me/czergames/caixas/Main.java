package me.czergames.caixas;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static HashMap<Integer, EnderCaixa> CACHE_CAIXAS = new HashMap<>();
	public static HashMap<String, Integer> CACHE_LOCS = new HashMap<>();
	public static HashMap<String, Integer> CACHE_USER = new HashMap<>();

	public void onEnable() {
		ConsoleCommandSender csl = Bukkit.getConsoleSender();
		csl.sendMessage("§6§lCzerCaixas §ffoi ativado com sucesso!");
		csl.sendMessage("§eVersion: §f"+getDescription().getVersion());
		csl.sendMessage("§eAutor: §fCzerGamesBR_ + zPJR13_");
		Bukkit.getPluginManager().registerEvents(new ExternEvents(), this);
		Bukkit.getPluginManager().registerEvents(new CaixasEvents(), this);
		getCommand("caixas").setExecutor(new SetCaixa());
		File f = new File(getDataFolder(), "config.yml");
	    if (!f.exists()) {
	      saveResource("config.yml", false);
	    }
	    File f1 = new File(getDataFolder(), "caixas.yml");
	    if (!f1.exists()) {
	      saveResource("caixas.yml", false);
	    }
	}
	
	public void onDisable() {
		ConsoleCommandSender csl = Bukkit.getConsoleSender();
		csl.sendMessage("§6§lCzerCaixas §ffoi desativado com sucesso!");
	}
	
	public static Main getInstance() {
		return Main.getPlugin(Main.class);
	}
	
}


