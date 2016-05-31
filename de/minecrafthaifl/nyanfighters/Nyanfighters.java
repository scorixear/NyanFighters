package de.minecrafthaifl.nyanfighters;

import com.minnymin.command.CommandFramework;
import de.minecrafthaifl.nyanfighters.commands.NyanfightersCommands;
import de.minecrafthaifl.nyanfighters.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Paul on 27.04.2016.
 */
public class Nyanfighters extends JavaPlugin
{
    private static Nyanfighters plugin;                                                                                 //Klasse Nyanfighters
    private CommandFramework framework;                                                                                 //framework für die Commands
    private File spawnpoints;                                                                                           //Spawnpointsfile
    private FileConfiguration spawnpointsc;
    private File players;                                                                                               //Attacker-File
    private FileConfiguration playersc;
    private File stats;                                                                                                 //Stats-File
    private FileConfiguration statsc;
    private boolean game;                                                                                               //Spiel-Variable
    private boolean nomove;                                                                                             //Vorbereitung-Variable
    private boolean noblocks;                                                                                           //skyfall-Variable
    public void onEnable()
    {
        game=false;
        nomove=false;
        noblocks=false;
        plugin = this;
        framework = new CommandFramework(this);
        framework.registerCommands(new NyanfightersCommands());
        spawnpoints = YamlHandler.createFile("spawnpoints.yml");
        spawnpointsc = YamlHandler.createYamlFile(spawnpoints);
        players = YamlHandler.createTempFile("players.yml");
        playersc = YamlHandler.createYamlFile(players);
        stats= YamlHandler.createTempFile("stats.yml");
        statsc= YamlHandler.createYamlFile(stats);
        registerListener();
        GameListener.lobbyWait();
    }
    public void onDisable()
    {
        for(Player p: Bukkit.getOnlinePlayers())                                                                        //Notwendig wegen der Spectator-Collection
        {
            p.kickPlayer("Reload!");
        }
    }
    public static Nyanfighters getInstance()
    {
        return plugin;
    }
    public File getSpawnpoints()
    {
        return spawnpoints;
    }
    public FileConfiguration getSpawnpointsConfi()
    {
        return spawnpointsc;
    }
    public File getPlayers()
    {
        return players;
    }
    public FileConfiguration getPlayersConfi()
    {
        return playersc;
    }
    public File getStats()
    {
        return stats;
    }
    public FileConfiguration getStatsConfi()
    {
        return statsc;
    }
    public boolean getGame() {return game;}
    public void setGame(boolean g){game=g;}
    public void setNoMove(boolean g){nomove=g;}
    public boolean getNoMove() {return nomove;}
    public void setNoBlocks(boolean b){noblocks=b;}
    public boolean getNoBlocks() {return noblocks;}
    public void registerListener()
    {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new AttackListener(),this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHitListener(), this);
        Bukkit.getPluginManager().registerEvents(new RightClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new LeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new FallDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }
}
