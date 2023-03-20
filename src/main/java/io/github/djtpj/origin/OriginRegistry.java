package io.github.djtpj.origin;

import java.util.ArrayList;
import java.util.Objects;

public class OriginRegistry {
    private static OriginRegistry instance;

    private final ArrayList<Origin> registry;

    private OriginRegistry() {
        registry = new ArrayList<>();
    }

    public void register(Origin origin) {
        registry.add(origin);
    }

    public Origin[] registry() {
        return registry.toArray(new Origin[0]);
    }

    public Origin getOrigin(String id) {
        for (Origin origin : registry) {
            if (Objects.equals(origin.getId(), id)) return origin;
        }

        return null;
    }

    public static OriginRegistry getInstance() {
        if (instance == null) {
            instance = new OriginRegistry();
        }
        return instance;
    }
}
