package tech.asmussen.grappler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import tech.asmussen.grappler.commands.GetGrapplingGun;
import tech.asmussen.grappler.events.FishEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class Grappler extends JavaPlugin {
	
	public static HashMap<Player, Integer> cooldowns = new HashMap<>();
	
	public static String colorize(String text) {
		
		return text.replaceAll("&", "§");
	}
	
	public static ItemStack getGrapplingGun() {
		
		ItemStack item = new ItemStack(Material.FISHING_ROD);
		ItemMeta meta = item.getItemMeta();
		
		assert meta != null;
		
		meta.setDisplayName(colorize("&9&lGrappling Gun"));
		meta.setLore(generateLore(colorize("\n&7&oRight-click to grapple to point.")));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static List<String> generateLore(String pureLore) {
		
		return Arrays.asList(pureLore.split("\n"));
	}
	
	public static int secondsToTicks(int seconds) {
		
		return seconds * 20;
	}
	
	@Override
	public void onEnable() {
		
		// Register Commands.
		Objects.requireNonNull(getServer().getPluginCommand("grappler"), "Command is null!").setExecutor(new GetGrapplingGun());
		
		// Register Events.
		Bukkit.getPluginManager().registerEvents(new FishEvent(), this);
		
		// Register Tasks.
		new Counter().runTaskTimer(this, 0, secondsToTicks(1));
		
		Bukkit.getConsoleSender().sendMessage("Grappler enabled!");
	}
	
	@Override
	public void onDisable() {
		
		Bukkit.getConsoleSender().sendMessage("Grappler disabled!");
	}
}
