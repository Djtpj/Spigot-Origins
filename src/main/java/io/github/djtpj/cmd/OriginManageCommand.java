package io.github.djtpj.cmd;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.gui.OriginPicker;
import io.github.djtpj.origin.Origin;
import io.github.djtpj.origin.OriginRegistry;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.djtpj.origin.Main.plugin;

public class OriginManageCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This is a player only command!");
            return true;
        }

        if (!player.hasPermission("origin.manage")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Please specify a player to affect and the action you would like to complete.");
            return true;
        }

        var affectedPlayer = Bukkit.getPlayer(args[0]);

        if (affectedPlayer == null) {
            sender.sendMessage(ChatColor.RED + "That is not an online Player. Please specify one.");
            return true;
        }

        var subCommand = getSubCommand(args);

        if (subCommand == null) {
            sender.sendMessage(ChatColor.RED + "Please specify a sub-command to run.");
            return true;
        }

        // Check if the args (after removing the subcommand and player args) meet the subcommand's required arg count
        if ((args.length - 2) < subCommand.getReqArgSize()) {
            sender.sendMessage(ChatColor.RED + "Please provide the required arguments for this sub-command.");
            return true;
        }

        Optional<String> result;

        if (subCommand.usesArgs()) {
            // Args that are adjusted for the subCommand
            var editedArgs = new ArrayList<>(Arrays.stream(args).toList());
            editedArgs.remove(0);
            editedArgs.remove(0);

            result = subCommand.run(affectedPlayer, editedArgs.toArray(new String[0]));
        }

        else {
            result = subCommand.run(affectedPlayer, null);
        }

        if (result.isPresent()) {
            sender.sendMessage(ChatColor.RED + result.get());
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var arguments = determineBaseTabResults(args);

        var results = new ArrayList<String>();

        for (String argument : arguments) {
            var lastArg = args[args.length - 1];

            if (argument.toLowerCase().startsWith(lastArg)) {
                results.add(argument);
            }
        }

        return results;
    }

    private static List<String> determineBaseTabResults(String[] args) {
        final int PLAYER = 1, SUB = 2, SUB_ARG = 3;

        var subCommand = getSubCommand(args);

        if (args.length == PLAYER) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getDisplayName)
                    .collect(Collectors.toList());
        }

        else if (args.length == SUB) {
            return Arrays.stream(Commands.values())
                    .map(Commands::getTrigger)
                    .collect(Collectors.toList());
        }

        else if (args.length == SUB_ARG) {
            return subCommand.generateTabCompleteResults();
        }

        return new ArrayList<>();
    }

    private static Commands getSubCommand(String[] args) {
        var usableArgs = new ArrayList<>(Arrays.asList(args));

        // an affected player will be specified at the actual 0 slot, so we adjust for that
        usableArgs.remove(0);

        if (usableArgs.size() < 1) {
            return null;
        }

        var subCommandTrigger = usableArgs.get(0);

        for (Commands command : Commands.values()) {
            if (command.getTrigger().equalsIgnoreCase(subCommandTrigger)) {
                return command;
            }
        }

        return null;
    }
}

enum Commands {
    DEPERM("deperm") {
        @Override
        public Optional<String> run(Player player, String[] args) {
            var attachment = Commands.getAttachment(player);
            attachment.setPermission("origin", false);

            return Optional.empty();
        }
    },
    PERM("perm") {
        @Override
        public Optional<String> run(Player player, String[] args) {
            var attachment = Commands.getAttachment(player);
            attachment.setPermission("origin", true);

            return Optional.empty();
        }
    },
    OPEN("open") {
        @Override
        public Optional<String> run(Player player, String[] args) {
            new OriginPicker(player);

            return Optional.empty();
        }
    },
    GIVE("give", 1) {
        @Override
        public Optional<String> run(Player player, String[] args) {
            if (args.length < 1) {
                return Optional.of("You must specify an origin to give");
            }

            var origin = OriginRegistry.getInstance().getOrigin(args[0]);

            if (origin == null) {
                return Optional.of("That is not a valid origin. Double check your spelling and spacing.");
            }

            PlayerManager.getInstance().setOrigin(player, origin);

            return Optional.empty();
        }

        @Override
        public List<String> generateTabCompleteResults() {
            return Arrays.stream(OriginRegistry.getInstance().registry())
                    .map(Origin::getId)
                    .collect(Collectors.toList());
        }
    },
    CLEAR("clear") {
        @Override
        public Optional<String> run(Player player, String[] args) {
            var humanOrigin = OriginRegistry.getInstance().getOrigin("human");
            PlayerManager.getInstance().setOrigin(player, humanOrigin);

            return Optional.empty();
        }
    },

    GET("get") {
        @Override
        public Optional<String> run(Player player, String[] args) {
            var playerOrigin = PlayerManager.getInstance().getOrigin(player);
            player.sendMessage("That player's origin is " + playerOrigin.getName());

            return Optional.empty();
        }
    };

    private final static HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

    @Getter
    private final String trigger;

    @Getter
    private final int reqArgSize;

    @Getter
    @Accessors(fluent = true)
    private final boolean usesArgs;

    Commands(String trigger) {
        this(trigger, 0);
    }

    Commands(String trigger, int reqArgSize) {
        this.trigger = trigger;
        this.reqArgSize = reqArgSize;
        this.usesArgs = reqArgSize != 0;
    }

    public abstract Optional<String> run(Player player, String[] args);
    public List<String> generateTabCompleteResults() {
        return new ArrayList<>();
    }

    private static PermissionAttachment getAttachment(Player player) {
        var uuid = player.getUniqueId();

        if (!permissions.containsKey(uuid)) {
            var permission = player.addAttachment(plugin);
            permissions.put(uuid, permission);
        }

        return permissions.get(uuid);
    }
}
