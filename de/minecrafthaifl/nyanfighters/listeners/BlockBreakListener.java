package de.minecrafthaifl.nyanfighters.listeners;

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
        e.setCancelled(true);
    }
}
