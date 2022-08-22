package tech.asmussen.grappler.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import tech.asmussen.grappler.CooldownManager;
import tech.asmussen.grappler.Grappler;

import java.util.Objects;

public class FishEvent implements Listener {
	
	public static void throwPlayer(Player player, Vector velocity) {
		
		player.setVelocity(velocity);
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		
		final ItemStack playerItem = event.getPlayer().getInventory().getItemInMainHand();
		final ItemStack neededItem = Grappler.getGrapplingGun();
		
		if (!playerItem.getType().equals(neededItem.getType()) && Objects.equals(playerItem.getItemMeta(), neededItem.getItemMeta()))
			return;
		
		if (event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() == PlayerFishEvent.State.IN_GROUND) { // If the rod is reeled in.
			
			double multiplier = event.getHook().getLocation().distance(event.getPlayer().getLocation());
			
			Vector hookVector = event.getHook().getLocation().toVector();
			Vector playerVector = event.getPlayer().getLocation().toVector();
			
			double x = hookVector.getX() - playerVector.getX();
			double z = hookVector.getZ() - playerVector.getZ();
			
			Vector velocity = new Vector(x, 1, z).normalize().multiply(multiplier);
			
			Player player = event.getPlayer();
			
			CooldownManager cooldownManager = new CooldownManager();
			
			cooldownManager.setCooldown(player.getUniqueId(), 3);
			
			throwPlayer(player, velocity);
			
			player.playSound(hookVector.toLocation(player.getWorld()), Sound.ENTITY_GHAST_SHOOT, 1, 1);
		}
	}
}
