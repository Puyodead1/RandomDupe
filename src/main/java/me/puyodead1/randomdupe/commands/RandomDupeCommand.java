package me.puyodead1.randomdupe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.puyodead1.randomdupe.RandomDupe;
import me.puyodead1.randomdupe.Utils;
import me.randomhashtags.randompackage.addons.CustomEnchant;
import me.randomhashtags.randompackage.addons.EnchantRarity;
import me.randomhashtags.randompackage.utils.RPStorage;

public class RandomDupeCommand extends RPStorage implements CommandExecutor {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = sender instanceof Player ? (Player) sender : null;
		if (player != null) {
			if (player.hasPermission("randomdupe.dupe")) {
				ItemStack is = player.getInventory().getItemInHand().clone();
				CustomEnchant ce = CustomEnchant.valueOf(is);
				EnchantRarity rarity = valueOfEnchantRarity(is);
				if (rarity != null || ce != null) {
					is.setAmount(rdConfig.getInt("settings.stack size") - player.getItemInHand().getAmount());
					player.getInventory().addItem(is);
				}
				return true;
			} else {
				player.sendMessage(Utils.formatString(rdConfig.getString("messages.no permission")));
				return true;
			}
		}
		return false;
	}
}
