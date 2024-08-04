package de.chaosjan44.nachtcafe.Util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class CountdownTimer implements Runnable {

    private final JavaPlugin plugin;

    private Integer assignedTaskId;

    private int secondsLeft;

    private final Consumer<CountdownTimer> everySecond;
    private final Runnable afterTimer;

    public CountdownTimer(JavaPlugin plugin, int seconds, Runnable afterTimer, Consumer<CountdownTimer> everySecond) {
        this.plugin = plugin;

        this.secondsLeft = seconds;

        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    @Override
    public void run() {
        if (secondsLeft < 1) {
            afterTimer.run();

            if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
            return;
        }
        everySecond.accept(this);
        secondsLeft--;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
    }

}