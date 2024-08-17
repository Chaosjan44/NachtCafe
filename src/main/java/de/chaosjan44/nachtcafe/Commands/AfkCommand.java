package de.chaosjan44.nachtcafe.Commands;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.AFKHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public AfkCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        AFKHandler afkHandler = plugin.getAfkHandler();
        if (sender instanceof Player) {
            if (args.length == 0) {
                if (afkHandler.afkPlayers.contains((Player) sender))
                    afkHandler.updateAFKPTimer((Player) sender);
                else
                    afkHandler.setAfk((Player) sender);
                return true;
            } else
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                        .append(Component.text("/afk").color(TextColor.fromCSSHexString("#C849FF")))
                        .append(Component.text(".").color(NamedTextColor.GRAY)));
        } else
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("You must be a player to perform this command").color(NamedTextColor.DARK_RED)));
        return true;
    }
}
