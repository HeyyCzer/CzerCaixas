package me.czergames.caixas;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCaixa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Comando disponivel apenas para jogadores.");
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("cz.admin")){
            p.sendMessage("§cVocê precisa do grupo §c[Admin] §cou superior para executar este comando.");
            return true;
        }
        if(args.length == 0) {
            p.sendMessage("§cUse: /caixas <give/remove/setloc/set> [player/ID] [quantidade].");
            return true;
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("setloc")){
                EnderCaixa ec = new EnderCaixa(p.getLocation(), Material.ENDER_CHEST, Integer.valueOf(args[1]));
                Main.CACHE_CAIXAS.put(ec.getId(), ec);
                Block b = Bukkit.getWorld(p.getLocation().getWorld().getName()).getBlockAt(p.getLocation());
                b.setType(ec.getType());
                float rotation = p.getLocation().getYaw();
                if (0 <= rotation && rotation < 67.5) {
                    b.setData((byte)3);
                } else if (67.5 <= rotation && rotation < 157.5) {
                    b.setData((byte)0);
                }  else if (157.5 <= rotation && rotation < 247.5) {
                    b.setData((byte)2);
                } else if (247.5 <= rotation && rotation < 337.5) {
                    b.setData((byte)1);
                }  else if (337.5 <= rotation && rotation < 360.0) {
                    b.setData((byte)3);
                }
                return true;
            }
        }
        return false;
    }
}
