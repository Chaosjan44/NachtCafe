package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.LuckPermsWorker;
import de.chaosjan44.nachtcafe.Util.UserDataHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    private final Nachtcafe plugin;
    public JoinLeaveListener(Nachtcafe plugin) {this.plugin = plugin;}



    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        UserDataHandler userData = new UserDataHandler(plugin, event.getPlayer().getUniqueId());
        userData.createUser(event.getPlayer());
        userData.reloadConfig();
        plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        event.joinMessage(Component.text('[').color(NamedTextColor.DARK_GRAY)
                .append(Component.text('+').color(NamedTextColor.GREEN))
                .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
                .append(LegacyComponentSerializer.legacyAmpersand().deserialize(luckPermsWorker.getPrefix(event.getPlayer())))
                .append(Component.text(event.getPlayer().getName()).color(NamedTextColor.WHITE)));
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent event) {
        plugin.getAfkHandler().removeFromAfk(event.getPlayer());
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        event.quitMessage(Component.text('[').color(NamedTextColor.DARK_GRAY)
                .append(Component.text('-').color(NamedTextColor.RED))
                .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
                .append(LegacyComponentSerializer.legacyAmpersand().deserialize(luckPermsWorker.getPrefix(event.getPlayer())))
                .append(Component.text(event.getPlayer().getName()).color(NamedTextColor.WHITE)));
    }
}
