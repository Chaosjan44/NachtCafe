package de.chaosjan44.nachtcafe.Commands.Util;

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

import java.util.Objects;

public class EnderchestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.enderchest")) {
                if (args.length == 0) {
                    Player p = (Player) sender;
                    p.openInventory(p.getEnderChest());
                } else if (args.length == 1 && sender.hasPermission("nachtcafe.enderchest.others")) {
                    Player p = (Player) sender;
                    p.openInventory(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getEnderChest());
                } else
                    sender.sendMessage(Nachtcafe.PREFIX
                            .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                            .append(Component.text("/ec").color(TextColor.fromCSSHexString("#C849FF")))
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
