package io.github.djtpj.trait.traits;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.djtpj.authenticator.authenticators.PlayerAuthenticator;
import io.github.djtpj.origin.Main;

public class Airborne extends RunnableSimpleTrait {
  public static final String ID = "airborne";
  private static final String TAG = "last-airborne";

  private static Date getLastAirborneDate(Player player) {
    try {
      return new Date(player.getMetadata(TAG).get(0).asLong());
    } catch (Exception e) {
      return new Date();
    }
  }

  private static boolean cancelAirborne(Player player) {
    Date date = getLastAirborneDate(player);

    final int ALLOWED_FLIGHT_SECONDS = 15;

    return 
      Math.abs(date.getTime() - new Date().getTime()) > 
        // Convert to milliseconds
        (ALLOWED_FLIGHT_SECONDS * 1_000);
  }

  private static void updateAirborne(Player player) {
    player.setMetadata(TAG, new FixedMetadataValue(Main.plugin, new Date().getTime()));
  }

  public Airborne() {
    super("Airborne", "You are able to fly, but you can't fly on an empty stomach.", ChatColor.AQUA, Material.FEATHER, Type.POSITIVE);
  }

  @EventHandler
  public void reallow(PlayerRespawnEvent event) {
    if (!new PlayerAuthenticator<>(this).authenticate(event)) return;

    Player player = event.getPlayer();

    player.setAllowFlight(true);
  }

  @EventHandler
  public void toggleHunger(PlayerToggleFlightEvent event) {
    if (!new PlayerAuthenticator<>(this).authenticate(event)) return;

    Player player = event.getPlayer();

    if (event.isFlying()) {
      updateAirborne(player);
    }
  }

  @Override
  protected void onEnable(Player player) {
    player.setAllowFlight(true);
  }

  @Override
  protected void onDisable(Player player) {
    player.setAllowFlight(false);
  }

  @Override
  protected void onTick(Player player) {
    if (cancelAirborne(player)) {
      player.setFlying(false);
    }
  }
}
