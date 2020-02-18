package me.puyodead1.randomdupe.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.puyodead1.randomdupe.RandomDupe;
import me.puyodead1.randomdupe.TYPES;
import me.puyodead1.randomdupe.Utils;

public class RandomDupeCommand extends Utils implements CommandExecutor {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = sender instanceof Player ? (Player) sender : null;
		if (player != null) {
			if (player.hasPermission("randomdupe.dupe")) {
				ItemStack itemInHand = player.getInventory().getItemInHand();
				if (itemInHand.getType() != Material.AIR) {
					final TYPES type = getType(itemInHand);

					if (type != TYPES.INVALID) {
						if (!isBlacklisted(itemInHand) && rdConfig.getBoolean("settings.allow duping." + type)) {
							dupe(player, itemInHand);
							return true;
						} else {
							player.sendMessage(formatString(rdConfig.getString("messages.not dupable." + type)
									.replace("{ENCHANT_NAME}", getIdentifier(itemInHand))
									.replace("{RARITY_NAME}", getIdentifier(itemInHand))
									.replace("{DUST_NAME}", getIdentifier(itemInHand))));
							return true;
						}
					} else {
						player.sendMessage(formatString(rdConfig.getString("messages.item not dupable")
								.replace("{ITEM_TYPE}", itemInHand.getType().toString())
								.replace("{ITEM_DISPLAYNAME}", itemInHand.getItemMeta().hasDisplayName() ? itemInHand.getItemMeta().getDisplayName() : itemInHand.getType().name())));
						return true;
					}
				} else {
					player.sendMessage(formatString(rdConfig.getString("messages.cannot dupe air")));
					return true;
				}
			} else {
				player.sendMessage(formatString(rdConfig.getString("messages.no permission")));
				return true;
			}
		}
		return false;
	}
}
