package de.minecrafthaifl.nyanfighters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardUtil {

    public static void sendScoreboard(Player p) {

        String name = p.getUniqueId().toString();

        Scoreboard board;
        Objective obj;
        ScoreboardManager sm;

        sm = Bukkit.getScoreboardManager();
        board = sm.getNewScoreboard();
        obj = board.registerNewObjective("aba", "bab");

        obj.setDisplayName("§6[NyanFighters]");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("§a§lKills: ").setScore(Stats.getKills(name));
        obj.getScore("§c§lDeaths: ").setScore(Stats.getDeaths(name));

        p.setScoreboard(board);
    }

    public static void updateScoreboard(Player p) {
        ScoreboardUtil.sendScoreboard(p);
    }
}
