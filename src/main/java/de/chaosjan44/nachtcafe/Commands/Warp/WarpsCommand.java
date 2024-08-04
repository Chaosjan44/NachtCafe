package de.chaosjan44.nachtcafe.Commands.Warp;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.WarpHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WarpsCommand implements CommandExecutor {

    private final Nachtcafe plugin;
    public WarpsCommand(Nachtcafe plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpHandler warpHandler = plugin.getWarpHandler();
        if (args.length == 0) {
            if (warpHandler.warps.isEmpty()) {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("There are no warps.").color(NamedTextColor.GRAY)));
            } else {
                sender.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Warps:").color(NamedTextColor.GRAY)));
                for (String warp : warpHandler.warps) {
                    sender.sendMessage(Component.text("- ").color(NamedTextColor.GRAY)
                            .append(Component.text(warp).color(TextColor.fromCSSHexString("#C849FF"))).clickEvent(ClickEvent.runCommand("/warp " + warp)));
                }
            }
            return true;
        } else {
            sender.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Usage: ").color(NamedTextColor.GRAY))
                    .append(Component.text("/warps").color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
        }
    return true;
    }
}
