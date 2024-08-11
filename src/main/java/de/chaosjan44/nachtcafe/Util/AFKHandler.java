package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for (Map.Entry<Player, Long> entry : afkPTimer.entrySet()) {
                Player player = entry.getKey();
                long timestamp = entry.getValue();
                long curtime= System.currentTimeMillis();
                long diff = Math.abs(curtime - timestamp);
                // minutes a player needs to be afk to be markes as afk
                int afkmin = 5;
                if (diff > afkmin * 60 * 1000) {
                    afkPlayers.add(player);
                    afkPTimer.remove(player);
                    plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                            .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                            .append(Component.text(" is now AFK.").color(NamedTextColor.GRAY)));
                }
            }
        }
        // start 15 timer till next check
        startAFKTimer();
    }

    // only needed on disconnect
    public void removeFromAfk(Player player) {
        if (afkPlayers.contains(player)) {
            plugin.getServer().broadcast(Component.text("* ").color(NamedTextColor.GRAY)
                    .append(Component.text(player.getName()).color(NamedTextColor.GRAY))
                    .append(Component.text(" is no longer AFK.").color(NamedTextColor.GRAY)));
            afkPlayers.remove(player);
        }
        if (afkPTimer.containsKey(player)) {
            afkPTimer.remove(player);
        }
    }


    public void startAFKTimer() {
        Timer timer = plugin.getTimer();
        if (timer.isRunning())
            timer.stop();
        timer.start(15);
    }

    public void stopAFKTimer() {
        Timer timer = plugin.getTimer();
        if (timer.isRunning())
            timer.stop();
        afkPTimer.clear();
        afkPlayers.clear();
    }
}
