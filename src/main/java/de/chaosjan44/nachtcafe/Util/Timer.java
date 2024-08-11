package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import org.bukkit.Bukkit;

public class Timer implements TimerSuper {

    private boolean isRunning;
    private int taskID;
    private int internalseconds;

    protected final Nachtcafe plugin;

    public Timer(Nachtcafe plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start(int seconds) {
        internalseconds = seconds;
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (internalseconds == 0) {
                plugin.getAfkHandler().checkAfk();
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
