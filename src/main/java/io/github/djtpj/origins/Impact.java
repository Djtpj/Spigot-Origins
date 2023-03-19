package io.github.djtpj.origins;

import io.github.djtpj.gui.GuiUtils;
import lombok.Getter;
import org.bukkit.Material;

public enum Impact {
    NONE(GuiUtils.fillerItem().getType()),
    LOW(Material.GREEN_STAINED_GLASS_PANE),
    MEDIUM(Material.YELLOW_STAINED_GLASS_PANE),
    HIGH(Material.RED_STAINED_GLASS_PANE);

    @Getter
    private final Material icon;

    Impact(Material icon) {
        this.icon = icon;
    }
}
