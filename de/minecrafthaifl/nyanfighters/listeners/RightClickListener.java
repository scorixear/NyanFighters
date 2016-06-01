package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.Nyanfighters;
import de.minecrafthaifl.nyanfighters.YamlHandler;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Paul on 10.05.2016.
 */
public class RightClickListener implements Listener
{
    @EventHandler
     public void onClick(PlayerInteractEvent e)
    {
        if (JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString())) {                                    //Spieler ist kein Spec
            if (e.getAction() == Action.RIGHT_CLICK_AIR || Action.RIGHT_CLICK_BLOCK == e.getAction()) {                 //Spieler macht Rechtsklick
                if (e.getItem() != null) {                                                                              //Item ist nicht null
                    if (e.getItem().getType() == Material.SLIME_BLOCK) {                                                //Item ist SlimeBlock -->Jumpboost
                        e.setCancelled(true);
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30 * 20, 4));
                        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                    } else if (e.getItem().getType() == Material.SNOW_BALL) {                                           //Item ist Schneeball --->Projektilwurf
                        e.setCancelled(true);
                        Snowball sb = e.getPlayer().launchProjectile(Snowball.class);
                        sb.setShooter(e.getPlayer());
                        sb.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }


                    } else if (e.getItem().getType() == Material.EGG) {                                                 //Item ist Ei -->Projektilwurf
                        e.setCancelled(true);
                        Egg egg = e.getPlayer().launchProjectile(Egg.class);
                        egg.setShooter(e.getPlayer());
                        egg.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }
                    } else if (e.getItem().getType() == Material.ENDER_PEARL) {                                         //Item ist Enderpearl -->Teleport um 23 Blöcke
                        e.setCancelled(true);
                        Vector a = e.getPlayer().getLocation().getDirection().normalize();
                        Location l = e.getPlayer().getLocation().add(a.multiply(23));
                        e.getPlayer().teleport(l);
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }
                    } else {
                        if (e.getItem().getType() == Material.SPLASH_POTION) {                                          //item ist Splashpotion --> Wirf alle Spieler, die nicht im Umkreis von 4 Blöcken sind, in die Luft
                            e.setCancelled(true);
                            /*Collection<String> c = new ArrayList<String>();
                            for (String f : JoinListener.getCP()) {
                                c.add(f);
                                if (!Bukkit.getPlayer(UUID.fromString(f)).equals(null)) {
                                    if (Bukkit.getPlayer(UUID.fromString(f)).getLocation().toVector().subtract(e.getPlayer().getLocation().toVector()).length() <= 4) {
                                        if (c.contains(f))
                                            c.remove(f);
                                    } else if (!Bukkit.getPlayer(UUID.fromString(f)).equals(null)) {
                                        Bukkit.getPlayer(UUID.fromString(f)).teleport(new Location(Bukkit.getPlayer(UUID.fromString(f)).getLocation().getWorld(), Bukkit.getPlayer(UUID.fromString(f)).getLocation().getX(), Bukkit.getPlayer(UUID.fromString(f)).getLocation().getY() + 10, Bukkit.getPlayer(UUID.fromString(f)).getLocation().getZ()));
                                        //Bukkit.getPlayer(UUID.fromString(f)).damage(5);
                                        Nyanfighters.getInstance().setNoBlocks(true);
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(Nyanfighters.getInstance(), new Runnable() {
                                            @Override
                                            public void run() {
                                                Nyanfighters.getInstance().setNoBlocks(false);
                                            }
                                        }, 3 * 20);
                                        FileConfiguration playersConfi = Nyanfighters.getInstance().getPlayersConfi();
                                        File players = Nyanfighters.getInstance().getPlayers();

                                        String uuida = f;
                                        String uuidb = e.getPlayer().getUniqueId().toString();
                                        playersConfi.set(uuida + ".Attacker", uuidb);
                                        YamlHandler.saveYamlFile(playersConfi, players);
                                    }
                                }
                            }*/
                            for(Entity pl:e.getPlayer().getNearbyEntities(5,5,5))
                            {
                                if(pl instanceof Player)
                                {
                                    if(JoinListener.getCP().contains(((Player)pl).getUniqueId().toString()))
                                    {
                                        Vector velo = ((Player)pl).getLocation().toVector().subtract(e.getPlayer().getLocation().toVector()).normalize();//Spieler zurückschubsen
                                        ((Player)pl).setVelocity(velo.multiply(5));
                                    }
                                }

                            }
                            if (e.getItem().getAmount() == 1) {
                                e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                            } else {
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            }
                        }
                    }
                }


            }
        }

    }
    public void projectileLaunch(ProjectileLaunchEvent e)
    {
        if(e.getEntityType() == EntityType.ENDER_PEARL)
        {
            e.setCancelled(true);
        }
    }

}
