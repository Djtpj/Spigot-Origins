package io.github.djtpj.trait.traits;

import io.github.djtpj.authenticator.authenticators.ItemConsumptionAuthenticator;
import io.github.djtpj.trait.Ability;
import io.github.djtpj.trait.UtilityAbility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import javax.annotation.Nonnull;

import static org.bukkit.Material.*;

/**
 * DietaryRestriction is an ability that prevents a player from eating certain sets of food.
 * @see RestrictionType
 */
@UtilityAbility
public class DietaryRestriction extends Ability {
    public static final String ID = "dietary-restriction";

    private final RestrictionType restrictionType;

    public DietaryRestriction(RestrictionType type) {
        super(type.name, type.description, type.color, type.icon, Type.NEGATIVE);

        this.restrictionType = type;
    }

    public DietaryRestriction(String type) {
        this(RestrictionType.valueOf(type));
    }

    @EventHandler
    public void preventEat(PlayerItemConsumeEvent event) {
        if (!new ItemConsumptionAuthenticator(this, restrictionType.bannedFood).authenticate(event)) return;

        event.setCancelled(true);
    }

    /**
     * A RestrictionType is a predefined set of foods under an {@link io.github.djtpj.gui.ItemIcon}
     * @see io.github.djtpj.gui.ItemIcon
     */
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
