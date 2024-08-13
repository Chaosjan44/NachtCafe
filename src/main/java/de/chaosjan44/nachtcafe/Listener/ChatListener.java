package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.LuckPermsWorker;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.jetbrains.annotations.NotNull;

public class ChatListener implements Listener, ChatRenderer {

    private final Nachtcafe plugin;
    public ChatListener(Nachtcafe plugin) {this.plugin = plugin;}


    // replace renderer so we can have our fancy prefixes :p
    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        LuckPermsWorker luckPermsWorker =  plugin.getLuckPermsWorker();
        Component formatted = Component.text(luckPermsWorker.getPrefix(source) != null ? luckPermsWorker.getPrefix(source) : "")
                .append(sourceDisplayName)
                .append(Component.text(" »").color(NamedTextColor.GRAY))
                .append(Component.text(" ").color(NamedTextColor.WHITE))
                .append(message).color(NamedTextColor.WHITE);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(ChatColor.translateAlternateColorCodes('&', LegacyComponentSerializer.legacyAmpersand().serialize(formatted)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncChatEvent event) {
        event.renderer(this);
        // update player's AFK Timer
        plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandSendEvent event) {
        plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
    }
}
