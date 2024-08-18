package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.LuckPermsWorker;
import de.chaosjan44.nachtcafe.Util.UserDataHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;


public class JoinLeaveListener implements Listener {

    private final Nachtcafe plugin;
    public JoinLeaveListener(Nachtcafe plugin) {this.plugin = plugin;}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        UserDataHandler userData = new UserDataHandler(plugin, event.getPlayer().getUniqueId());
        if (userData.getUserFile().getString("User.Info.FirstJoin") != null) {
            userData.createUser(event.getPlayer());
            userData.reloadConfig();
            if (Objects.requireNonNull(userData.getUserFile().getString("User.Info.FirstJoin")).equalsIgnoreCase("1")) {
                event.getPlayer().teleport(plugin.getWarpHandler().loadLocation("spawn"));
                userData.getUserFile().set("User.Info.FirstJoin", "0");
                userData.saveUserFile();
                userData.reloadConfig();
            }
        }
        plugin.getHomeHandler().loadPlayerHomes(event.getPlayer());
        plugin.getAfkHandler().removeFromAfk(event.getPlayer());
        plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        event.joinMessage(Component.text('[').color(NamedTextColor.DARK_GRAY)
                .append(Component.text('+').color(NamedTextColor.GREEN))
                .append(Component.text("] ").color(NamedTextColor.DARK_GRAY))
                .append(LegacyComponentSerializer.legacyAmpersand().deserialize(luckPermsWorker.getPrefix(event.getPlayer())))
                .append(Component.text(event.getPlayer().getName()).color(NamedTextColor.WHITE)));
        tabStuff(event.getPlayer());
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
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendPlayerListFooter(Component.text("Online players: ").color(NamedTextColor.GRAY)
                    .append(Component.text(Bukkit.getOnlinePlayers().size()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.newline())
                    .append(MiniMessage.miniMessage().deserialize("<strikethrough><gradient:#FFFFFF:#6666CC>                                                <reset><!strikethrough>")));
        }
    }

    public void tabStuff(Player player) {
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        player.playerListName();
        player.sendPlayerListHeader(MiniMessage.miniMessage().deserialize("<gradient:#FFFFFF:#6666CC><strikethrough>                                                <reset><!strikethrough>")
                .append(Component.newline())
                .append(Component.text("N").color(TextColor.fromCSSHexString("#C849FF")))
                .append(Component.text("a").color(TextColor.fromCSSHexString("#B160F6")))
                .append(Component.text("c").color(TextColor.fromCSSHexString("#9977ED")))
                .append(Component.text("h").color(TextColor.fromCSSHexString("#828DE4")))
                .append(Component.text("t").color(TextColor.fromCSSHexString("#6AA4DB")))
                .append(Component.text("C").color(TextColor.fromCSSHexString("#53BBD1")))
                .append(Component.text("a").color(TextColor.fromCSSHexString("#3BD2C8")))
                .append(Component.text("f").color(TextColor.fromCSSHexString("#24E8BF")))
                .append(Component.text("e").color(TextColor.fromCSSHexString("#0CFFB6")))
                .append(Component.text(" - ").color(NamedTextColor.DARK_GRAY))
                .append(Component.text("Dein SMP Server").color(NamedTextColor.BLUE))
                .append(Component.newline()));
        player.playerListName(LegacyComponentSerializer.legacyAmpersand().deserialize(luckPermsWorker.getPrefix(player))
                .append(Component.text(player.getName()).color(NamedTextColor.WHITE)));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendPlayerListFooter(Component.text("Online players: ").color(NamedTextColor.GRAY)
                    .append(Component.text(Bukkit.getOnlinePlayers().size()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.newline())
                    .append(MiniMessage.miniMessage().deserialize("<strikethrough><gradient:#FFFFFF:#6666CC>                                                <reset><!strikethrough>")));
        }
    }
}
