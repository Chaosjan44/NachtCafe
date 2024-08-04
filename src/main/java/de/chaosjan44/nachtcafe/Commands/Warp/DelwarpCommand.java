package de.chaosjan44.nachtcafe.Commands.Warp;

import de.chaosjan44.nachtcafe.Configs.WarpConfig;
import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.WarpHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DelwarpCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public DelwarpCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender  sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpConfig warpConfig = plugin.getWarpConfig();
        WarpHandler warpHandler = plugin.getWarpHandler();
        if (sender.hasPermission("nachtcafe.warp.del")) {
            if (args.length == 1) {
                warpHandler.warps.remove(args[0]);
                warpConfig.getConfig().set("warps.list", warpHandler.warps);
                warpConfig.saveConfig();
                warpConfig.reloadConfig();
                warpHandler.loadWarpList();
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Warp ").color(NamedTextColor.GRAY))
                        .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text(" was deleted.").color(NamedTextColor.GRAY)));
                return true;
            } else {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                        .append(Component.text("/delwarp ").color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text("<warp>").color(TextColor.fromCSSHexString("#0CFFB6")))
                        .append(Component.text(".").color(NamedTextColor.GRAY)));
            }
        } else {
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
        }
        return true;
    }
}
