package io.github.djtpj.origin;

import io.github.djtpj.PlayerManager;
import io.github.djtpj.cmd.OriginCommand;
import io.github.djtpj.gui.OriginPane;
import io.github.djtpj.gui.OriginPicker;
import io.github.djtpj.trait.IllDefinedTraitException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public final class Main extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getServer().getPluginManager().registerEvents(new OriginPicker(null), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OriginPane(null, null), this);

        getCommand("origin").setExecutor(new OriginCommand());
        getCommand("origin").setTabCompleter(new OriginCommand());

        try {
            String json = getOriginJSON();

            readOrigins(json);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Origin origin = PlayerManager.getInstance().getOrigin(player);

            if (origin == null) continue;

            System.out.println(origin.getName());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void readOrigins(String json) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(json);

        for (Object o : array) {
            JSONObject object = (JSONObject) o;

            try {
                OriginRegistry.getInstance().register(new Origin(object));
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | IllDefinedTraitException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getOriginJSON() throws IOException {
        InputStream inputStream = getResource("origins.json");
        assert inputStream != null;

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        StringBuilder buffer = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line);
        }

        return buffer.toString();
    }
}
