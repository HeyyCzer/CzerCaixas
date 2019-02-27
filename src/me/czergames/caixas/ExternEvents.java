package me.czergames.caixas;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import apis.ConfigAPI;

public class ExternEvents implements Listener{

	public ConfigAPI caixas = new ConfigAPI("caixas.yml");
		
	@EventHandler
	public void invClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equals("§cCaixas")) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if(e.getCurrentItem().getType() == Material.CHEST) {
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eCaixa básica")) {
					if(caixas.getConfig().contains(p.getName())) {
						if(caixas.getInt(p.getName()) > 0) {
							caixas.set(p.getName(), caixas.getInt(p.getName()) - 1);
							caixas.saveConfig();
							caixas.reloadConfig();
							
							
							p.closeInventory();
							CaixasEvents.animationCreateB(CaixasEvents.clickloc.add(0.5, 0, 0.5));
								
						}else {
							p.sendMessage("§cVocê não tem caixas básicas para abrir!");
						}
					}else {
						p.sendMessage("§cOcorreu um erro! Relogue no servidor e tente novamente...");
					}
				}
			}
		}
	}
	
}
