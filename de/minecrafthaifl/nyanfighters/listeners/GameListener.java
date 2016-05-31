package de.minecrafthaifl.nyanfighters;

import de.minecrafthaifl.nyanfighters.listeners.JoinListener;
import net.minecraft.server.v1_9_R1.*;
import org.apache.commons.lang.enums.Enum;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

/**
 * Created by Paul on 29.04.2016.
 */
public class  GameListener {
    static int time;
    static String title;
    static int id;
    static int id2;

    public static void lobbyWait()                                                                                      //Lobby-Ablauf
    {

            time=90;
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Nyanfighters.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(time<30)
                    {
                        if (time == 10) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch 10 Sekunden, bis das Spiel beginnt!");
                        } else if (time == 5) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch §45§9 Sekunden, bis das Spiel beginnt!");
                        title = "§45";

                        } else if (time == 4) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch §c4§9 Sekunden, bis das Spiel beginnt!");
                        title = "§c4";

                        } else if (time == 3) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch §63§9 Sekunden, bis das Spiel beginnt!");
                        title = "§63";

                        } else if (time == 2) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch §e2§9 Sekunden, bis das Spiel beginnt!");
                        title = "§e2";

                        } else if (time == 1) {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch §a1§9 Sekunden, bis das Spiel beginnt!");
                        title = "§a1";

                        }else if (time == 0)
                        {
                            if (Bukkit.getOnlinePlayers().size() < 2) {
                                Bukkit.broadcastMessage("§6[NyanFighters] §cWarte auf weitere Spieler!");
                                time=91;
                            } else {
                                time = 300;
                                Nyanfighters.getInstance().setNoMove(true);

                                Bukkit.getScheduler().cancelTask(id);
                                gameWait();

                            }
                        }
                    }
                    else if(time%30==0)
                    {
                        Bukkit.broadcastMessage("§6[NyanFighters] §9Noch "+time+" Sekunden, bis das Spiel beginnt!");
                    }
                            time--;
                }
            }, 20L,20L);

    }
    public static void gameWait()                                                                                       //Spiel-Ablauf
    {
        int spawn = 0;
        int i  = 0;
        FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
        ItemStack slime =  new ItemStack(Material.SLIME_BLOCK);
        ItemMeta slimem = slime.getItemMeta();
        slimem.setDisplayName("§r§aSprungball");
        slime.setItemMeta(slimem);

        while(c.isSet("SpielSpawn."+i))
        {
            i++;
        }
        i--;
        spawn=i;
        for(Player p: Bukkit.getOnlinePlayers())
        {
            Location teleport = YmlMethods.getSpielSpawn(i);
            Location plattform = new Location(teleport.getWorld(), teleport.getX(), teleport.getY()-1, teleport.getZ(), teleport.getYaw(), teleport.getPitch());
            plattform.getBlock().setType(Material.STAINED_GLASS);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Nyanfighters.getInstance(), new Runnable() {
                @Override
                public void run() {
                    plattform.getBlock().setType(Material.AIR);
                }
            },20*7);
            p.teleport(teleport);
            i--;
            if(i+1==0)
           {
               i=spawn;
           }
            p.getInventory().clear();
            p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            p.getInventory().setItem(0,new ItemStack(Material.STONE_SWORD));
            p.getInventory().setItemInOffHand(new ItemStack(Material.GOLDEN_APPLE, 16));
            p.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
            p.getInventory().setItem(2, slime);
        }
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Nyanfighters.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(time%20==0&&time!=300)
                {
                    ItemStack granade = new ItemStack(Material.SNOW_BALL);
                    ItemMeta granadem= granade.getItemMeta();
                    granadem.setDisplayName("§r§2Granate");
                    granade.setItemMeta(granadem);
                    ItemStack trap = new ItemStack (Material.EGG);
                    ItemMeta trapm = trap.getItemMeta();
                    trapm.setDisplayName("§r3§fSpinnenfalle");
                    trap.setItemMeta(trapm);
                    ItemStack skyfallx = new ItemStack(Material.SPLASH_POTION);
                    ItemMeta skyfallm = skyfallx.getItemMeta();
                    skyfallm.setDisplayName("§r§4SkyFall");
                    skyfallx.setItemMeta(skyfallm);
                    addRandomItem(trap,granade,skyfallx);
                    Bukkit.broadcastMessage("§6[NyanFighters] §a Neue Special-Items wurden ausgeteilt!");
                }
                if (time == 300) {
                    Nyanfighters.getInstance().setNoMove(true);
                    Bukkit.broadcastMessage("§6[NyanFighters] §45");
                } else if (time == 299) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §c4");
                } else if (time == 298) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §63");
                } else if (time == 297) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §e2");
                } else if (time == 296) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §a1");
                } else if (time == 295) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §dGO!");
                    Nyanfighters.getInstance().setNoMove(false);
                    Nyanfighters.getInstance().setGame(true);
                }else if (time == 60) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §bNoch 60 Sekunden");
                } else if (time == 30) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §bNoch 30 Sekunden");
                } else if (time == 15) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §bNoch 15 Sekunden");
                }
                else if (time <= 0)
                {
                    time = 11;
                    FileConfiguration statsConfi = Nyanfighters.getInstance().getStatsConfi();
                    ConfigurationSection section = statsConfi.getConfigurationSection("");
                    int kills = -1;
                    int deaths = -1;
                    String winner="";
                    for(String l:section.getKeys(false))
                    {
                        if(statsConfi.isSet(l+".Kills"))
                        {
                            if(statsConfi.getInt(l+".Kills")>kills)
                            {
                                kills=statsConfi.getInt(l+".Kills");
                                deaths=statsConfi.getInt(l+".Deaths");
                                winner= l;


                            }
                            else if(statsConfi.getInt(l+".Kills")==kills)
                            {
                                if(statsConfi.isSet(l+".Deaths"))
                                {
                                    if(statsConfi.getInt(l+".Deaths")<deaths)
                                    {
                                        kills=statsConfi.getInt(l+".Kills");
                                        deaths=statsConfi.getInt(l+".Deaths");
                                        winner= l;
                                    }
                                    else if(deaths==-1)
                                    {
                                        kills=statsConfi.getInt(l+".Kills");
                                        deaths=statsConfi.getInt(l+".Deaths");
                                        winner= l;
                                    }
                                }
                                else if(deaths==-1)
                                {
                                    kills=statsConfi.getInt(l+".Kills");
                                    deaths=0;
                                    winner= l;
                                }
                            }
                        }
                    }
                    if(winner.equals(""))
                    {
                        Bukkit.broadcastMessage("§6[NyanFighters] §cUnentschieden!");
                    }
                    else
                    {
                        Bukkit.broadcastMessage("§6[NyanFighters] §c"+Bukkit.getPlayer(UUID.fromString(winner)).getName()+" §9hat gewonnen!");
                    }
                    Nyanfighters.getInstance().setGame(false);
                    Bukkit.getScheduler().cancelTask(id);
                    serverRestart();


                } else if (time%60 == 0) {
                    Bukkit.broadcastMessage("§6[NyanFighters] §bNoch " + time / 60 + " Minuten");
                }
                time--;
                for(String p: JoinListener.getCP())
                {
                    ScoreboardUtil.updateScoreboard(Bukkit.getPlayer(UUID.fromString(p)));
                }
            }
        }, 20, 20L );

    }
    public static void serverRestart()                                                                                  //Ende Ablauf
    {
        for(Player p: Bukkit.getOnlinePlayers())
        {
            p.teleport(YmlMethods.getLobbySpawn());
            p.getInventory().clear();
            p.setAllowFlight(false);
        }
        for(Player p: Bukkit.getOnlinePlayers())
        {
            for(String f: JoinListener.getCP())
            {
                if(!p.getUniqueId().toString().equals(f))
                {
                    Bukkit.getPlayer(UUID.fromString(f)).hidePlayer(p);
                }

            }
            if(!JoinListener.getCP().contains(p.getUniqueId().toString()))
            {
                p.setFlying(false);
                p.setAllowFlight(false);
            }

        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Nyanfighters.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(time==10)
                {
                    Bukkit.broadcastMessage("§6[NyanFighters] §cServer restartet in 10 Sekunden");
                }
                else if (time==0)
                {
                    //Bukkit.getServer().reload();
                    Bukkit.broadcastMessage("§4Hier würde der Server jetzt reloaden");
                }
                time--;
            }
        }, 20, 20L);
    }
    public static void addRandomItem(ItemStack trap, ItemStack granade, ItemStack skyfall)
    {
        for(String p:JoinListener.getCP()) {
            double random = Math.random() * 3;
            if (random < 1) {
                Bukkit.getPlayer(UUID.fromString(p)).getInventory().addItem(trap);
            } else if (random < 2) {
                Bukkit.getPlayer(UUID.fromString(p)).getInventory().addItem(granade);
            } else {
                Bukkit.getPlayer(UUID.fromString(p)).getInventory().addItem(skyfall);
                Bukkit.getPlayer(UUID.fromString(p)).getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
            }
        }
    }
    public static void setTime(int i)
    {
        time=i;
    }
}
