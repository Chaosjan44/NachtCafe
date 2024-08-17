package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AFKHandler {

    private final Nachtcafe plugin;
    public AFKHandler(Nachtcafe plugin) {this.plugin = plugin;}

    public HashMap<Player, Long> afkPTimer = new HashMap<>();
    public List<Player> afkPlayers = new ArrayList<>();

    public void updateAFKPTimer(Player player)  {
        if (afkPlayers.contains(player)) {
            plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                    .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                    .append(Component.text(" is no longer AFK.").color(NamedTextColor.GRAY)));
            afkPlayers.remove(player);
        }
        afkPTimer.put(player, System.currentTimeMillis());
    }

    public void checkAfk () {
        if (!(afkPTimer.isEmpty())) {
            List<Player> toremove = new ArrayList<>();
            afkPTimer.forEach((k, v) -> {
                long curtime= System.currentTimeMillis();
                long diff = Math.abs(curtime - v);
                // minutes a player needs to be afk to be marked as afk
                int afkmin = 5;
                if (diff > afkmin * 60 * 1000) {
                    afkPlayers.add(k);
                    toremove.add(k);
                    plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                            .append(Component.text(k.getName()).color(NamedTextColor.GRAY))
                            .append(Component.text(" is now AFK.").color(NamedTextColor.GRAY)));
                }
            });
            for (Player p : toremove) {
                afkPTimer.remove(p);
            }
            toremove.clear();
        }
        // start 15 timer till next check
        startAFKTimer();
    }

    public void setAfk(Player player) {
        if (!afkPlayers.contains(player)) {
            plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                    .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                    .append(Component.text(" is now AFK.").color(NamedTextColor.GRAY)));
            afkPlayers.add(player);
        }
        afkPTimer.remove(player);
    }

    // only needed on disconnect
    public void removeFromAfk(Player player) {
        if (afkPlayers.contains(player)) {
            plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                    .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                    .append(Component.text(" is no longer AFK.").color(NamedTextColor.GRAY)));
            afkPlayers.remove(player);
        }
        afkPTimer.remove(player);
    }

    public void startAFKTimer() {
        AfkTimer afkTimer = plugin.getTimer();
        if (afkTimer.isRunning())
            afkTimer.stop();
        afkTimer.start(15);
    }

    public void stopAFKTimer() {
        AfkTimer afkTimer = plugin.getTimer();
        if (afkTimer.isRunning())
            afkTimer.stop();
        afkPTimer.clear();
        afkPlayers.clear();
    }
}
