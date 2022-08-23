package tech.asmussen.grappler.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tech.asmussen.grappler.Grappler;

import java.util.Objects;

public class FishEvent implements Listener {
	
	@EventHandler
	public void onFish(PlayerFishEvent event) {
		
		final ItemStack playerItem = event.getPlayer().getInventory().getItemInMainHand();
		final ItemStack neededItem = Grappler.getGrapplingGun();
		
		if (!playerItem.getType().equals(neededItem.getType()) && Objects.equals(playerItem.getItemMeta(), neededItem.getItemMeta()))
			
			return;
		
		Player player = event.getPlayer();
		
		if (Grappler.cooldowns.getOrDefault(player, 0) > 0) {
			
			player.sendMessage(Grappler.colorize("&cYou must wait " + Grappler.cooldowns.get(player) + (Grappler.cooldowns.get(player) == 1 ? " second" : " seconds") + " before you can use the grappling hook again!"));
			
			event.setCancelled(true);
			
			return;
		}
		
		if (event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() == PlayerFishEvent.State.IN_GROUND) { // If the rod is reeled in.
			
			double multiplier = event.getHook().getLocation().distance(player.getLocation());
			
			Vector hookVector = event.getHook().getLocation().toVector();
			Vector playerVector = player.getLocation().toVector();
			
			double x = hookVector.getX() - playerVector.getX();
			double z = hookVector.getZ() - playerVector.getZ();
			
			player.setVelocity(new Vector(x, 1, z).normalize().multiply(multiplier));
			
			Grappler.cooldowns.put(player, 2);
			
			player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 10);
		}
	}
}
