package me.czergames.caixas;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends JavaPlugin {

	public static ArrayList<EnderCaixa> CACHE_CAIXAS = new ArrayList<>();

	public void onEnable() {
		ConsoleCommandSender csl = Bukkit.getConsoleSender();
		csl.sendMessage("§6§lCzerCaixas §ffoi ativado com sucesso!");
		csl.sendMessage("§eVersion: §f"+getDescription().getVersion());
		csl.sendMessage("§eAutor: §fCzerGamesBR_ + zPJR13_");
		Bukkit.getPluginManager().registerEvents(new ExternEvents(), this);
		Bukkit.getPluginManager().registerEvents(new CaixasEvents(), this);
		getCommand("setcaixa").setExecutor(new SetCaixa());
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


