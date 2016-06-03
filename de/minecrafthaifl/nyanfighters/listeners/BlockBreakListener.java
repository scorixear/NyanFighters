package de.minecrafthaifl.nyanfighters.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Paul on 30.05.2016.
 */
public class BlockBreakListener implements Listener
{
    @EventHandler
    public void onBlockBreack(BlockBreakEvent e)
    {
        if(!e.getPlayer().hasPermission("NyanFighters.build"))
        {
            if(e.getBlock().getType().equals(Material.getMaterial("double_plant")))
            {
                e.setCancelled(true);
                e.getBlock().getLocation().getBlock().setType(Material.getMaterial("double_plant"));
            }
            else
            {
                e.setCancelled(true);
            }
        }
    }
}
