package de.minecrafthaifl.nyanfighters.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Paul on 11.05.2016.
 */
public class FallDamageListener implements Listener
{
    @EventHandler
    public void onFallDamage(EntityDamageEvent e)
    {
        //if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)&&!Nyanfighters.getInstance().getNoBlocks())          //Falldamage ausstellen, wenn gerade kein skyfall-item aktiviert wurde
        //{
            e.setCancelled(true);
        //}

    }
}
