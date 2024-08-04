package de.chaosjan44.nachtcafe.Commands.Warp;

import de.chaosjan44.nachtcafe.Configs.WarpConfig;
import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.WarpHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetwarpCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public SetwarpCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpConfig warpConfig = plugin.getWarpConfig();
        WarpHandler warpHandler = plugin.getWarpHandler();
        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.warp.set")) {
                if (args.length == 1) {
                    Location location = ((Player) sender).getLocation();
                    if (!warpHandler.warps.contains(args[0])) {
                        warpHandler.warps.add(args[0]);
                        warpConfig.getConfig().set("warps.list", warpHandler.warps);
                    }
                    warpConfig.getConfig().set(args[0] + "." + "world", location.getWorld().getName());
                    warpConfig.getConfig().set(args[0] + "." + "x", location.getX());
                    warpConfig.getConfig().set(args[0] + "." + "y", location.getY());
                    warpConfig.getConfig().set(args[0] + "." + "z", location.getZ());
                    warpConfig.getConfig().set(args[0] + "." + "yaw", location.getYaw());
                    warpConfig.getConfig().set(args[0] + "." + "pitch", location.getPitch());
                    warpConfig.saveConfig();
                    warpConfig.reloadConfig();
                    warpHandler.loadWarpList();
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Warp ").color(NamedTextColor.GRAY))
                            .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                            .append(Component.text(" has been set.").color(NamedTextColor.GRAY)));
                    return true;
                } else {
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/setwarp ").color(TextColor.fromCSSHexString("#C849FF")))
                            .append(Component.text("<warp>").color(TextColor.fromCSSHexString("#0CFFB6")))
                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                }
            } else {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
            }
        } else {
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        }
        return true;
    }
}
