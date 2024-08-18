package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class UserDataHandler {

    protected final Nachtcafe plugin;
    UUID u;
    File userFile;
    FileConfiguration userConfig;

    public UserDataHandler(Nachtcafe plugin, UUID u) {
        this.plugin = plugin;
        this.u = u;
        userFile = new File(plugin.getDataFolder(), "userdata/" + u + ".yml");
        userConfig = YamlConfiguration.loadConfiguration(userFile);
    }

    public void createUser(final Player player){
        if ( !(userFile.exists()) ) {
            try {
                YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(userFile);
                UserConfig.set("User.Info.PreviousName", player.getName());
                UserConfig.set("User.Info.FirstJoin", "1");
                UserConfig.set("User.Info.UniqueID", player.getUniqueId().toString());
                UserConfig.save(userFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration getUserFile(){
        return userConfig;
    }

    public void saveUserFile() {
        try {
            getUserFile().save(userFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        if (this.userFile == null)
            this.userFile = new File(plugin.getDataFolder(), "userdata/" + u + ".yml");

        this.userConfig = YamlConfiguration.loadConfiguration(this.userFile);

        InputStream defaultStream = this.plugin.getResource("userdata/" + u + ".yml");
        if (defaultStream != null) {
            YamlConfiguration UserConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.userConfig.setDefaults(UserConfig);
        }
    }
}
