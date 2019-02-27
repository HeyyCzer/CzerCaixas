package me.czergames.caixas;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
            p.sendMessage("§cUse: /caixas <give/remove/setloc/set> [player] [quantidade].");
            return true;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("setloc")){
                EnderCaixa ec = new EnderCaixa(p.getLocation(), Material.ENDER_CHEST, null);
                Main.CACHE_CAIXAS.add(ec);
                Bukkit.getWorld(p.getLocation().getWorld().getName()).getBlockAt(p.getLocation()).setType(ec.getType());
                return true;
            }
        }
        return false;
    }

}
