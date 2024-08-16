package de.chaosjan44.nachtcafe.Commands.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("nachtcafe.gamemode")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                        if (sender.hasPermission("nachtcafe.gamemode.survival")) {
                            ((Player) sender).setGameMode(GameMode.SURVIVAL);
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                    .append(Component.text("survival").color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(".").color(NamedTextColor.GRAY)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                    } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                        if (sender.hasPermission("nachtcafe.gamemode.creative")) {
                            ((Player) sender).setGameMode(GameMode.CREATIVE);
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                    .append(Component.text("creative").color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(".").color(NamedTextColor.GRAY)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                    } else if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                        if (sender.hasPermission("nachtcafe.gamemode.adventure")) {
                            ((Player) sender).setGameMode(GameMode.ADVENTURE);
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                    .append(Component.text("adventure").color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(".").color(NamedTextColor.GRAY)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                    } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                        if (sender.hasPermission("nachtcafe.gamemode.spectator")) {
                            ((Player) sender).setGameMode(GameMode.SPECTATOR);
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                    .append(Component.text("spectator").color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(".").color(NamedTextColor.GRAY)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                    } else
                        sender.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                                .append(Component.text("/gm ").color(TextColor.fromCSSHexString("#C849FF")))
                                .append(Component.text("<s,0 / c,1 / a,2 / sp,3>").color(TextColor.fromCSSHexString("#0CFFB6")))
                                .append(Component.text(".").color(NamedTextColor.GRAY)));
                } else if (args.length == 2) {
                    if (sender.hasPermission("nachtcafe.gamemode.others")) {
                        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
                            if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                                if (sender.hasPermission("nachtcafe.gamemode.survival")) {
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).setGameMode(GameMode.SURVIVAL);
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text(args[1]).color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text("'s gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("survival").color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("survival").color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text(" by ").color(NamedTextColor.GRAY))
                                            .append(Component.text(sender.getName()).color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                } else
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                            } else if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                                if (sender.hasPermission("nachtcafe.gamemode.creative")) {
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).setGameMode(GameMode.CREATIVE);
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text(args[1]).color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text("'s gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("creative").color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("creative").color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text(" by ").color(NamedTextColor.GRAY))
                                            .append(Component.text(sender.getName()).color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                } else
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                            } else if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                                if (sender.hasPermission("nachtcafe.gamemode.adventure")) {
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).setGameMode(GameMode.ADVENTURE);
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text(args[1]).color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text("'s gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("adventure").color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("adventure").color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text(" by ").color(NamedTextColor.GRAY))
                                            .append(Component.text(sender.getName()).color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                } else
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                            } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                                if (sender.hasPermission("nachtcafe.gamemode.spectator")) {
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).setGameMode(GameMode.SPECTATOR);
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text(args[1]).color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text("'s gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("spectator").color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                    Objects.requireNonNull(Bukkit.getPlayer(args[1])).sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("Your gamemode has been set to ").color(NamedTextColor.GRAY))
                                            .append(Component.text("spectator").color(TextColor.fromCSSHexString("#C849FF")))
                                            .append(Component.text(" by ").color(NamedTextColor.GRAY))
                                            .append(Component.text(sender.getName()).color(TextColor.fromCSSHexString("#0CFFB6")))
                                            .append(Component.text(".").color(NamedTextColor.GRAY)));
                                } else
                                    sender.sendMessage(Nachtcafe.PREFIX
                                            .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                            } else
                                sender.sendMessage(Nachtcafe.PREFIX
                                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                                        .append(Component.text("/gm ").color(TextColor.fromCSSHexString("#C849FF")))
                                        .append(Component.text("<s / c / a / sp> [<Player>]").color(TextColor.fromCSSHexString("#0CFFB6")))
                                        .append(Component.text(".").color(NamedTextColor.GRAY)));
                        } else
                            sender.sendMessage(Nachtcafe.PREFIX
                                    .append(Component.text(args[1]).color(TextColor.fromCSSHexString("#C849FF")))
                                    .append(Component.text(" is not online!").color(NamedTextColor.GRAY)));
                    } else
                        sender.sendMessage(Nachtcafe.PREFIX
                                .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
                }

            } else
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("You don't have the permissions to do that!").color(NamedTextColor.DARK_RED)));
        } else
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        return true;
    }
}
