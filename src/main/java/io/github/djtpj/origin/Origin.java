package io.github.djtpj.origin;

import io.github.djtpj.trait.CompoundAbility;
import io.github.djtpj.trait.IllDefinedTraitException;
import io.github.djtpj.trait.Trait;
import io.github.djtpj.trait.TraitRegistry;
import io.github.djtpj.gui.ItemIcon;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import static io.github.djtpj.origin.Main.plugin;

/** An Origin is a collection of Traits as defined by the "origins.json" file
 *
 */
@Getter
public class Origin {
    private final String id;
    private final Impact impact;
    private final ItemIcon icon;
    private final Trait[] traits;

    /**
     * @param object The JSONObject to define this origin with
     * @throws NoSuchMethodException Thrown if a trait does not possess a constructor that matches the arguments it was passed
     * @throws InvocationTargetException Thrown if a trait's constructor throws an error
     * @throws InstantiationException Thrown if an abstract trait is attempted to be instantiated
     * @throws IllegalAccessException Thrown if a trait's constructor is not public
     * @throws IllDefinedTraitException Thrown in a trait does not possess the required static ID field
     * @see Trait
     * @see JSONObject
     */
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

            JSONArray args = (JSONArray) jsonTrait.get("args");

            Trait trait;
            if (args == null || args.size() < 1) {
                trait = traitClass.getConstructor().newInstance();
            }

            else {
                Class<?>[] argTypes = getArgTypes(args.toArray());
                trait = traitClass.getConstructor(argTypes).newInstance(args.toArray());
            }

            plugin.getServer().getPluginManager().registerEvents(trait, plugin);

            traits.add(trait);
        }

        this.traits = traits.toArray(new Trait[0]);
    }

    /**
     * @return Returns the formatted name of the Origin
     */
    public String getName() {
        return icon.getColor() + icon.getName();
    }

    /** Enables all of the Origin's traits for a specific player
     * @param player the player to enable the traits for
     */
    public void enable(Player player) {
        for (Trait trait : traits) {
            trait.enable(player);
        }
    }

    /** Disables all of the Origin's traits for a specific player
     * @param player the player to disable the traits for
     */
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

    /**
     * @return All the traits, including the traits inside a {@link io.github.djtpj.trait.CompoundAbility}.
     */
    public Trait[] getAllTraits() {
        ArrayList<Trait> results = new ArrayList<>(Arrays.asList(traits));

        for (Trait trait : traits) {
            if (!(trait instanceof CompoundAbility ability)) continue;

            results.addAll(Arrays.asList(ability.getTraits()));
        }

        return results.toArray(new Trait[0]);
    }
}
