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
        if (JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString())) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || Action.RIGHT_CLICK_BLOCK == e.getAction()) {
                if (e.getItem() != null) {
                    if (e.getItem().getType() == Material.SLIME_BLOCK) {
                        e.setCancelled(true);
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30 * 20, 4));
                        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                    } else if (e.getItem().getType() == Material.SNOW_BALL) {
                        e.setCancelled(true);
                        Snowball sb = e.getPlayer().launchProjectile(Snowball.class);
                        sb.setShooter(e.getPlayer());
                        sb.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }


                    } else if (e.getItem().getType() == Material.EGG) {
                        e.setCancelled(true);
                        Egg egg = e.getPlayer().launchProjectile(Egg.class);
                        egg.setShooter(e.getPlayer());
                        egg.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }
                    } else if (e.getItem().getType() == Material.ENDER_PEARL) {
                        e.setCancelled(true);
                        Vector a = e.getPlayer().getLocation().getDirection().normalize();
                        Location l = e.getPlayer().getLocation().add(a.multiply(23));
                    /*for(int k = 22; k>=0&&!l.getBlock().getType().equals(Material.AIR);k--)
                    {
                        l = e.getPlayer().getLocation().add(a.multiply(k));
                        Bukkit.broadcastMessage(""+l.getBlock().getType());
                    }*/
                        e.getPlayer().teleport(l);
                        if (e.getItem().getAmount() == 1) {
                            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        } else {
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                        }
                    } else {
                        if (e.getItem().getType() == Material.SPLASH_POTION) {
                            e.setCancelled(true);
                            Collection<String> c = new ArrayList<String>();
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
