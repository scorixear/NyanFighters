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
        if(Nyanfighters.getInstance().getGame())                                                                        //Spiel läuft
        {
            if(!JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString()))                                  //Spieler ist Spectator
            {
                e.setCancelled(true);
                for(Player p: Bukkit.getOnlinePlayers())                                                                //Durch alle Spieler iterieren
                {
                    if(!JoinListener.getCP().contains(p.getUniqueId().toString()))                                      //Ausgewählte Spieler ist Spectator
                    {
                        p.sendMessage("§8[Spec]"+p.getName()+" §f: "+e.getMessage());
                    }
                }
            }
        }
    }
}
