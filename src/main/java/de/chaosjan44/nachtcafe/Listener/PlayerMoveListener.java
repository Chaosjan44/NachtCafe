package de.chaosjan44.nachtcafe.Listener;

import de.chaosjan44.nachtcafe.Nachtcafe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final Nachtcafe plugin;
    public PlayerMoveListener(Nachtcafe plugin) {this.plugin = plugin;}

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if  (event.hasChangedBlock())  {
            plugin.getAfkHandler().updateAFKPTimer(event.getPlayer());
        }
    }

}
