package me.puyodead1.randomdupe.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.puyodead1.randomdupe.RandomDupe;
import me.puyodead1.randomdupe.TYPES;
import me.puyodead1.randomdupe.Utils;
import me.randomhashtags.randompackage.addons.CustomEnchant;

public class RandomDupeCommand extends Utils implements CommandExecutor {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();
	private List<String> blacklist = rdConfig.getStringList("blacklist");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player player = sender instanceof Player ? (Player) sender : null;
		if (player != null) {
			if (player.hasPermission("randomdupe.dupe")) {
				ItemStack itemInHand = player.getInventory().getItemInHand();
				final TYPES type = getType(itemInHand);

				// Note to self: maybe dont use so may if statments just for the messages, just
				// dupe after the not INVALID check

				if (type != TYPES.INVALID) {
					if (!blacklist
							.contains(ChatColor.stripColor(itemInHand.getItemMeta().getDisplayName().toUpperCase()))
							&& rdConfig.getBoolean("settings.allow duping." + type)) {
						dupe(player, itemInHand);
						return true;
					} else {
						player.sendMessage(formatString(rdConfig.getString("messages.not dupable." + type)
								.replace("{ENCHANT_NAME}", CustomEnchant.valueOf(itemInHand).getIdentifier())
								.replace("{RARITY_NAME}", valueOfEnchantRarity(itemInHand).getIdentifier())
								.replace("{DUST_NAME}", getWhiteScrolls().valueOf(itemInHand).getIdentifier())));
						return true;
					}
				} else {
					player.sendMessage(formatString(rdConfig.getString("messages.item not dupable")
							.replace("{ITEM_NAME}", itemInHand.getType().toString())
							.replace("{ITEM_DISPLAYNAME}", itemInHand.getItemMeta().getDisplayName())));
					return true;
				}

//				CustomEnchant ce = CustomEnchant.valueOf(is);
//				EnchantRarity rarity = valueOfEnchantRarity(is);

//				if (rarity != null && !rdConfig.getBoolean("settings.allow duping rarity books")) {
//					player.sendMessage(Utils.formatString(rdConfig.getString("messages.only certain rarity books are dupable")));
//					return true;
//				} else if(ce != null && !rdConfig.getBoolean("settings.allow duping enchant books")) {
//					player.sendMessage(Utils.formatString(rdConfig.getString("messages.only certain enchant books are dupable")));
//					return true;
//				}else if(rarity == null && ce == null) {
//					player.sendMessage(Utils.formatString(rdConfig.getString("messages.not dupable")));
//					return true;
//				} else {
//
//					// TODO: This if statment may not be needed
//					if (rarity != null || ce != null) {
//						if (!blacklist.contains(ce != null ? ce.getIdentifier() : rarity.getIdentifier())) {
//							is.setAmount(rdConfig.getInt("settings.stack size") - player.getItemInHand().getAmount());
//							player.getInventory().addItem(is);
//							
//							player.sendMessage(Utils.formatString(rdConfig.getString("messages.success")));
//							return true;
//						} else {
//							player.sendMessage(Utils.formatString(rdConfig.getString("messages.not dupable")));
//							return true;
//						}
//					}
//				}
			} else {
				player.sendMessage(formatString(rdConfig.getString("messages.no permission")));
				return true;
			}
		}
		return false;
	}
}
