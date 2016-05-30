package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        if(Nyanfighters.getInstance().getGame())
        {
            if(!JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString()))
            {
                e.setCancelled(true);
                for(Player p: Bukkit.getOnlinePlayers())
                {
                    if(!JoinListener.getCP().contains(p.getUniqueId().toString()))
                    {
                        p.sendMessage("§8[Spec]"+p.getName()+" §f: "+e.getMessage());
                    }
                }
            }
        }
    }
}
