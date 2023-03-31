package io.github.djtpj.trait;

import io.github.djtpj.gui.ItemIcon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

import static io.github.djtpj.origin.Main.plugin;

/**
 * Static Registry of all the {@link Trait}s' classes as gotten by a static reflection call
 */
public class TraitRegistry {
    public static final Class<? extends Trait>[] registry;

    static {
        Reflections reflections = new Reflections("io.github.djtpj.trait.traits");
        Set<Class<? extends Trait>> classes = reflections.getSubTypesOf(Trait.class);

        ArrayList<Class<? extends Trait>> results = new ArrayList<>(classes);

        registry = (Class<? extends Trait>[]) results.toArray(new Class[0]);
    }

    public static Class<? extends Trait> getTrait(String id) throws IllDefinedTraitException {
        for (Class<? extends Trait> aClass : registry) {
            if (aClass.isAnonymousClass() || Modifier.isAbstract(aClass.getModifiers())) continue;

            try {
                Field idField = aClass.getDeclaredField("ID");
                String classId = (String) idField.get(null);

                if (Objects.equals(classId, id)) {
                    return aClass;
                }
            } catch (NoSuchFieldException e) {
                throw new IllDefinedTraitException("SimpleTrait \"" + aClass.getName() + "\" does not have the required static \"ID\" field.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Nullable
    public static Trait deserializeTrait(JSONObject jsonTrait) throws IllDefinedTraitException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String id = (String) jsonTrait.get("id");

        Class<? extends Trait> aClass = getTrait(id);

        if (aClass == null) {
            plugin.getLogger().log(Level.WARNING, "There was no trait found with ID \"" + id + "\". Please check that there are no typos in the origins.json file.");
            return null;
        }

        JSONArray jsonArgs = (JSONArray) jsonTrait.get("args");

        if (jsonArgs == null) {
           return aClass.getConstructor().newInstance();
        }

        else {
            List<Object> args = new ArrayList<>();

            JSONObject jsonIcon = (JSONObject) jsonTrait.get("icon");
            if (jsonIcon != null) {
                ItemIcon icon = new ItemIcon(jsonIcon);
                Trait.Type type = Trait.Type.valueOf((String) jsonTrait.get("type"));

                args.add(icon);
                args.add(type);
            }

            args.addAll(jsonArgs);

            return aClass.getConstructor(
                    // Grab the class of each argument in the arguments list
                    args.stream()
                            .map(Object::getClass).
                            toArray(Class[]::new)
                    )
                    .newInstance(args.toArray());
        }
    }
}
