package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.Authenticator;
import io.github.djtpj.authenticator.authenticators.ItemConsumptionAuthenticator;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.CompoundAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.bukkit.Material.*;

public class DietaryRestriction extends Ability<PlayerItemConsumeEvent> {
    public static final String ID = "dietary-restriction";

    private final RestrictionType restrictionType;

    public DietaryRestriction(RestrictionType type) {
        super(type.name, type.description, type.color, type.icon, Type.NEGATIVE);

        this.restrictionType = type;
    }

    public DietaryRestriction(String type) {
        this(RestrictionType.valueOf(type));
    }

    public DietaryRestriction(CompoundAbility ability, RestrictionType restrictionType) {
        super(ability);

        this.restrictionType = restrictionType;
    }

    @Nullable
    @Override
    protected Authenticator<? super PlayerItemConsumeEvent> getAuthenticator() {
        return new ItemConsumptionAuthenticator(this, restrictionType.bannedFood);
    }

    @EventHandler
    public void preventEat(PlayerItemConsumeEvent event) {
        if (!getAuthenticator().authenticate(event)) return;

        event.setCancelled(true);
    }

    enum RestrictionType {
        VEGETARIAN(
                "Vegetarian", "You cannot eat meat of any kind.", ChatColor.GREEN, CARROT,

                BEEF,
                COOKED_BEEF,
                PORKCHOP,
                COOKED_PORKCHOP,
                CHICKEN,
                COOKED_CHICKEN,
                COD,
                COOKED_COD,
                SALMON,
                COOKED_SALMON,
                MUTTON,
                COOKED_MUTTON,
                RABBIT,
                COOKED_RABBIT,
                ROTTEN_FLESH,
                TROPICAL_FISH,
                PUFFERFISH,
                RABBIT_STEW
        );

        public final String name, description;
        public final ChatColor color;
        public final Material icon;

        public final Material[] bannedFood;

        RestrictionType(String name, String description, ChatColor color, Material icon, @Nonnull Material... bannedFood) {
            this.name = name;
            this.description = description;
            this.color = color;
            this.icon = icon;
            this.bannedFood = bannedFood;
        }
    }
}
