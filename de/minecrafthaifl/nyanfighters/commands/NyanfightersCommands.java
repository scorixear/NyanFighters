package de.minecrafthaifl.nyanfighters.commands;

import com.minnymin.command.Command;
import com.minnymin.command.CommandArgs;
import de.minecrafthaifl.nyanfighters.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import java.io.File;


/**
 * Created by Paul on 27.04.2016.
 */
public class NyanfightersCommands {
    @Command(name="setspawn", description = "Setzt Spawnpoints", usage = "/setspawn <Lobby/Spiel> [Nummer]", permission = "Nyanfighters.setspawn")
    public void commandSetSpawn (CommandArgs args)
    {
        Player p = args.getPlayer();
        String[] arg = args.getArgs();
        if(arg.length==0)                                                                   //keine Argumente
        {
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /setspawn <Lobby/Spiel> [Nummer]");
            return;
        }
        else if(arg.length==1)                                                              //1 Argument
        {
            if(arg[0].equals("Lobby"))                                                      //Lobby-Argument
            {
                Location l = p.getLocation();
                int x= l.getBlockX();
                int y = l.getBlockY();
                int z = l.getBlockZ();
                float pitch = l.getPitch();
                float yaw = l.getYaw();
                String world = l.getWorld().getName();
                File f = Nyanfighters.getInstance().getSpawnpoints();
                FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
                c.set("LobbySpawn.X", x);
                c.set("LobbySpawn.Y", y);
                c.set("LobbySpawn.Z", z);
                c.set("LobbySpawn.Pitch", pitch);
                c.set("LobbySpawn.Yaw", yaw);
                c.set("LobbySpawn.World", world);
                YamlHandler.saveYamlFile(c,f);
                p.sendMessage("§6[Nyanfighters] §aLobbySpawn erfolgreich gesetzt!");
                return;
            }
            else                                                                                                        //falsches Argument
            {
                p.sendMessage("§6[Nyanfighters]§c Nutzung: /setspawn <Lobby/Spiel> [Nummer]");
                return;
            }
        }
        else if(arg.length==2)                                                                                          //2 Argumente
        {
            if(arg[0].equals("Spiel"))                                                                                  //1. Argument ist Spiel
            {
                int i;
                try
                {
                     i = Integer.parseInt(arg[1]);
                }
                catch(NumberFormatException e)
                {
                    i=-1;
                }
                if(i>=0)
                {

                    Location l = p.getLocation();
                    int x= l.getBlockX();
                    int y = l.getBlockY();
                    int z = l.getBlockZ();
                    float pitch = l.getPitch();
                    float yaw = l.getYaw();
                    String world = l.getWorld().getName();
                    File f = Nyanfighters.getInstance().getSpawnpoints();
                    FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
                    if(c.isSet("SpielSpawn."+(i-1))||i==0)
                    {
                        c.set("SpielSpawn."+i+".X", x);
                        c.set("SpielSpawn."+i+".Y", y);
                        c.set("SpielSpawn."+i+".Z", z);
                        c.set("SpielSpawn."+i+".Pitch", pitch);
                        c.set("SpielSpawn."+i+".Yaw", yaw);
                        c.set("SpielSpawn."+i+".World", world);
                        YamlHandler.saveYamlFile(c,f);
                        p.sendMessage("§6[Nyanfighters] §aSpielSpawn "+i+" erfolgreich gesetzt!");
                        return;
                    }
                    else
                    {
                        int k =0;
                        while(c.isSet("SpielSpawn."+k))
                            k++;
                        k--;
                        if(k<0)
                        {
                            p.sendMessage("§6[Nyanfighters]§c Bitte nutze aufeinander folgende Nummern mit 0 beginnend.");

                        }
                        else
                        {
                            p.sendMessage("§6[Nyanfighters]§c Bitte nutze aufeinander folgende Nummern. Die letzte Nummer ist "+(k));

                        }

                        return;
                    }

                }
                else                                                                                                    //falsches 2. Argument
                {
                    p.sendMessage("§6[Nyanfighters]§c Nutzung: /setspawn <Lobby/Spiel> [Nummer]");
                    return;
                }
            }
            else                                                                                                        //falsches 1. Argument
            {
                p.sendMessage("§6[Nyanfighters]§c Nutzung: /setspawn <Lobby/Spiel> [Nummer]");
                p.sendMessage("§6[Nyanfighters]§c Bedenke: um den Lobbyspawn zu setzten, nutze: /setspawn Lobby");
                return;
            }
        }
        else                                                                                                            //zu viele Argumente
        {
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /setspawn <Lobby/Spiel> [Nummer]");
            return;
        }
    }
    @Command(name="spawn", description = "Teleportiert dich zum Spawn", usage = "/spawn <Lobby/Spiel> [Number]", permission = "Nyanfighers.spawn")
    public void commandSpawn(CommandArgs args)
    {
        Player p = args.getPlayer();
        String[] arg = args.getArgs();
        if(arg.length==0) {                                                                                             //kein Argument
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /spawn <Lobby/Spiel> [Nummer]");
            return;
        }
        else if(arg.length==1)                                                                                          //1 Argument
        {
            if(arg[0].equals("Lobby"))                                                                                  //Lobby-Argument
            {
                File f = Nyanfighters.getInstance().getSpawnpoints();
                FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
                if(c.isSet("LobbySpawn"))
                {
                    double x = c.getInt("LobbySpawn.X")+0.5;
                    double y = c.getInt("LobbySpawn.Y");
                    double z = c.getInt("LobbySpawn.Z")+0.5;
                    float pitch = (float)c.getDouble("LobbySpawn.Pitch");
                    float yaw = (float)c.getDouble("LobbySpawn.Yaw");
                    String w = c.getString("LobbySpawn.World");
                    World world = Bukkit.getWorld(w);
                    Location  l = new Location(world, x, y, z, yaw, pitch);
                    p.teleport(l);
                }
                else
                {
                    p.sendMessage("§6[Nyanfighters]§c LobbySpawn wurde nicht gesetzt.");
                    return;
                }
            }
            else                                                                                                        //falsches Argument
            {
                p.sendMessage("§6[Nyanfighters]§c Nutzung: /spawn <Lobby/Spiel> [Nummer]");
                return;
            }
        }
        else if(arg.length==2)                                                                                          //2 Argumente
        {
            if(arg[0].equals("Spiel"))                                                                                  //1. Argument ist Spiel
            {

                int i;
                try {
                    i = Integer.parseInt(arg[1]);
                } catch (NumberFormatException e) {
                    i = -1;
                }
                if (i >= 0) {
                    File f = Nyanfighters.getInstance().getSpawnpoints();
                    FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
                    if(c.isSet("SpielSpawn."+i))
                    {
                        double x = c.getInt("SpielSpawn."+i+".X")+0.5;
                        double y = c.getInt("SpielSpawn."+i+".Y");
                        double z = c.getInt("SpielSpawn."+i+".Z")+0.5;
                        float pitch = (float)c.getDouble("SpielSpawn."+i+".Pitch");
                        float yaw = (float)c.getDouble("SpielSpawn."+i+".Yaw");
                        String w = c.getString("SpielSpawn."+i+".World");
                        World world = Bukkit.getWorld(w);
                        Location  l = new Location(world, x, y, z, yaw, pitch);
                        p.teleport(l);
                    }
                    else
                    {
                        int k=0;
                        while(c.isSet("SpielSpawn."+k))
                            k++;
                        k--;
                        if(k>=0)
                        {
                            p.sendMessage("§6[Nyanfighters]§c SpielSpawn "+i+" wurde nicht gesetzt, alle Nummern bis "+k+" sind vorhanden.");
                        }
                        else
                        {
                            p.sendMessage("§6[Nyanfighters]§c Kein Spielspawn wurde gesetzt.");
                        }

                        return;
                    }
                }
                else
                {
                    p.sendMessage("§6[Nyanfighters]§c Bitte benutze positive Nummern");
                    return;
                }

            }
            else                                                                                                        //falsches 1. Argument
            {
                p.sendMessage("§6[Nyanfighters]§c Nutzung: /spawn <Lobby/Spiel> [Nummer]");
                p.sendMessage("§6[Nyanfighters]§c Bedenke: Um dich zum LobbySpawn zu teleportieren, nutze: /spawn Lobby");
                return;
            }
        }

        else                                                                                                            //zu viele Argumente
        {
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /spawn <Lobby/Spiel> [Nummer]");
            return;
        }

    }
    @Command(name="game", description = "Startet/beendet das Spiel", usage = "/game <start/lobby/end>", permission = "Nyanfighers.game")
    public void commandGamestart(CommandArgs args)
    {
        Player p = args.getPlayer();
        String[] arg = args.getArgs();
        if(arg.length==0)                                                                                               //kein Argument
        {
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /game <start/lobby/end>");
            return;
        }
        else if(arg.length==1)                                                                                          //1 Argument
        {
            if(arg[0].equals("start"))                                                                                  //start Argument
            {
                Nyanfighters.getInstance().getServer().getScheduler().cancelTasks(Nyanfighters.getInstance());
                GameListener.gameWait();
                GameListener.setTime(300);
            }
            else if(arg[0].equals("lobby"))                                                                             //lobby Argument
            {
                Nyanfighters.getInstance().getServer().getScheduler().cancelTasks(Nyanfighters.getInstance());
                Nyanfighters.getInstance().setGame(false);
                GameListener.lobbyWait();
                GameListener.setTime(90);

            }
            else if(arg[0].equals("end"))                                                                               //end Argument
            {
                Nyanfighters.getInstance().getServer().getScheduler().cancelTasks(Nyanfighters.getInstance());
                Nyanfighters.getInstance().setGame(true);
                GameListener.gameWait();
                GameListener.setTime(1);

            }
            else                                                                                                        //falsches Argument
            {
                p.sendMessage("§6[Nyanfighters]§c Nutzung: /game <start/lobby/end>");
                return;
            }

        }
        else                                                                                                            //zu viele Argumente
        {
            p.sendMessage("§6[Nyanfighters]§c Nutzung: /game <start/lobby/end>");
            return;
        }
    }
    @Command(name="stats", description = "zeigt die Stats", usage = "/stats", permission ="Nyanfighters.stats")
    public void commandstats(CommandArgs args)
    {
        Player p =args.getPlayer();
        String[] arg = args.getArgs();
        if(arg.length == 0) {                                                                                           //kein Argument
            p.sendMessage("§7---====§8[§6Stats§8]§7====---");
            p.sendMessage("§a§lKills: §r§e" + Stats.getKills(p.getUniqueId().toString()));
            p.sendMessage("§c§lTode: §r§e" + Stats.getDeaths(p.getUniqueId().toString()));
            p.sendMessage("§7---====§8[§6Stats§8]§7====---");
        }
        ScoreboardUtil.updateScoreboard(p);
        return;
    }
}
