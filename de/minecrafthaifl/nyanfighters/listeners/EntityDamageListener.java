package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;

/**
 * Created by Paul on 11.05.2016.
 */
public class EntityDamageListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            if (JoinListener.getCP().contains(((Player)e.getEntity()).getUniqueId().toString())) {
                if ( e.getDamager() instanceof Snowball && ((Snowball) e.getDamager()).getShooter() instanceof Player) {
                    FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                    File f = Nyanfighters.getInstance().getPlayers();
                    e.setDamage(10);
                    String uuida = ((Player) e.getEntity()).getUniqueId().toString();
                    String uuidb = ((Player) ((Snowball) e.getDamager()).getShooter()).getUniqueId().toString();
                    c.set(uuida + ".Attacker", uuidb);
                    YamlHandler.saveYamlFile(c, f);
                } else if ( e.getDamager() instanceof Player) {
                    FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                    File f = Nyanfighters.getInstance().getPlayers();

                    String uuida = ((Player) e.getEntity()).getUniqueId().toString();
                    String uuidb = ((Player) e.getDamager()).getUniqueId().toString();
                    c.set(uuida + ".Attacker", uuidb);
                    YamlHandler.saveYamlFile(c, f);
                } else if ( e.getDamager() instanceof FishHook && ((FishHook) e.getDamager()).getShooter() instanceof Player) {
                    FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                    File f = Nyanfighters.getInstance().getPlayers();

                    String uuida = ((Player) e.getEntity()).getUniqueId().toString();
                    String uuidb = ((Player) ((FishHook) e.getDamager()).getShooter()).getUniqueId().toString();
                    c.set(uuida + ".Attacker", uuidb);
                    YamlHandler.saveYamlFile(c, f);
                } else if ( e.getDamager() instanceof EnderPearl && ((EnderPearl) e.getDamager()).getShooter() instanceof Player) {
                    FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                    File f = Nyanfighters.getInstance().getPlayers();

                    String uuida = ((Player) e.getEntity()).getUniqueId().toString();
                    String uuidb = ((Player) ((EnderPearl) e.getDamager()).getShooter()).getUniqueId().toString();
                    c.set(uuida + ".Attacker", uuidb);
                    YamlHandler.saveYamlFile(c, f);
                }
            }
        }

    }
}
