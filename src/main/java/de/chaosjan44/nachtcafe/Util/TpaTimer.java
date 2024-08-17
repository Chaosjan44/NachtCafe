package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TpaTimer implements TpaTimerSuper {

    private boolean isRunning;
    private int taskID;
    private int internalseconds;

    protected final Nachtcafe plugin;

    public TpaTimer(Nachtcafe plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start(int seconds, Player player) {
        internalseconds = seconds;
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (internalseconds == 0) {
                player.sendMessage(Nachtcafe.PREFIX
                        .append(Component.text("Your teleport request has timed out.").color(NamedTextColor.GRAY)));
                plugin.getTpaHandler().tpatimers.remove(player);
            }
            internalseconds--;
        }, 0, 20L);

    }

    @Override
    public void stop() {
        if (isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
        isRunning = false;
    }

    @Override
    public void setSeconds(int seconds) {
        this.internalseconds = seconds;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
