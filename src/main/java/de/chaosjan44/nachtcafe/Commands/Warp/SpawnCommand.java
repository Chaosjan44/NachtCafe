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

public class SpawnCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public SpawnCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpHandler warpHandler = plugin.getWarpHandler();
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (warpHandler.warps.isEmpty()) {
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("The spawn isn't set.").color(NamedTextColor.GRAY)));
                } else { warpHandler.warp(((Player) sender), "spawn"); }
                return true;
            } else {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                        .append(Component.text("/spawn").color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text(".").color(NamedTextColor.GRAY)));
            }
        } else {
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        }
        return true;
    }
}
