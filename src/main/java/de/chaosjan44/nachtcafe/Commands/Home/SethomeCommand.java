package de.chaosjan44.nachtcafe.Commands.Home;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.HomeHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SethomeCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public SethomeCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        HomeHandler homeHandler = plugin.getHomeHandler();

        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.home")) {
                if (args.length == 1) {
                    Location location = ((Player) sender).getLocation();
                    if (homeHandler.playerHomes.get(sender) == null || !homeHandler.playerHomes.get(sender).contains(args[0])) {
                        // check if player doesn't have more homes than he is allowed to - this should probably be done with a config...
                        // default homes
                        Integer allowedhomes = 3;
                        // booster homes
                        if (sender.hasPermission("nachtcafe.home.multi.booster"))
                            allowedhomes = 5;
                        // team homes
                        else if (sender.hasPermission("nachtcafe.home.multi.team"))
                            allowedhomes = 9999;
                        if (homeHandler.playerHomesCount.get(sender) == null || homeHandler.playerHomesCount.get(sender) < allowedhomes) {
                            // set new home
                            homeHandler.addHome((Player) sender, args[0], location);
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Home ").color(NamedTextColor.GRAY))
                                    .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(" has been set.").color(NamedTextColor.GRAY)));
                        } else
                            // give out error message to player - player can't have more homes than configured for them
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You can't have more than ").color(NamedTextColor.GRAY))
                                    .append(Component.text(allowedhomes).color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(" Homes.").color(NamedTextColor.GRAY)));
                    } else {
                        // update old home
                        homeHandler.addHome((Player) sender, args[0], location);
                        sender.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("Home ").color(NamedTextColor.GRAY))
                                .append(Component.text(args[0]).color(TextColor.fromCSSHexString("#C849FF")))
                                .append(Component.text(" has been set.").color(NamedTextColor.GRAY)));
                    }
                    return true;
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/sethome ").color(TextColor.fromCSSHexString("#C849FF")))
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
