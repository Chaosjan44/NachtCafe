package de.chaosjan44.nachtcafe.Util;

public interface AfkTimerSuper {
    void start(int seconds);
    void stop();
    void setSeconds(int seconds);
    boolean isRunning();
}
