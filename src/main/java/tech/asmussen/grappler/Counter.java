package tech.asmussen.grappler;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Counter extends BukkitRunnable {
	
	@Override
	public void run() {
		
		for (Player player : Grappler.cooldowns.keySet()) {
			
			if (Grappler.cooldowns.get(player) > 0) {
				
				Grappler.cooldowns.put(player, Grappler.cooldowns.get(player) - 1);
				
			} else {
				
				Grappler.cooldowns.remove(player);
				
				player.sendMessage(Grappler.colorize("&aYou can now use the grappling hook again!"));
			}
		}
	}
}
