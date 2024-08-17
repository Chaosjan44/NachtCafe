package de.chaosjan44.nachtcafe.Util;

import org.bukkit.entity.Player;

public interface TpaTimerSuper {
    void start(int seconds, Player player);
    void stop();
    void setSeconds(int seconds);
    boolean isRunning();
}
