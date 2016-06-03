package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

        if(e.getDamager() instanceof Player)                                                                            //Angreifer ist Spieler
        {
            if(e.getEntity()instanceof Player)                                                                          //Angegriffene ist Spieler
            {
                if(!Nyanfighters.getInstance().getGame())                                                               //Spiel läuft nicht
                {
                    e.setCancelled(true);

                }
                else                                                                                                    //Spiel läuft
                {
                    if (JoinListener.getCP().contains(((Player)e.getEntity()).getUniqueId().toString())) {              //Angegriffener ist kein Spectator
                        FileConfiguration c = Nyanfighters.getInstance().getPlayersConfi();
                        c.set(((Player) e.getEntity()).getUniqueId().toString()+".Attacker",((Player) e.getDamager()).getUniqueId());
                        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(),Nyanfighters.getInstance().getPlayers());

                    }
                    else                                                                                                //Angegriffener ist Spectator
                    {
                        e.setCancelled(true);
                    }

                }
            }
        }

    }
}
