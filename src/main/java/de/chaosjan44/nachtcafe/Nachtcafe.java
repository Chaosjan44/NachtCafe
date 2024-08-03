package de.chaosjan44.nachtcafe;

import de.chaosjan44.nachtcafe.Listener.JoinLeaveListener;
import de.chaosjan44.nachtcafe.Util.LuckPermsWorker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nachtcafe extends JavaPlugin {

    public static Nachtcafe INSTANCE;
    public Nachtcafe() {
        INSTANCE = this;
    }
    public static LuckPerms luckPerms = null;

    public static Component PREFIX = (Component.text("Nacht").color(TextColor.fromCSSHexString("#C849FF"))
            .append(Component.text("Cafe").color(TextColor.fromCSSHexString("#0CFFB6")))
            .append(Component.text(" » ").color(NamedTextColor.DARK_GRAY)));
    private LuckPermsWorker luckPermsWorker;


    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupLuckPerms()) {
            getComponentLogger().error(PREFIX.append(Component.text("Can't get ahold of LuckPerms - disabling now.").color(NamedTextColor.DARK_RED)));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        registration(Bukkit.getPluginManager());
        getComponentLogger().info(PREFIX.append(Component.text("Successfully enabled.").color(NamedTextColor.GREEN)));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getComponentLogger().info(PREFIX.append(Component.text("Successfully disabled.").color(NamedTextColor.GREEN)));
    }


    private boolean setupLuckPerms() {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            return false;
        }
        RegisteredServiceProvider<LuckPerms> rsp = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (rsp == null) {
            return false;
        }
        luckPerms = rsp.getProvider();
        return true;
    }


    public void registration(PluginManager pluginManager) {
        luckPermsWorker = new LuckPermsWorker(this);
        pluginManager.registerEvents(new JoinLeaveListener(this), this);
    }

    public LuckPermsWorker getLuckPermsWorker() {return luckPermsWorker;}
    public LuckPerms getLuckPerms() {return luckPerms;}
}
