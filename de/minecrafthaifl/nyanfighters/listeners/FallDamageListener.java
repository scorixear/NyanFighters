package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YmlMethods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Paul on 11.05.2016.
 */
public class FallDamageListener implements Listener
{
    @EventHandler
    public void onFallDamage(EntityDamageEvent e)
    {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)&&!Nyanfighters.getInstance().getNoBlocks())
        {
            e.setCancelled(true);
        }

    }
}
