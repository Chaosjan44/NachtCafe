package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private final Nachtcafe plugin;
    public PlayerRespawnListener(Nachtcafe plugin) {this.plugin = plugin;}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerRespawnListener(PlayerRespawnEvent event) {
        if(!event.isBedSpawn()) {
            event.setRespawnLocation(plugin.getWarpHandler().loadLocation("spawn"));
            event.getPlayer().sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("you have been respawned at spawn.").color(NamedTextColor.GRAY)));
        }
    }


}
