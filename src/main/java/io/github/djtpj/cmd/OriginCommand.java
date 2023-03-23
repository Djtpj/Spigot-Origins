package io.github.djtpj.cmd;

import io.github.djtpj.gui.OriginPicker;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OriginCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player only command!");
            return false;
        }

        if (!label.equalsIgnoreCase("origin")) return true;

        new OriginPicker(((Player) sender));

        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, @NonNull String[] strings) {
        return new ArrayList<>();
    }
}