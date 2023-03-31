package io.github.djtpj.origin;

import io.github.djtpj.gui.ItemIcon;
import io.github.djtpj.trait.CompoundAbility;
import io.github.djtpj.trait.IllDefinedTraitException;
import io.github.djtpj.trait.Trait;
import io.github.djtpj.trait.TraitRegistry;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

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

            Trait trait = TraitRegistry.deserializeTrait(jsonTrait);

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

    private static Class<?>[] getConstructorTypes(JSONObject traitObject) {
        ArrayList<Class<?>> results = new ArrayList<>();

        if (traitObject.containsKey("icon")) results.add(ItemIcon.class);
        if (traitObject.containsKey("type")) results.add(Trait.Type.class);

        if (traitObject.containsKey("args")) {
            for (Object arg : (JSONArray) traitObject.get("args")) {
                results.add(arg.getClass());
            }
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
