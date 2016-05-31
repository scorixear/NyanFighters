package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by Paul on 10.05.2016.
 */
public class LeaveListener implements Listener
{
    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        e.setQuitMessage("");
        FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
        Nyanfighters.getInstance().getPlayersConfi().set(e.getPlayer().getUniqueId().toString(), null);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(), Nyanfighters.getInstance().getPlayers());
       if(JoinListener.getCP()!=null)
       {
           if(JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString()))                                    //Spieler ist kein Spec
           {
               JoinListener.getCP().remove(e.getPlayer().getUniqueId().toString());
               Bukkit.broadcastMessage("§6[NyanFighters] §8"+e.getPlayer().getName()+" hat das Spiel verlassen.");
               Nyanfighters.getInstance().getPlayersConfi().set(e.getPlayer().getUniqueId().toString()+".Attacker", null);
               Nyanfighters.getInstance().getStatsConfi().set(e.getPlayer().getUniqueId().toString()+".Kills", null);
               Nyanfighters.getInstance().getStatsConfi().set(e.getPlayer().getUniqueId().toString()+".Deaths",null);
               YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(), Nyanfighters.getInstance().getPlayers());
               YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
           }
       }

    }
}
