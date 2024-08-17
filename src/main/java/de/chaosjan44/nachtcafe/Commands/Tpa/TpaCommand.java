package de.chaosjan44.nachtcafe.Commands.Tpa;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public TpaCommand(Nachtcafe plugin) {this.plugin = plugin;}

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.tpa")) {
                if (args.length == 1) {
                    plugin.getTpaHandler().tpa((Player) sender, Bukkit.getPlayer(args[0]));
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/tpa ").color(TextColor.fromCSSHexString("#C849FF")))
                            .append(Component.text("<Player>").color(TextColor.fromCSSHexString("#0CFFB6")))
                            .append(Component.text(".").color(NamedTextColor.GRAY)));
            } else
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
        } else
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        return true;
    }
}
