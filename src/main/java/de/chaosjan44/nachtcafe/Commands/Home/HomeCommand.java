package de.chaosjan44.nachtcafe.Commands.Home;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.HomeHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public HomeCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        HomeHandler homeHandler = plugin.getHomeHandler();

        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.home")) {
                if (args.length == 1) {
                    if (homeHandler.playerHomesList.get(sender) != null) {
                        if (homeHandler.playerHomesList.get(sender).contains(args[0])) {
                            homeHandler.home((Player) sender, args[0]);
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Home ").color(NamedTextColor.GRAY))
                                    .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(" does not exist.").color(NamedTextColor.GRAY)));
                    } else
                        sender.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("Home ").color(NamedTextColor.GRAY))
                                .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                                .append(Component.text(" does not exist.").color(NamedTextColor.GRAY)));
                    return true;
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/home ").color(TextColor.fromCSSHexString("#C849FF")))
                            .append(Component.text("<home>").color(TextColor.fromCSSHexString("#0CFFB6")))
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
