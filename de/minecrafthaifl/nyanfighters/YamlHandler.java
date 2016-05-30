package de.minecrafthaifl.nyanfighters;
/**
 * API for easy YML-Configuration
 */
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlHandler
{
    public static File createTempFile(String filename)
    {

        File c = new File(Nyanfighters.getInstance().getDataFolder().getAbsolutePath());
        c.mkdir();
        File f= new File(Nyanfighters.getInstance().getDataFolder().getAbsolutePath() + File.separator + filename);

        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            f.delete();
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return f;
    }
    public static File createFile(String filename)
    {

        File c = new File(Nyanfighters.getInstance().getDataFolder().getAbsolutePath());
        c.mkdir();
        File f= new File(Nyanfighters.getInstance().getDataFolder().getAbsolutePath() + File.separator + filename);

        if(!f.exists())
        {
            try {
                f.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return f;
    }
    public static FileConfiguration createYamlFile(File f)
    {
        FileConfiguration fc= YamlConfiguration.loadConfiguration(f);

        return fc;
    }

    public static void saveYamlFile(FileConfiguration c,File f)
    {
        try {
            c.save(f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}