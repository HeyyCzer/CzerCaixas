package me.czergames.caixas;

import apis.ConfigAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class CaixasEvents implements Listener {

    public static ArrayList<Integer> ids = new ArrayList<>();

    public static ArrayList<String> cblore = new ArrayList<>();

    public static ConfigAPI caixas = new ConfigAPI("caixas.yml");


    public static void openInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 5 * 9, "§cCaixas");
        ItemStack cb = new ItemStack(Material.CHEST);
        ItemMeta cbm = cb.getItemMeta();
        caixas.reloadConfig();
        cbm.setDisplayName("§eCaixa básica");
        cblore.clear();
        cblore.add("§7");
        cblore.add("§7Você possui: " + caixas.getInt(p.getName()));
        cblore.add("§7");
        cblore.add("§eClique para abrir!");
        cbm.setLore(cblore);
        cb.setItemMeta(cbm);
        inv.setItem(13, cb);
        p.openInventory(inv);
    }


    @EventHandler
    public void aoClicarNaCaixa(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            for (EnderCaixa ec : Main.CACHE_CAIXAS) {
                if (ec.getLoc().equals(b.getLocation())) {
                    e.setCancelled(true);
                    if (ec.getUser() != null) {
                        p.sendMessage("§cJá há alguem abrindo uma caixa no momento. Tente novamente mais tarde.");
                        return;
                    }
                    ec.setUser(p.getName());
                    openInventory(p);
                    Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            p.closeInventory();
                            ec.setUser(null);
                        }
                    }, 20 * 20);
                }
            }
        }
    }

    @EventHandler
    public void armorStandClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ArmorStand) {
            ArmorStand en = (ArmorStand) e.getRightClicked();
            if (!en.isVisible()) {
                e.setCancelled(true);
            }
        }
    }
}
