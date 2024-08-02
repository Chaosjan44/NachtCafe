package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LuckPermsWorker {

    private final Nachtcafe plugin;
    public LuckPermsWorker(Nachtcafe plugin) {this.plugin = plugin;}

    public String getPrefix(Player player) {
        LuckPerms luckPerms = plugin.getLuckPerms();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
        String prefix = user.getCachedData().getMetaData().getPrefix();
        return (prefix);
    }


}
