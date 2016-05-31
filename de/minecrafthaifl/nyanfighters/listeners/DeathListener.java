package de.minecrafthaifl.nyanfighters.listeners;

import de.minecrafthaifl.nyanfighters.*;
import org.apache.commons.codec.language.Soundex;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/**
 * Created by Paul on 11.05.2016.
 */
public class DeathListener implements Listener
{
    @EventHandler
    public void onDeath(PlayerDeathEvent e)
    {
        e.setDeathMessage("");
        if (JoinListener.getCP().contains(((Player)e.getEntity()).getUniqueId().toString())) {
            Player player = (Player) e.getEntity();
            player.spigot().respawn();
            e.setKeepInventory(true);
            player.setHealth(20);
            int k = 0;
            FileConfiguration c = Nyanfighters.getInstance().getSpawnpointsConfi();
            while (c.isSet("SpielSpawn." + k))
                k++;
            k--;
            int random = (int) Math.random() * k;
            player.teleport(YmlMethods.getSpielSpawn(k));

            FileConfiguration playersConfi = Nyanfighters.getInstance().getPlayersConfi();
            FileConfiguration statsConfi = Nyanfighters.getInstance().getStatsConfi();
            if (statsConfi.isSet(player.getUniqueId().toString() + ".Deaths")) {
                Stats.addDeaths(player.getUniqueId().toString(), 1);
            } else {
                Stats.setDeaths(player.getUniqueId().toString(), 1);
            }

            if (playersConfi.isSet(player.getUniqueId().toString() + ".Attacker")) {
                if (statsConfi.isSet(playersConfi.getString(player.getUniqueId().toString() + ".Attacker") + ".Kills")) {
                    Stats.addKills(playersConfi.getString(player.getUniqueId().toString() + ".Attacker"), 1);
                } else {
                    Stats.setKills(playersConfi.getString(player.getUniqueId().toString() + ".Attacker"), 1);
                }
                ScoreboardUtil.updateScoreboard(Bukkit.getPlayer(UUID.fromString(playersConfi.getString(player.getUniqueId().toString() + ".Attacker"))));

            }
            ScoreboardUtil.updateScoreboard(player);

            ItemStack slime = new ItemStack(Material.SLIME_BLOCK);
            ItemMeta slimem = slime.getItemMeta();
            slimem.setDisplayName("§r§aSprungball");
            slime.setItemMeta(slimem);

            player.getInventory().clear();
            player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
            player.getInventory().setItemInOffHand(new ItemStack(Material.GOLDEN_APPLE, 16));
            player.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
            player.getInventory().setItem(2, slime);
            if (playersConfi.isSet(player.getUniqueId().toString() + ".Attacker")) {
                String uuid = playersConfi.getString(player.getUniqueId().toString() + ".Attacker");
                UUID u = UUID.fromString(uuid);
                String Attacker = Bukkit.getPlayer(u).getDisplayName();
                Bukkit.getPlayer(u).playSound(Bukkit.getPlayer(u).getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                Bukkit.broadcastMessage("§6[NyanFighters] §c" + player.getDisplayName() + " §7wurde von §a " + Attacker + " §7getötet.");
                playersConfi.set(player.getUniqueId().toString() + ".Attacker", null);
                YamlHandler.saveYamlFile(playersConfi, Nyanfighters.getInstance().getPlayers());
            } else {
                Bukkit.broadcastMessage("§6[NyanFighters] §c" + player.getDisplayName() + " §7 ist gestorben.");
            }

        }
    }

}
