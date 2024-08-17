package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Configs.WarpConfig;
import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class WarpHandler {

    private final Nachtcafe plugin;
    public WarpHandler(Nachtcafe plugin) { this.plugin = plugin; }

    public List<String> warps;

    public void loadWarpList() {
        WarpConfig warpConfig = plugin.getWarpConfig();
        warps = warpConfig.getConfig().getStringList("warps.list");
    }

    public void warp(Player player, String warp) {
        UserDataHandler userData = new UserDataHandler(plugin, player.getUniqueId());
        if (player.hasPermission("nachtcafe.warp.instant") || Objects.requireNonNull(userData.getUserFile().getString("User.Info.FirstJoin")).equalsIgnoreCase("1")) {
            player.teleport(loadLocation(warp));
            player.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleported you to warp ").color(NamedTextColor.GRAY))
                    .append(Component.text(warp).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
        } else {
            CountdownTimer timer = new CountdownTimer(plugin, 3,
                    () -> {
                        player.teleport(loadLocation(warp));
                        player.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("Teleported you to warp ").color(NamedTextColor.GRAY))
                                .append(Component.text(warp).color(TextColor.fromCSSHexString("#C849FF")))
                                .append(Component.text(".").color(NamedTextColor.GRAY)));
                    }, (t) -> player.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleporting you to warp ").color(NamedTextColor.GRAY))
                    .append(Component.text(warp).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" in ").color(NamedTextColor.GRAY))
                    .append(Component.text(t.getSecondsLeft()).color(TextColor.fromCSSHexString("#0CFFB6")))
                    .append(Component.text(" seconds.").color(NamedTextColor.GRAY)))); timer.scheduleTimer();
        }
    }

    public Location loadLocation(String warp){
        WarpConfig warpConfig = plugin.getWarpConfig();
        World world = Bukkit.getWorld(Objects.requireNonNull(warpConfig.getConfig().getString(warp + ".world")));
        double x = warpConfig.getConfig().getDouble(warp + ".x"),
                y = warpConfig.getConfig().getDouble(warp + ".y"),
                z = warpConfig.getConfig().getDouble(warp + ".z");
        float yaw = (float) warpConfig.getConfig().getDouble(warp + ".yaw"),
                pitch = (float) warpConfig.getConfig().getDouble(warp + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}