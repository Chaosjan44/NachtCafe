package de.chaosjan44.nachtcafe.Commands.Warp;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.WarpHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public WarpCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpHandler warpHandler = plugin.getWarpHandler();
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (warpHandler.warps.contains(args[0])) {
                    warpHandler.warp(((Player) sender), args[0]);
                    return true;
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("The warp ").color(NamedTextColor.GRAY))
                            .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                            .append(Component.text(" does not exist.").color(NamedTextColor.GRAY)));
            } else {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                        .append(Component.text("/warp ").color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text("<warp>").color(TextColor.fromCSSHexString("#0CFFB6")))
                        .append(Component.text(".").color(NamedTextColor.GRAY)));
            }
        } else {
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        }
        return true;
    }
}
