package de.chaosjan44.nachtcafe.Commands.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Your have a ping of ").color(NamedTextColor.GRAY))
                        .append(Component.text(((Player) sender).getPing()).color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text("ms.").color(NamedTextColor.GRAY)));
            } else
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                        .append(Component.text("/ping").color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text(".").color(NamedTextColor.GRAY)));

        } else
            sender.sendMessage(Nachtcafe.PREFIX
                       .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
    return true;
    }
}
