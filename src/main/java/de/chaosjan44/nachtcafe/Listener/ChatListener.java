package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.ColorHelper;
import de.chaosjan44.nachtcafe.Util.LuckPermsWorker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class ChatListener implements Listener {

    private final Nachtcafe plugin;
    public ChatListener(Nachtcafe plugin) {this.plugin = plugin;}


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        ColorHelper colorHelper =  plugin.getColorHelper();

        final String message = event.getMessage();
        final Player player = event.getPlayer();

        String format = "{prefix}{name} &7»&r {message}"
                .replace("{prefix}", luckPermsWorker.getPrefix(player) != null ? luckPermsWorker.getPrefix(player) : "")
                .replace("{name}", player.getName());

        format = colorHelper.colorize(colorHelper.translateHexColorCodes(format));

        event.setFormat(format.replace("{message}", player.hasPermission("nachtcafe.colorcodes") && player.hasPermission("nachtcafe.rgbcodes")
                ? colorHelper.colorize(colorHelper.translateHexColorCodes(message)) : player.hasPermission("nachtcafe.colorcodes") ? colorHelper.colorize(message) : player.hasPermission("nachtcafe.rgbcodes")
                ? colorHelper.translateHexColorCodes(message) : message).replace("%", "%%"));
        // update player's AFK Timer
        plugin.getAfkHandler().updateAFKPTimer(player);
    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
    }
}
