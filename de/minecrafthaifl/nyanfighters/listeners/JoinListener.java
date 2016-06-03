package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import de.minecrafthaifl.nyanfighters.YmlMethods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Paul on 28.04.2016.
 */
public class JoinListener implements Listener
{
   private static Collection<String> cp;
    private static Collection<String> cpspec;;
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage("");
        Player p = e.getPlayer();
        FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
        Nyanfighters.getInstance().getPlayersConfi().set(p.getUniqueId().toString(), null);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(), Nyanfighters.getInstance().getPlayers());
        if(c.isSet("LobbySpawn"))                                                                                       //Lobbyspawn ist gesetzt
        {
            int i = 0;
            while (c.isSet("SpielSpawn." + i))
                i++;
            i--;
            int random = (int) Math.random() * i;
            Location l = YmlMethods.getLobbySpawn();
            Location s = YmlMethods.getSpielSpawn(i);
            if(Nyanfighters.getInstance().getGame())
            {
                p.teleport(s);
            }
            else
            {
                p.teleport(l);
            }
            p.getInventory().clear();

            if(!Nyanfighters.getInstance().getGame())
            {
                Bukkit.broadcastMessage("§6[NyanFighters] §8"+p.getName()+" hat das Spiel betreten.");
            }

        }
        else                                                                                                            //Lobbyspawn ist nicht gesetzt
        {
            p.sendMessage("§6[NyanFighters]§c Warning: No LobbySpawn is set!");
        }
        if(Nyanfighters.getInstance().getGame()||Nyanfighters.getInstance().getNoMove())                                //Spiel läuft oder Vorbereitungszeit
        {
            if(getCP()==null)                                                                                           //Als Spec hinzufügen
            {
                setCP((Collection<Player>) Bukkit.getOnlinePlayers());
            }
            if(getCP().contains(p.getUniqueId().toString()))
            {
                getCP().remove(p.getUniqueId().toString());

            }
            for(String f: getCP())                                                                                      //Spieler hiden
            {
                Bukkit.getPlayer(UUID.fromString(f)).hidePlayer(p);

            }
            p.setAllowFlight(true);
            p.setFlying(true);

        }
        else                                                                                                            //Spiel läuft nicht
        {
            if(getCP()==null)                                                                                           //Spieler ist normaler Spieler
            {
                setCP((Collection<Player>) Bukkit.getOnlinePlayers());
            }
            else
            {
                getCP().add(p.getUniqueId().toString());
            }
        }


    }
    public static Collection<String>getCP()                                                                             //gibt eine Collection mit UUIDs[Strings] zurück (keine Spectator)
    {
        if(cp==null)
        {
            cp=new ArrayList<String>();
        }
        return cp;
    }
    public static void setCP(Collection<Player> cpe)                                                                    //Setzt eine Collection aus Strings, fordert Collection aus Playern
    {
        cp=new ArrayList<String>();
        for(Player f:cpe)
        {
            getCP().add(f.getUniqueId().toString());
        }

    }



}
