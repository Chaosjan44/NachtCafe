package de.chaosjan44.nachtcafe.Util;

import de.chaosjan44.nachtcafe.Nachtcafe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.util.HashMap;


public class TPAHandler {

    private final Nachtcafe plugin;
    public TPAHandler(Nachtcafe plugin) {this.plugin = plugin;}


    public HashMap<Player, TpaTimer> tpatimers = new HashMap<>();
    public HashMap<Player, Player> tpafromto = new HashMap<>();
    public HashMap<Player, Player> tpatofrom = new HashMap<>();

    public void tpa(Player from, Player to) {
        if (!tpatimers.containsKey(from)) {
            tpafromto.remove(from);
            tpafromto.put(from, to);
            tpatofrom.remove(to);
            tpatofrom.put(to, from);
            TpaTimer tpaTimer = new TpaTimer(plugin);
            tpatimers.put(from, tpaTimer);
            startTPATimer(tpaTimer, from);
        }  else {
            canceltpa(from);
            tpafromto.put(from, to);
            tpatofrom.put(to, from);
            TpaTimer tpaTimer = new TpaTimer(plugin);
            tpatimers.put(from, tpaTimer);
            startTPATimer(tpaTimer, from);
        }
        if (plugin.getAfkHandler().afkPlayers.contains(to))
            from.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" is currently AFK, they might not answer your request.").color(NamedTextColor.GRAY)));
        from.sendMessage(Nachtcafe.PREFIX
                .append(Component.text("Request sent to ").color(NamedTextColor.GRAY))
                .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                .append(Component.text(".").color(NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("To cancel this request, type ").color(NamedTextColor.GRAY))
                .append(Component.text("/tpacancel").color(NamedTextColor.RED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to cancel").color(NamedTextColor.RED)))
                        .clickEvent(ClickEvent.runCommand("/tpacancel")))
                .append(Component.text(".").color(NamedTextColor.GRAY)));
        to.sendMessage(Nachtcafe.PREFIX
                .append(Component.text(from.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                .append(Component.text(" has requested to teleport to you.").color(NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("To teleport them, type ").color(NamedTextColor.GRAY))
                .append(Component.text("/tpaccept").color(NamedTextColor.GREEN)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to accept").color(NamedTextColor.GREEN)))
                        .clickEvent(ClickEvent.runCommand("/tpaccept")))
                .append(Component.text(".").color(NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("To deny this request, type ").color(NamedTextColor.GRAY))
                .append(Component.text("/tpdeny").color(NamedTextColor.RED)
                        .hoverEvent(HoverEvent.showText(Component.text("Click to deny").color(NamedTextColor.RED)))
                        .clickEvent(ClickEvent.runCommand("/tpdeny")))
                .append(Component.text(".").color(NamedTextColor.GRAY))
                .append(Component.newline())
                .append(Component.text("This request will timeout after ").color(NamedTextColor.GRAY))
                .append(Component.text("120").color(TextColor.fromCSSHexString("#C849FF")))
                .append(Component.text(" seconds.").color(NamedTextColor.GRAY)));
    }

    public void canceltpa(Player from) {
        if (tpafromto.containsKey(from)) {
            Player to = tpafromto.get(from);
            stopTPATimer(tpatimers.get(from));
            tpatimers.remove(from);
            tpatofrom.remove(to);
            tpafromto.remove(from);
            from.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("All outstanding teleport requests ").color(NamedTextColor.GRAY))
                    .append(Component.text("canceled").color(NamedTextColor.RED))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
            to.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" canceled").color(NamedTextColor.RED))
                    .append(Component.text(" their teleport request.").color(NamedTextColor.GRAY)));
        } else
            from.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("There's no outstanding teleport request.").color(NamedTextColor.RED)));
    }

    public void denytpa(Player to) {
        if (tpatofrom.containsKey(to)) {
            Player from = tpatofrom.get(to);
            stopTPATimer(tpatimers.get(from));
            tpatimers.remove(from);
            tpafromto.remove(from);
            tpatofrom.remove(to);
            to.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleport request from ").color(NamedTextColor.GRAY))
                    .append(Component.text(from.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" denied").color(NamedTextColor.RED))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
            from.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" denied").color(NamedTextColor.RED))
                    .append(Component.text(" your teleport request.").color(NamedTextColor.GRAY)));
        } else
            to.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("There's no outstanding teleport request.").color(NamedTextColor.RED)));
    }

    public void accepttpa(Player to) {
        if (tpatofrom.containsKey(to)) {
            Player from = tpatofrom.get(to);
            stopTPATimer(tpatimers.get(from));
            tpatimers.remove(from);
            tpafromto.remove(from);
            tpatofrom.remove(to);
            from.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" accepted ").color(NamedTextColor.GREEN))
                    .append(Component.text("your teleport request.").color(NamedTextColor.GRAY))
                    .append(Component.newline())
                    .append(Component.text("Teleporting to ").color(NamedTextColor.GRAY))
                    .append(Component.text(to.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
            to.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("Teleport request from ").color(NamedTextColor.GRAY))
                    .append(Component.text(from.getName()).color(TextColor.fromCSSHexString("#C849FF")))
                    .append(Component.text(" accepted").color(NamedTextColor.GREEN))
                    .append(Component.text(".").color(NamedTextColor.GRAY)));
            from.teleport(to.getLocation());
        } else
            to.sendMessage(Nachtcafe.PREFIX
                    .append(Component.text("There's no outstanding teleport request.").color(NamedTextColor.RED)));
    }


    public void startTPATimer(TpaTimer tpaTimer, Player player) {
        if (tpaTimer.isRunning())
            tpaTimer.stop();
        tpaTimer.start(120, player);
    }

    public void stopTPATimer(TpaTimer tpaTimer) {
        if (tpaTimer.isRunning())
            tpaTimer.stop();
    }
}
