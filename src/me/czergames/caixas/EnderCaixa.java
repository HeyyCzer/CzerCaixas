package me.czergames.caixas;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class EnderCaixa {
    private Location loc;
    private Material type;
    private String user;

    public EnderCaixa(Location loc, Material type, String user) {
        this.loc = loc;
        this.type = type;
        this.user = user;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void animate() {
        Location loc2 = this.loc.clone().add(0, -1.10, 0);
        final ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc2, EntityType.ARMOR_STAND);
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
                if (i <= 5 && i > 0) {
                    as.teleport(as.getLocation().add(0, 0.2, 0));
                    i++;
                    for (Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
                        if (entity instanceof Player) {
                            Player p = (Player) entity;
                            p.playSound(loc, Sound.NOTE_BASS, 1F, 1F);
                            break;
                        }

                    }

                } else if (i < 100 && i > 5) {
                    EulerAngle oldRot = as.getHeadPose();
                    EulerAngle newRot = oldRot.add(0, 0.2, 0);
                    as.setHeadPose(newRot);
                    i++;
                    for (Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
                        if (entity instanceof Player) {
                            Player p = (Player) entity;
                            p.playSound(loc, Sound.NOTE_PLING, 1F, 1F);
                            break;
                        }

                    }
                    as.setCustomNameVisible(true);
                } else if (i == 100) {
                    Location asloc = as.getEyeLocation();
                    as.setCustomName("§aVocê ganhou: §fTESTE1");
                    for (Entity entity : Bukkit.getWorld("mundotestes").getEntities()) {
                        if (entity instanceof Player) {
                            Player p = (Player) entity;
                            PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, true, (float) asloc.getX(), (float) asloc.getY(), (float) asloc.getZ(), 0F, 0F, 0F, 0, 0, 1);
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(particle);
                            p.playSound(loc, Sound.EXPLODE, 1F, 1F);
                            break;
                        }

                    }
                    i = 150;

                } else if (i >= 150 && i < 250) {
                    i++;
                } else if (i == 250) {
                    i = 0;
                    as.remove();
                }

            }
        }, 10, 1);


    }

}
