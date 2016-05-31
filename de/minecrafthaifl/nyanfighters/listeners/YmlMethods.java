package de.minecrafthaifl.nyanfighters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * Created by Paul on 28.04.2016.
 */
public class YmlMethods
{
    public static Location getLobbySpawn()                                                                              //Liefert den LobbySpawn
    {
        File f = Nyanfighters.getInstance().getSpawnpoints();
        FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
        double x = c.getInt("LobbySpawn.X")+0.5;
        double y = c.getInt("LobbySpawn.Y");
        double z = c.getInt("LobbySpawn.Z")+0.5;
        float pitch = (float)c.getDouble("LobbySpawn.Pitch");
        float yaw = (float)c.getDouble("LobbySpawn.Yaw");
        String w = c.getString("LobbySpawn.World");
        World world = Bukkit.getWorld(w);
        Location  l = new Location(world, x, y, z, yaw, pitch);

        return l;

    }
    public static Location getSpielSpawn(int n)                                                                         //Liefert einen SpielSpawn
    {
        File f = Nyanfighters.getInstance().getSpawnpoints();
        FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();

        double x = c.getInt("SpielSpawn."+n+".X")+0.5;
        double y = c.getInt("SpielSpawn."+n+".Y");
        double z = c.getInt("SpielSpawn."+n+".Z")+0.5;
        float pitch = (float)c.getDouble("SpielSpawn."+n+".Pitch");
        float yaw = (float)c.getDouble("SpielSpawn."+n+".Yaw");
        String w = c.getString("SpielSpawn."+n+".World");
        World world = Bukkit.getWorld(w);
        Location  l = new Location(world, x, y, z, yaw, pitch);

        return l;
    }
}
