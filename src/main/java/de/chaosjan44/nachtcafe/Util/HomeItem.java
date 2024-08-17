package de.chaosjan44.nachtcafe.Util;

import org.bukkit.Location;

public class HomeItem {
    public String homename;
    public Location location;

    public HomeItem(String homename, Location location) {
        this.homename = homename;
        this.location = location;
    }

    public String getHomeName()  {
        return (homename);
    }
}
