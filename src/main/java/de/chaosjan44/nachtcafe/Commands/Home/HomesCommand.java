package de.chaosjan44.nachtcafe.Commands.Home;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.HomeHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomesCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public HomesCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        HomeHandler homeHandler = plugin.getHomeHandler();

        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.home")) {
                if (args.length == 0) {
                    if (homeHandler.playerHomesList.get(sender) != null && homeHandler.playerHomesCount.get(sender) != null) {
                        if (homeHandler.playerHomesCount.get(sender) != 0) {
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Your Homes:").color(NamedTextColor.GRAY)));
                            for (String home : homeHandler.playerHomesList.get(sender))
                                sender.sendMessage(Component.text("- ").color(NamedTextColor.GRAY)
                                        .append(Component.text(home).color(TextColor.fromCSSHexString("#C849FF")))
                                        .hoverEvent(HoverEvent.showText(Component.text("click to teleport to this home.").color(TextColor.fromCSSHexString("#C849FF"))))
                                        .clickEvent(ClickEvent.runCommand("/home " + home)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You have no homes.").color(NamedTextColor.GRAY)));
                    } else
                        sender.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("You have no homes.").color(NamedTextColor.GRAY)));
                    return true;
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/homes ").color(TextColor.fromCSSHexString("#C849FF")))
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
