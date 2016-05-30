package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Paul on 10.05.2016.
 */
public class AttackListener implements Listener
{
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e)
    {

        if(e.getDamager() instanceof Player)
        {
            if(e.getEntity()instanceof Player)
            {
                if(!Nyanfighters.getInstance().getGame())
                {
                    e.setCancelled(true);
                }
                else
                {
                    if (JoinListener.getCP().contains(((Player)e.getEntity()).getUniqueId().toString())) {
                        FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                        c.set(((Player) e.getEntity()).getUniqueId().toString()+".Attacker",((Player) e.getDamager()).getUniqueId());
                        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(),Nyanfighters.getInstance().getPlayers());
                    }
                    else
                    {
                        e.setCancelled(true);
                    }

                }
            }
        }

    }
}
