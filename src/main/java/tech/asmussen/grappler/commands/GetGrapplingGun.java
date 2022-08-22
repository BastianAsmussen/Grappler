package tech.asmussen.grappler.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import tech.asmussen.grappler.Grappler;

public class GetGrapplingGun implements CommandExecutor {
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		
		((Player) sender).getInventory().addItem(Grappler.getGrapplingGun());
		
		sender.sendMessage("You have been given a grappling gun!");
		
		return true;
	}
}
