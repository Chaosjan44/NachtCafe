package de.chaosjan44.nachtcafe;

import de.chaosjan44.nachtcafe.Commands.AfkCommand;
import de.chaosjan44.nachtcafe.Commands.Home.DelhomeCommand;
import de.chaosjan44.nachtcafe.Commands.Home.HomeCommand;
import de.chaosjan44.nachtcafe.Commands.Home.HomesCommand;
import de.chaosjan44.nachtcafe.Commands.Home.SethomeCommand;
import de.chaosjan44.nachtcafe.Commands.Util.TabCompleter;
import de.chaosjan44.nachtcafe.Commands.Warp.*;
import de.chaosjan44.nachtcafe.Commands.WbCommand;
import de.chaosjan44.nachtcafe.Configs.WarpConfig;
import de.chaosjan44.nachtcafe.Listener.ChatListener;
import de.chaosjan44.nachtcafe.Listener.JoinLeaveListener;
import de.chaosjan44.nachtcafe.Listener.PlayerMoveListener;
import de.chaosjan44.nachtcafe.Util.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Nachtcafe extends JavaPlugin {

    public Nachtcafe() {
        INSTANCE = this;
    }
    public static Nachtcafe INSTANCE;
    public static Component PREFIX = (Component.text("N").color(TextColor.fromCSSHexString("#C849FF"))
            .append(Component.text("a").color(TextColor.fromCSSHexString("#B160F6")))
            .append(Component.text("c").color(TextColor.fromCSSHexString("#9977ED")))
            .append(Component.text("h").color(TextColor.fromCSSHexString("#828DE4")))
            .append(Component.text("t").color(TextColor.fromCSSHexString("#6AA4DB")))
            .append(Component.text("C").color(TextColor.fromCSSHexString("#53BBD1")))
            .append(Component.text("a").color(TextColor.fromCSSHexString("#3BD2C8")))
            .append(Component.text("f").color(TextColor.fromCSSHexString("#24E8BF")))
            .append(Component.text("e").color(TextColor.fromCSSHexString("#0CFFB6")))
            .append(Component.text(" » ").color(NamedTextColor.DARK_GRAY)));

    public static LuckPerms luckPerms = null;

    private WarpConfig warpConfig;

    private LuckPermsWorker luckPermsWorker;
    private WarpHandler warpHandler;
    private AFKHandler afkHandler;
    private HomeHandler homeHandler;
    Timer timer = new Timer(this);


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
        afkHandler.stopAFKTimer();
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
        // register config getters
        warpConfig = new WarpConfig(this);

        // register listeners
        pluginManager.registerEvents(new JoinLeaveListener(this), this);
        pluginManager.registerEvents(new ChatListener(this),this);
        pluginManager.registerEvents(new PlayerMoveListener(this), this);

        // register comands
        // Warp Stuff
        Objects.requireNonNull(this.getCommand("warp")).setExecutor(new WarpCommand(this));
        Objects.requireNonNull(this.getCommand("warp")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("warps")).setExecutor(new WarpsCommand(this));
        Objects.requireNonNull(this.getCommand("warps")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new SetwarpCommand(this));
        Objects.requireNonNull(this.getCommand("setwarp")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("delwarp")).setExecutor(new DelwarpCommand(this));
        Objects.requireNonNull(this.getCommand("delwarp")).setTabCompleter(new TabCompleter(this));

        //AFK Stuff
        Objects.requireNonNull(this.getCommand("afk")).setExecutor(new AfkCommand(this));
        Objects.requireNonNull(this.getCommand("afk")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("wb")).setExecutor(new WbCommand(this));
        Objects.requireNonNull(this.getCommand("wb")).setTabCompleter(new TabCompleter(this));

        // Home Stuff
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new SethomeCommand(this));
        Objects.requireNonNull(this.getCommand("sethome")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("delhome")).setExecutor(new DelhomeCommand(this));
        Objects.requireNonNull(this.getCommand("delhome")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("homes")).setExecutor(new HomesCommand(this));
        Objects.requireNonNull(this.getCommand("homes")).setTabCompleter(new TabCompleter(this));

        Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommand(this));
        Objects.requireNonNull(this.getCommand("home")).setTabCompleter(new TabCompleter(this));

        // register utils
        luckPermsWorker = new LuckPermsWorker(this);
        warpHandler = new WarpHandler(this);
        afkHandler = new AFKHandler(this);
        homeHandler = new HomeHandler(this);

        // load warps
        warpHandler.loadWarpList();
        afkHandler.startAFKTimer();
        homeHandler.loadAllOnlinePlayerHomes();
    }
    public LuckPerms getLuckPerms() {return luckPerms;}

    public WarpConfig getWarpConfig() {return warpConfig;}

    public LuckPermsWorker getLuckPermsWorker() {return luckPermsWorker;}
    public WarpHandler getWarpHandler() {return warpHandler;}
    public AFKHandler getAfkHandler() {return afkHandler;}
    public HomeHandler getHomeHandler() {return homeHandler;}
    public Timer getTimer() {return timer;}
}
