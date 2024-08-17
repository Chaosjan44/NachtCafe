package de.chaosjan44.nachtcafe.Commands.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import de.chaosjan44.nachtcafe.Util.HomeItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        } else if (command.getName().equalsIgnoreCase("delhome")) {
            if (args.length == 1) {
                List<HomeItem> homes = plugin.getHomeHandler().playerHomes.get(sender);
                List<String> homesString = new ArrayList<>();
                for (HomeItem home : homes) {
                    homesString.add(home.getHomeName());
                }
                return completionPerChar(args[0], homesString);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("home")) {
            if (args.length == 1) {
                List<HomeItem> homes = plugin.getHomeHandler().playerHomes.get(sender);
                List<String> homesString = new ArrayList<>();
                for (HomeItem home : homes) {
                    homesString.add(home.getHomeName());
                }
                return completionPerChar(args[0], homesString);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("gamemode") || command.getName().equalsIgnoreCase("gm")) {
            if (args.length == 1) {
                List<String> gms = new ArrayList<>();
                gms.add("s");
                gms.add("c");
                gms.add("a");
                gms.add("sp");
                return completionPerChar(args[0], gms);
            } else if (args.length == 2 && sender.hasPermission("nachtcafe.gamemode.others")) {
                List<String> oPlayers = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers())
                    oPlayers.add(p.getName());
                return completionPerChar(args[1], oPlayers);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("enderchest") || command.getName().equalsIgnoreCase("ec")) {
            if (args.length == 1 && sender.hasPermission("nachtcafe.enderchest.others")) {
                List<String> oPlayers = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers())
                    oPlayers.add(p.getName());
                return completionPerChar(args[0], oPlayers);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("invsee")) {
            if (args.length == 1 && sender.hasPermission("nachtcafe.invsee")) {
                List<String> oPlayers = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers())
                    oPlayers.add(p.getName());
                return completionPerChar(args[0], oPlayers);
            } else {
                return empty;
            }
        } else if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1 && sender.hasPermission("nachtcafe.tpa")) {
                List<String> oPlayers = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers())
                    oPlayers.add(p.getName());
                return completionPerChar(args[0], oPlayers);
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
