package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;

import net.minecraft.server.v1_9_R2.Explosion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.io.File;

/**
 * Created by Paul on 10.05.2016.
 */
public class ProjectileHitListener implements Listener
{
    @EventHandler
    public void onHit(ProjectileHitEvent e)
    {
        if(e.getEntity() instanceof Snowball)                                                                           //Entity ist Schneeball
        {
            Snowball sb = (Snowball) e.getEntity();
            if(sb.getShooter() instanceof Player)                                                                       //Shooter ist Spieler
            {
                Explosion ex =((CraftWorld) sb.getWorld()).getHandle().createExplosion(((CraftPlayer) sb.getShooter()).getHandle(), sb.getLocation().getX(), sb.getLocation().getY()+1, sb.getLocation().getZ(), 1.5F, false, false);
                for(Entity en:sb.getNearbyEntities(1,1,1))                                                              //Alle um 1 Block nahen Entitys
                {
                    if(en instanceof Player)                                                                            //Entity ist Spieler
                    {
                        Nyanfighters.getInstance().getPlayersConfi().set(((Player) en).getUniqueId().toString()+".Attacker", ((CraftPlayer) sb.getShooter()).getUniqueId().toString());//Shooter als Attacker hinzugefügt
                        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getPlayersConfi(),Nyanfighters.getInstance().getPlayers());
                    }
                }

            }

        }
        else if(e.getEntity() instanceof Egg)                                                                           //Entity ist Ei
        {
            Egg egg = (Egg) e.getEntity();
            if(egg.getShooter() instanceof Player)                                                                      //Shooter ist Spieler
            {
                setArea(1,1,e.getEntity().getLocation());
            }
        }
    }
    public void setArea(int x, int z, Location start)                                                                   //Setzt 3x3 Würfel asu Spinnenweben(fakeblocks), siehe setArea Movelistener
    {
        setBlocks("start",0,0,0,start);
        setBlocks("left",x,0,0,start);
        setBlocks("right",-x,0,0,start);
        setBlocks("front",0,0,z,start);
        setBlocks("back",0,0,-z,start);
        setBlocks("leftfront",x,0,z,start);
        setBlocks("rightfront",-x,0,z,start);
        setBlocks("leftback",x,0,-z,start);
        setBlocks("rightback",-x,0,-z,start);

        setBlocks("startup",0,1,0,start);
        setBlocks("leftup",x,1,0,start);
        setBlocks("rightup",-x,1,0,start);
        setBlocks("frontup",0,1,z,start);
        setBlocks("backup",0,1,-z,start);
        setBlocks("leftfrontup",x,1,z,start);
        setBlocks("rightfrontup",-x,1,z,start);
        setBlocks("leftbackup",x,1,-z,start);
        setBlocks("rightbackup",-x,1,-z,start);

        setBlocks("startupup",0,2,0,start);
        setBlocks("leftupup",x,2,0,start);
        setBlocks("rightupup",-x,2,0,start);
        setBlocks("frontupup",0,2,z,start);
        setBlocks("backupup",0,2,-z,start);
        setBlocks("leftfrontupup",x,2,z,start);
        setBlocks("rightfrontupup",-x,2,z,start);
        setBlocks("leftbackupup",x,2,-z,start);
        setBlocks("rightbackupup",-x,2,-z,start);
    }
    public void setBlocks(String s, int x, int y, int z, Location start)                                                //Setzt Fakeblocks (siehe setBlocks Movelistener)
    {

        Location web = new Location(start.getWorld(), start.getBlockX()+x,start.getBlockY()+y, start.getBlockZ()+z);
        if(web.getBlock().getType()==Material.AIR)
        {
            for(Player p: Bukkit.getOnlinePlayers())
                p.sendBlockChange(web, Material.WEB, (byte)0);
                removeBlock(start, web, x, y, z);
        }

    }
    public void removeBlock(Location start, Location remove, int x, int y, int z) {                                     //entfernt jeweilige Blöcke nach 10 Sekunden
        Bukkit.getScheduler().scheduleSyncDelayedTask(Nyanfighters.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers())
                    p.sendBlockChange(remove, Material.AIR, (byte) 0);
            }
        }, 20 * 10);
    }
}
