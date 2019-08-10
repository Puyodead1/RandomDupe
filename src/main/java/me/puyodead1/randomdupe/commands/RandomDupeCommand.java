package me.puyodead1.randomdupe.commands;

import java.util.List;

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
	private List<String> blacklist = rdConfig.getStringList("blacklist");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = sender instanceof Player ? (Player) sender : null;
		if (player != null) {
			if (player.hasPermission("randomdupe.dupe")) {
				ItemStack is = player.getInventory().getItemInHand().clone();
				CustomEnchant ce = CustomEnchant.valueOf(is);
				EnchantRarity rarity = valueOfEnchantRarity(is);
				if (rarity != null && !rdConfig.getBoolean("settings.allow duping rarity books")) {
					player.sendMessage(Utils.formatString(rdConfig.getString("messages.only certain rarity books are dupable")));
					return true;
				} else if(ce != null && !rdConfig.getBoolean("settings.allow duping enchant books")) {
					player.sendMessage(Utils.formatString(rdConfig.getString("messages.only certain enchant books are dupable")));
					return true;
				}else if(rarity == null && ce == null) {
					player.sendMessage(Utils.formatString(rdConfig.getString("messages.not dupable")));
					return true;
				} else {

					// TODO: This if statment may not be needed
					if (rarity != null || ce != null) {
						if (!blacklist.contains(ce != null ? ce.getIdentifier() : rarity.getIdentifier())) {
							is.setAmount(rdConfig.getInt("settings.stack size") - player.getItemInHand().getAmount());
							player.getInventory().addItem(is);
							
							player.sendMessage(Utils.formatString(rdConfig.getString("messages.success")));
							return true;
						} else {
							player.sendMessage(Utils.formatString(rdConfig.getString("messages.not dupable")));
							return true;
						}
					}
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
