package me.puyodead1.randomdupe.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Strings;

import me.puyodead1.randomdupe.RandomDupe;
import me.puyodead1.randomdupe.Utils;

public class IdentifierCommand extends Utils implements CommandExecutor {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = sender instanceof Player ? (Player) sender : null;
		if (player != null) {
			if (player.hasPermission("randomdupe.identifier")) {
				final ItemStack itemInHand = player.getItemInHand();
				if(itemInHand.getType() != Material.AIR) {
					final String identifier = getIdentifier(itemInHand);

					if (!Strings.isNullOrEmpty(identifier)) {
						player.sendMessage(
								formatString(rdConfig.getString("messages.identifier").replace("{IDENTIFIER}", identifier)
										.replace("{ITEM_DISPLAYNAME}", itemInHand.getItemMeta().getDisplayName())));
						return true;
					} else {
						player.sendMessage(formatString(rdConfig.getString("messages.no identifier")
								.replace("{ITEM_DISPLAYNAME}", itemInHand.getItemMeta().getDisplayName())));
						return true;
					}
				}
			} else {
				player.sendMessage(formatString(rdConfig.getString("messages.no permission")));
				return true;
			}
		}
		return false;
	}
}
