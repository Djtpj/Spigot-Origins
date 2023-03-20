package io.github.djtpj.origin;

import io.github.djtpj.trait.CompoundAbility;
import io.github.djtpj.trait.IllDefinedTraitException;
import io.github.djtpj.trait.Trait;
import io.github.djtpj.trait.TraitRegistry;
import io.github.djtpj.ui.ItemIcon;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import static io.github.djtpj.origin.Main.plugin;

@Getter
public class Origin {
    private final String id;
    private final Impact impact;
    private final ItemIcon icon;
    private final Trait[] traits;

    public Origin(JSONObject object) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IllDefinedTraitException {
        id = (String) object.get("id");
        impact = Impact.valueOf(((String) object.get("impact")).toUpperCase());

        this.icon = new ItemIcon((JSONObject) object.get("icon"));

        ArrayList<Trait> traits = new ArrayList<>();
        JSONArray jsonTraits = (JSONArray) object.get("traits");

        for (Object traitObject : jsonTraits) {
            JSONObject jsonTrait = (JSONObject) traitObject;

            String id = (String) jsonTrait.get("id");
            Class<? extends Trait> traitClass = TraitRegistry.getTrait(id);

            if (traitClass == null) {
                plugin.getLogger().log(Level.WARNING, "There was no trait found with ID \"" + id + "\". Please check that there are no typos.");
                continue;
            }

            Object[] args = (Object[]) jsonTrait.get("args");

            Trait trait;
            if (args == null || args.length < 1) {
                trait = traitClass.getConstructor().newInstance();
            }

            else {
                Class<?>[] argTypes = getArgTypes(args);
                trait = traitClass.getConstructor(argTypes).newInstance(args);
            }

            plugin.getServer().getPluginManager().registerEvents(trait, plugin);

            traits.add(trait);
        }

        this.traits = traits.toArray(new Trait[0]);
    }

    public String getName() {
        return icon.getColor() + icon.getName();
    }

    public void enable(Player player) {
        for (Trait trait : traits) {
            trait.enable(player);
        }
    }

    public void disable(Player player) {
        for (Trait trait : traits) {
            trait.disable(player);
        }
    }

    private static Class<?>[] getArgTypes(Object[] args) {
        ArrayList<Class<Object>> results = new ArrayList<>();

        for (Object arg : args) {
            results.add((Class<Object>) arg.getClass());
        }

        return results.toArray(new Class[0]);
    }

    public Trait[] getAllTraits() {
        ArrayList<Trait> results = new ArrayList<>(Arrays.asList(traits));

        for (Trait trait : traits) {
            if (!(trait instanceof CompoundAbility ability)) continue;

            results.addAll(Arrays.asList(ability.getTraits()));
        }

        return results.toArray(new Trait[0]);
    }
}
