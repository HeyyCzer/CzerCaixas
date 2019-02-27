package me.czergames.caixas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import apis.ConfigAPI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;


public class CaixasEvents implements Listener {
	
	public static ArrayList<Integer> ids = new ArrayList<>();
	
	public static ArrayList<String> cblore = new ArrayList<>();
	
	public static ConfigAPI caixas = new ConfigAPI("caixas.yml");
	
	public static void animationCreateB(Location l) {
		final Location loc = l;
		Location loc2 = loc.add(0, -1.10, 0);
		final ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(loc2, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setMarker(false);
		as.setGravity(false);
		as.setCanPickupItems(false);
		as.setCustomName("§aAbrindo...");
		as.setCustomNameVisible(false);
		ItemStack head = new ItemStack(Material.CHEST);
		as.setHelmet(head);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Runnable() {
			int i = 1;
			
			public void run() {
				if(i <= 5 && i > 0) {
					as.teleport(as.getLocation().add(0, 0.2, 0));
					i++;
					for(Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
						if(entity instanceof Player) {
							Player p = (Player) entity;
							p.playSound(loc, Sound.NOTE_BASS, 1F, 1F);
							break;
						}
						
					}
					
				}
				else if(i < 100 && i > 5) {
					EulerAngle oldRot = as.getHeadPose();
					EulerAngle newRot = oldRot.add(0, 0.2, 0);
					as.setHeadPose(newRot);
					i++;
					for(Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
						if(entity instanceof Player) {
							Player p = (Player) entity;
							p.playSound(loc, Sound.NOTE_PLING, 1F, 1F);
							break;
						}
						
					}
					as.setCustomNameVisible(true);
				}
				else if(i == 100) {
					Location asloc = as.getEyeLocation();
					as.setCustomName("§aVocê ganhou: §fTESTE1");
					for(Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
						if(entity instanceof Player) {
							Player p = (Player) entity;
							PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, (float) asloc.getX(), (float) asloc.getY(), (float) asloc.getZ(), 0F, 0F, 0F, 0, 0, 1);
							((CraftPlayer) p).getHandle().playerConnection.sendPacket(particle);
							p.playSound(loc, Sound.EXPLODE, 1F, 1F);
							break;
						}
						
					}
					i = 150;
					
				}
				else if(i >= 150 && i < 250) {
					i++;
				}
				else if(i == 250) {
					i = 0;
					as.remove();
				}
				
			}
		}, 10, 1);
		
			
			
	}
	
	public static void openInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 5*9, "§cCaixas");
		ItemStack cb = new ItemStack(Material.CHEST);
		ItemMeta cbm = cb.getItemMeta();
		caixas.reloadConfig();
		cbm.setDisplayName("§eCaixa básica");
		cblore.clear();
		cblore.add("§7");
		cblore.add("§7Você possui: "+ caixas.getInt(p.getName()));
		cblore.add("§7");
		cblore.add("§eClique para abrir!");
		cbm.setLore(cblore);
		cb.setItemMeta(cbm);
		
		inv.setItem(13, cb);
		
		p.openInventory(inv);
	}
	
	static Location clickloc;
	
	@EventHandler
	public void aoClicarNaCaixa(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				if(e.getClickedBlock().getLocation().getWorld().getName().equalsIgnoreCase("mundotestes")) {
					openInventory(p);
					e.setCancelled(true);
					clickloc = e.getClickedBlock().getLocation();
				}
			}
		}
	}
	
	@EventHandler
	public void armorStandClick(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked() instanceof ArmorStand) {
			ArmorStand en = (ArmorStand) e.getRightClicked();
			if(!en.isVisible()) {
				e.setCancelled(true);
			}
		}
	}
	
	

}
