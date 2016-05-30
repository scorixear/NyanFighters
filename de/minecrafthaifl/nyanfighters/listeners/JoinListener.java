package de.minecrafthaifl.nyanfighters.listeners;

import com.darkblade12.particleeffect.ParticleEffect;
import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import de.minecrafthaifl.nyanfighters.YmlMethods;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
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
        if(c.isSet("LobbySpawn"))
        {
            Location l = YmlMethods.getLobbySpawn();
            p.teleport(l);
            p.getInventory().clear();

            if(!Nyanfighters.getInstance().getGame())
            {
                Bukkit.broadcastMessage("§6[NyanFighters] §8"+p.getName()+" hat das Spiel betreten.");
            }

        }
        else
        {
            p.sendMessage("§6[NyanFighters]§c Warning: No LobbySpawn is set!");
        }
        if(Nyanfighters.getInstance().getGame()||Nyanfighters.getInstance().getNoMove())
        {
            if(getCP()==null)
            {
                setCP((Collection<Player>) Bukkit.getOnlinePlayers());
            }
            if(getCP().contains(p.getUniqueId().toString()))
            {
                getCP().remove(p.getUniqueId().toString());

            }
            for(String f: getCP())
            {
                Bukkit.getPlayer(UUID.fromString(f)).hidePlayer(p);

            }
            p.setAllowFlight(true);
            p.setFlying(true);

        }
        else
        {
            if(getCP()==null)
            {
                setCP((Collection<Player>) Bukkit.getOnlinePlayers());
            }
            else
            {
                getCP().add(p.getUniqueId().toString());
            }
        }


    }
    public static Collection<String>getCP()
    {
        if(cp==null)
        {
            cp=new ArrayList<String>();
        }
        return cp;
    }
    public static void setCP(Collection<Player> cpe)
    {
        cp=new ArrayList<String>();
        for(Player f:cpe)
        {
            getCP().add(f.getUniqueId().toString());
        }

    }



}
