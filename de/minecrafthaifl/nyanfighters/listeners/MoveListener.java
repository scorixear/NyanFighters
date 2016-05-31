package de.minecrafthaifl.nyanfighters.listeners;

import com.darkblade12.particleeffect.ParticleEffect;
import com.sun.xml.internal.ws.util.HandlerAnnotationInfo;
import de.minecrafthaifl.nyanfighters.Nyanfighters;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Paul on 02.05.2016.
 */
public class MoveListener implements Listener {
    private static HashMap<String, Integer> playerTask = new HashMap<String, Integer>();
    private static HashMap<String, Double> rotation = new HashMap<String, Double>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (JoinListener.getCP().contains(e.getPlayer().getUniqueId().toString())) {                                    //Spieler ist kein Spec
            Player p = e.getPlayer();
            Location l = e.getTo();
            List<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
            players.remove(p);

            if (Nyanfighters.getInstance().getNoMove()) {                                                               //Vorbereitungszeit ist aktiv
                e.setCancelled(true);
                return;
            } else if (!Nyanfighters.getInstance().getNoBlocks() && Nyanfighters.getInstance().getGame() && !(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())) {
                //Specialitem Skyfall ist nicht aktiv, Spiel läuft und Spieler hat sich über einen Block hinaus bewegt (unnötiges Blocksetzen verhindert)
                if ((l.getYaw() <= -45 && l.getYaw() > -135.0) || (l.getYaw() <= 305 && l.getYaw() > 215)) {            //Einstellen der Farben des Regenbogens, abhängig vom Yaw
                    setArea2(-1, 1, l, e.getPlayer());
                } else if ((l.getYaw() <= -305 || (l.getYaw() > -45 && l.getYaw() <= 0)) || ((l.getYaw() <= 45 && l.getYaw() >= 0) || l.getYaw() > 305)) {
                    setArea(1, 1, l, e.getPlayer());
                } else if ((l.getYaw() <= -215 && l.getYaw() > -305) || (l.getYaw() <= 135 && l.getYaw() > 45)) {
                    setArea2(-1, 1, l, e.getPlayer());
                } else {
                    setArea(-1, -1, l, e.getPlayer());
                }
            }
            if (playerTask.containsKey(p.getUniqueId().toString())) {                                                   //Die Spiralen setzten, falls welche schon vorhanden sind
                Bukkit.getScheduler().cancelTask(playerTask.get(p.getUniqueId().toString()));                           //aktuellen Schedule abbrechen
                playerTask.remove(p.getUniqueId().toString());
                rotation.remove(p.getUniqueId().toString());
                rotation.put(p.getUniqueId().toString(), 0.0);
                playerTask.put(p.getUniqueId().toString(), Bukkit.getScheduler().runTaskTimer(Nyanfighters.getInstance(), new Runnable() {//Rotation der Spirale anstellen/Spirale setzten
                    @Override
                    public void run() {
                        for (double j = 0.0; j <= 720.0; j = j + 20.0) {
                            double x = p.getLocation().getX() + Math.cos(Math.toRadians(j + rotation.get(p.getUniqueId().toString()))) * 1.0;
                            double y = p.getLocation().getY() + ((j / 2) / 360.0) * 2.0;
                            double z = p.getLocation().getZ() + Math.sin(Math.toRadians(j + rotation.get(p.getUniqueId().toString()))) * 1.0;
                            Location k = new Location(p.getWorld(), x, y, z);
                            if (players.size() != 0) {
                                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, k, players);
                            } else {
                                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, k, 10);
                            }
                        }
                        double rotation2 = rotation.get(p.getUniqueId().toString()) + 5.0;
                        if (rotation2 == 360) {
                            rotation2 = 0.0;
                        }
                        rotation.remove(p.getUniqueId().toString());
                        rotation.put(p.getUniqueId().toString(), rotation2);
                    }
                }, 0L, 2L).getTaskId());
            } else {                                                                                                    //Spirale existierte noch nicht
                rotation.put(p.getUniqueId().toString(), 0.0);
                playerTask.put(p.getUniqueId().toString(), Bukkit.getScheduler().runTaskTimer(Nyanfighters.getInstance(), new Runnable() {//Rotation anstellen/Spirale setzten
                    @Override
                    public void run() {
                        for (double j = 0.0; j <= 720.0; j = j + 20.0) {
                            double x = p.getLocation().getX() + Math.cos(Math.toRadians(j + rotation.get(p.getUniqueId().toString()))) * 1.0;
                            double y = p.getLocation().getY() + ((j / 2) / 360.0) * 2.0;
                            double z = p.getLocation().getZ() + Math.sin(Math.toRadians(j + rotation.get(p.getUniqueId().toString()))) * 1.0;
                            Location k = new Location(p.getWorld(), x, y, z);

                            if (players.size() != 0) {
                                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, k, players);
                            } else {
                                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, k, 10);
                            }
                        }
                        double rotation2 = rotation.get(p.getUniqueId().toString()) + 10.0;
                        if (rotation2 == 360) {
                            rotation2 = 0.0;
                        }
                        rotation.remove(p.getUniqueId().toString());
                        rotation.put(p.getUniqueId().toString(), rotation2);
                    }
                }, 0L, 2L).getTaskId());
            }
        }
        else {                                                                                                          //Spieler ist Spectator

            for(Entity ef: e.getPlayer().getNearbyEntities(5,5,5))                                                      //Für alle nahen Entitys im radium von 5 Blöcken
            {
                if(ef instanceof Player&&JoinListener.getCP().contains(((Player)ef).getUniqueId().toString()))          //Entity ist Player und kein Spectator
                {
                    Vector velo = e.getPlayer().getLocation().toVector().subtract(((Player)ef).getLocation().toVector()).normalize();//Spieler zurückschubsen
                    e.getPlayer().setVelocity(velo);


                }
            }

        }
    }

    public void setArea(int x, int z, Location start, Player p) {                                                       //setzt den Regenbogen. x und z sind jeweils die Verschiebungen,
        if (p.getLocation().getPitch() > 65) {                                      //setzt Luft                        //würde man den linken Block (x) und den vorderen Block(z), um eine Längeneinheit
            setAir("start", 0, 0, start, 4);                                                                            //verschoben, betrachten
            setAir("left", x, 0, start, 1);
            setAir("lefttwice", x * 2, 0, start, 14);
            setAir("right", -x, 0, start, 5);
            setAir("righttwice", (-x) * 2, 0, start, 9);
            setAir("front", 0, z, start, 4);
            setAir("back", 0, -z, start, 4);
            setAir("leftfront", x, z, start, 1);
            setAir("lefttwicefront", x * 2, z, start, 14);
            setAir("rightfront", -x, z, start, 5);
            setAir("righttwicefront", (-x) * 2, z, start, 9);
            setAir("leftback", x, -z, start, 1);
            setAir("lefttwiceback", x * 2, -z, start, 14);
            setAir("rightback", -x, -z, start, 5);
            setAir("righttwiceback", (-x) * 2, -z, start, 9);
        } else {                                                                    //setzt Blöcke
            setBlocks("start", 0, 0, start, 4);
            setBlocks("left", x, 0, start, 1);
            setBlocks("lefttwice", x * 2, 0, start, 14);
            setBlocks("right", -x, 0, start, 5);
            setBlocks("righttwice", (-x) * 2, 0, start, 9);
            setBlocks("front", 0, z, start, 4);
            setBlocks("back", 0, -z, start, 4);
            setBlocks("leftfront", x, z, start, 1);
            setBlocks("lefttwicefront", x * 2, z, start, 14);
            setBlocks("rightfront", -x, z, start, 5);
            setBlocks("righttwicefront", (-x) * 2, z, start, 9);
            setBlocks("leftback", x, -z, start, 1);
            setBlocks("lefttwiceback", x * 2, -z, start, 14);
            setBlocks("rightback", -x, -z, start, 5);
            setBlocks("righttwiceback", (-x) * 2, -z, start, 9);
        }
    }

    public void setArea2(int x, int z, Location start, Player p) {                                                      //setzt den Regenbogen. x und z sind jeweils die Verschiebungen,
        if (p.getLocation().getPitch() > 65) {                                                                          //würde man den linken Block (z) und den vorderen Block(x), um eine Längeneinheit
            setAir("start", 0, 0, start, 4);                                                                            //verschoben, betrachten
            setAir("left", 0, x, start, 1);
            setAir("lefttwice", 0, x * 2, start, 14);
            setAir("right", 0, -x, start, 5);
            setAir("righttwice", 0, (-x) * 2, start, 9);
            setAir("front", z, 0, start, 4);
            setAir("back", -z, 0, start, 4);
            setAir("leftfront", z, x, start, 1);
            setAir("lefttwicefront", z, x * 2, start, 14);
            setAir("rightfront", z, -x, start, 5);
            setAir("righttwicefront", z, (-x) * 2, start, 9);
            setAir("leftback", -z, x, start, 1);
            setAir("lefttwiceback", -z, x * 2, start, 14);
            setAir("rightback", -z, -x, start, 5);
            setAir("righttwiceback", -z, (-x) * 2, start, 9);
        } else {
            setBlocks("start", 0, 0, start, 4);
            setBlocks("left", 0, x, start, 1);
            setBlocks("lefttwice", 0, x * 2, start, 14);
            setBlocks("right", 0, -x, start, 5);
            setBlocks("righttwice", 0, (-x) * 2, start, 9);
            setBlocks("front", z, 0, start, 4);
            setBlocks("back", -z, 0, start, 4);
            setBlocks("leftfront", z, x, start, 1);
            setBlocks("lefttwicefront", z, x * 2, start, 14);
            setBlocks("rightfront", z, -x, start, 5);
            setBlocks("righttwicefront", z, (-x) * 2, start, 9);
            setBlocks("leftback", -z, x, start, 1);
            setBlocks("lefttwiceback", -z, x * 2, start, 14);
            setBlocks("rightback", -z, -x, start, 5);
            setBlocks("righttwiceback", -z, (-x) * 2, start, 9);
        }
    }

    public void setBlocks(String s, int x, int z, Location start, int id) {                                             //setzt einen Block mit den jeweiligen Verschiebungen ausgehend von einer Startlocation und einer ID des Blockes (nur Fakeblocks)
        Location left = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() - 1, start.getBlockZ() + z);
        Location leftair = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY(), start.getBlockZ() + z);
        if (leftair.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair, Material.AIR, (byte) id);
        }
        Location leftair2 = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() + 1, start.getBlockZ() + z);
        if (leftair2.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair2, Material.AIR, (byte) id);
        }

        Location leftair3 = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() + 2, start.getBlockZ());
        if (leftair3.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair3, Material.AIR, (byte) id);
        }
        Material block = left.getBlock().getType();
        byte data = left.getBlock().getData();
        if (block == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(left, Material.STAINED_GLASS, (byte) id);
            removeBlock(start, left, x, -1, z, block, data);
        }
    }

    public void setAir(String s, int x, int z, Location start, int id) {                                                //setzt Luft mit den jeweiligen Verschiebungen ausgehend von einer Startlocation (nur Fakeblocks)

        Location left = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() - 1, start.getBlockZ() + z);
        if (left.getBlock().getType() == Material.AIR)
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(left, Material.AIR, (byte) id);
        Location leftair = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY(), start.getBlockZ() + z);
        if (leftair.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair, Material.AIR, (byte) id);
        }
        Location leftair2 = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() + 1, start.getBlockZ() + z);
        if (leftair2.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair2, Material.AIR, (byte) id);
        }
        Location leftair3 = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() + 2, start.getBlockZ());
        if (leftair3.getBlock().getType() == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(leftair3, Material.AIR, (byte) id);
        }
        Location right = new Location(start.getWorld(), start.getBlockX() + x, start.getBlockY() - 2, start.getBlockZ() + z);
        Material block = right.getBlock().getType();
        byte data = right.getBlock().getData();
        if (block == Material.AIR) {
            for (Player p : Bukkit.getOnlinePlayers())
                p.sendBlockChange(right, Material.STAINED_GLASS, (byte) id);
            removeBlock(start, right, x, -2, z, block, data);
        }
    }

    public void removeBlock(Location start, Location remove, int x, int y, int z, Material block, byte data) {          //entfernt den Block nach 5 Sekunden (Fakeblocks)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Nyanfighters.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers())
                    p.sendBlockChange(remove, Material.AIR, (byte) data);
            }
        }, 20 * 5);
    }

}
