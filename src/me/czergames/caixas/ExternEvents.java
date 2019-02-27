package me.czergames.caixas;


import apis.ConfigAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ExternEvents implements Listener {

    public ConfigAPI caixas = new ConfigAPI("caixas.yml");

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("§cCaixas")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getType() == Material.CHEST) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eCaixa básica")) {
                    if (caixas.getConfig().contains(p.getName())) {
                        if (caixas.getInt(p.getName()) > 0) {
                            for (EnderCaixa ec : Main.CACHE_CAIXAS) {
                                if (ec.getUser() != null) {
                                    if (ec.getUser().equals(p.getName())) {
                                        p.closeInventory();
                                        ec.animate();
                                        caixas.set(p.getName(), caixas.getInt(p.getName()) - 1);
                                        caixas.saveConfig();
                                        caixas.reloadConfig();
                                        if(ec.getUser() != null){
                                            ec.setUser(null);
                                        }
                                        return;
                                    }

                                }
                            }
                            p.sendMessage("§cJá há alguem abrindo uma caixa no momento. Tente novamente mais tarde.");
                        } else {
                            p.sendMessage("§cVocê não tem caixas básicas para abrir!");
                        }
                    } else {
                        p.sendMessage("§cOcorreu um erro! Relogue no servidor e tente novamente...");
                    }
                }
            }
        }
    }

}
