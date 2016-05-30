package de.minecrafthaifl.nyanfighters.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Paul on 29.04.2016.
 */
public class HungerListener implements Listener
{
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e)
    {
        e.setCancelled(true);
    }
}
