package de.chaosjan44.nachtcafe.Commands.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private final Nachtcafe plugin;
    public TabCompleter(Nachtcafe plugin) { this.plugin = plugin; }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args) {
        List<String> empty = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("warp")) {
            if (args.length == 1) {
                return completionPerChar(args[0], plugin.getWarpHandler().warps);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("setwarp")) {
            if (args.length == 1) {
                return completionPerChar(args[0], plugin.getWarpHandler().warps);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("delwarp")) {
            if (args.length == 1) {
                return completionPerChar(args[0], plugin.getWarpHandler().warps);
            } else {
                return empty;
            }
        }
        return empty;
    }

    public List<String> completionPerChar(String arg, List<String> list) {
        List<String> completions = null;
        for (String s : list) {
            if (s.startsWith(arg)) {
                if (completions == null) {
                    completions = new ArrayList<>();
                }
                completions.add(s);
            }
        }
        if (completions != null)
            Collections.sort(completions);
        return completions;
    }

}
