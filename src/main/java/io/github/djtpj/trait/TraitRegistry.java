package io.github.djtpj.trait;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

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
            try {
                Field idField = aClass.getDeclaredField("ID");
                String classId = (String) idField.get(null);

                if (Objects.equals(classId, id)) {
                    return aClass;
                }
            } catch (NoSuchFieldException e) {
                throw new IllDefinedTraitException("Ability \"" + aClass.getName() + "\" does not have the required static \"ID\" field.");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
