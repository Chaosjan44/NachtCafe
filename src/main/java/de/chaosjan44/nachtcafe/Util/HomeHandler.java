package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HomeHandler {

    private final Nachtcafe plugin;
    public HomeHandler(Nachtcafe plugin) {this.plugin = plugin;}

    public HashMap<Player, List<HomeItem>> playerHomes = new HashMap<>();
    public HashMap<Player, List<String>> playerHomesList = new HashMap<>();
    public HashMap<Player, Integer> playerHomesCount = new HashMap<>();

    public void loadPlayerHomes(Player player) {
        playerHomes.remove(player);
        playerHomesCount.remove(player);
        playerHomesList.remove(player);
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        userData.reloadConfig();
        List<String> homelist = userData.getUserFile().getStringList("User.Homes");
        playerHomesList.put(player, homelist);
        Integer homesCount = 0;
        if (!homelist.isEmpty()) {
           List<HomeItem> homes = new LinkedList<>();
            for (String home : homelist) {
                homes.add(new HomeItem(home, getHomeLocation(player, home)));
                homesCount++;
            }
            playerHomes.put(player, homes);
        }
        playerHomesCount.put(player, homesCount);
    }

    public void loadAllOnlinePlayerHomes() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            loadPlayerHomes(p);
        }
    }

    public Location getHomeLocation(Player player, String home) {
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        userData.reloadConfig();
        World world = Bukkit.getWorld(Objects.requireNonNull(userData.getUserFile().getString("User.Home." + home + ".world")));
        double x = userData.getUserFile().getDouble("User.Home." + home + ".x"),
                y = userData.getUserFile().getDouble("User.Home." + home + ".y"),
                z = userData.getUserFile().getDouble("User.Home." + home + ".z");
        float yaw = (float) userData.getUserFile().getDouble("User.Home." + home + ".yaw"),
                pitch = (float) userData.getUserFile().getDouble("User.Home." + home + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void addHome(Player player, String homename, Location location) {
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        if (playerHomesList.get(player) == null || !playerHomesList.get(player).contains(homename)) {
            playerHomesList.get(player).add(homename);
            userData.getUserFile().set("User.Homes", playerHomesList.get(player));
        }
        userData.getUserFile().set("User.Home." + homename + "." + "world", location.getWorld().getName());
        userData.getUserFile().set("User.Home." + homename + "." + "x", location.getX());
        userData.getUserFile().set("User.Home." + homename + "." + "y", location.getY());
        userData.getUserFile().set("User.Home." + homename + "." + "z", location.getZ());
        userData.getUserFile().set("User.Home." + homename + "." + "yaw", location.getYaw());
        userData.getUserFile().set("User.Home." + homename + "." + "pitch", location.getPitch());
        userData.saveUserFile();
        loadPlayerHomes(player);
    }

    public void delHome(Player player, String homename) {
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        userData.getUserFile().set("User.Home." + homename, null);
        playerHomesList.get(player).remove(homename);
        userData.getUserFile().set("User.Homes", playerHomesList.get(player));
        userData.saveUserFile();
        loadPlayerHomes(player);
    }

    public void home(Player player, String home) {
        if (player.hasPermission("nachtcafe.home.instant")) {
            player.teleport(loadLocation(player, home));
            player.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleported you to home ").color(NamedTextColor.GRAY))
                    .append(Component.text(home).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
        } else {
            CountdownTimer timer = new CountdownTimer(plugin, 3,
                    () -> {
                        player.teleport(loadLocation(player, home));
                        player.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("Teleported you to home ").color(NamedTextColor.GRAY))
                                .append(Component.text(home).color(TextColor.fromCSSHexString("#C849FF")))
                                .append(Component.text(".").color(NamedTextColor.GRAY)));
                    }, (t) -> player.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleporting you to home ").color(NamedTextColor.GRAY))
                    .append(Component.text(home).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" in ").color(NamedTextColor.GRAY))
                    .append(Component.text(t.getSecondsLeft()).color(TextColor.fromCSSHexString("#0CFFB6")))
                    .append(Component.text(" seconds.").color(NamedTextColor.GRAY)))); timer.scheduleTimer();
        }
    }

    public Location loadLocation(Player player, String home){
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        World world = Bukkit.getWorld(Objects.requireNonNull(userData.getUserFile().getString("User.Home." + home + ".world")));
        double x = userData.getUserFile().getDouble("User.Home." + home + ".x"),
                y = userData.getUserFile().getDouble("User.Home." + home + ".y"),
                z = userData.getUserFile().getDouble("User.Home." + home + ".z");
        float yaw = (float) userData.getUserFile().getDouble("User.Home." + home + ".yaw"),
                pitch = (float) userData.getUserFile().getDouble("User.Home." + home + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void disableHomes() {
        playerHomesList.clear();
        playerHomes.clear();
        playerHomesCount.clear();
    }

}
