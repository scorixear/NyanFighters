package de.minecrafthaifl.nyanfighters;


import org.bukkit.Bukkit;

public class Stats {

    // --------------------Kills--------------------\\

    public static void setKills(String name, int amount) {

        Nyanfighters.getInstance().getStatsConfi().set(name + ".Kills", amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());

    }

    public static void addKills(String name, int amount) {
        Bukkit.broadcastMessage(getKills(name)+"");
        Bukkit.broadcastMessage((getKills(name)+amount)+"");
        Nyanfighters.getInstance().getStatsConfi().set(name + ".Kills", getKills(name) + amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
    }

    public static void removeKills(String name, int amount) {

        Nyanfighters.getInstance().getStatsConfi().set(name + ".Kills", getKills(name) - amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
    }

    public static Integer getKills(String name) {

        return Nyanfighters.getInstance().getStatsConfi().getInt(name + ".Kills");
    }

    // --------------------Kills--------------------\\
    // --------------------Tode--------------------\\

    public static void setDeaths(String name, int amount) {

        Nyanfighters.getInstance().getStatsConfi().set(name + ".Deaths", amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
    }

    public static void addDeaths(String name, int amount) {

        Nyanfighters.getInstance().getStatsConfi().set(name + ".Deaths", getDeaths(name) + amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
    }

    public static void removeDeaths(String name, int amount) {

        Nyanfighters.getInstance().getStatsConfi().set(name + ".Deaths", getDeaths(name) - amount);
        YamlHandler.saveYamlFile(Nyanfighters.getInstance().getStatsConfi(), Nyanfighters.getInstance().getStats());
    }

    public static Integer getDeaths(String name) {

        return Nyanfighters.getInstance().getStatsConfi().getInt(name + ".Deaths");
    }

    // --------------------Tode--------------------\\
}
