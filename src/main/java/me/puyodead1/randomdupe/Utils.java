package me.puyodead1.randomdupe;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.randomhashtags.randompackage.RandomPackageAPI;
import me.randomhashtags.randompackage.api.addon.WhiteScrolls;
import me.randomhashtags.randompackage.util.RPStorage;


public class Utils extends RPStorage {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();
	private List<String> blacklist = rdConfig.getStringList("blacklist");

	/**
	 * Formats text with chat colors
	 * 
	 * @param text
	 * @return Formatted text
	 */
	public static String formatString(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	/**
	 * Returns type of ItemStack
	 * 
	 * @param item
	 * @return enum type of item
	 */
	public TYPES getType(ItemStack item) {
		if (item != null) {
			if (RandomDupe.rpapi.valueOfCustomEnchant(item) != null) {
				return TYPES.ENCHANT;
			} else if (RandomDupe.rpapi.valueOfCustomEnchantRarity(item) != null) {
				return TYPES.RARITY;
			} else if (WhiteScrolls.getWhiteScrolls().valueOf(item) != null) {
				return TYPES.WHITE_SCROLL;
			} else if (RandomPackageAPI.api.valueOfBlackScroll(item) != null) {
				return TYPES.BLACK_SCROLL;
			} else if (RandomPackageAPI.api.valueOfTransmogScroll(item) != null) {
				return TYPES.TRANSMOG_SCROLL;
			} else if (RandomPackageAPI.api.valueOfMagicDust(item) != null) {
				return TYPES.MAGIC_DUST;
			} else {
				return TYPES.INVALID;
			}
		}

		return null;
	}

	/**
	 * Dupes the specified item
	 * 
	 * @param player
	 * @param item
	 */
	public void dupe(Player player, ItemStack item) {
		item.setAmount(rdConfig.getInt("settings.stack size"));

		player.sendMessage(formatString(
				rdConfig.getString("messages.dupe success").replace("{ITEM_TYPE}", item.getType().toString())
						.replace("{ITEM_DISPLAYNAME}", item.getItemMeta().getDisplayName())));
	}

	/**
	 * Returns the string Identifier associated with the item type
	 * 
	 * @param item
	 * @return identifier or null if invalid
	 */
	public String getIdentifier(ItemStack item) {

		if (RandomDupe.rpapi.valueOfCustomEnchant(item) != null) {
			return RandomDupe.rpapi.valueOfCustomEnchantRarity(item).getIdentifier();
		} else if (RandomDupe.rpapi.valueOfCustomEnchantRarity(item) != null) {
			return RandomDupe.rpapi.valueOfCustomEnchantRarity(item).getIdentifier();
		} else if (WhiteScrolls.getWhiteScrolls().valueOf(item) != null) {
			return WhiteScrolls.getWhiteScrolls().valueOf(item).getIdentifier();
		} else if (RandomPackageAPI.api.valueOfTransmogScroll(item) != null) {
			return RandomPackageAPI.api.valueOfTransmogScroll(item).getIdentifier();
		} else if (RandomPackageAPI.api.valueOfBlackScroll(item) != null) {
			return RandomPackageAPI.api.valueOfBlackScroll(item).getIdentifier();
		} else if (RandomPackageAPI.api.valueOfMagicDust(item) != null) {
			return RandomPackageAPI.api.valueOfMagicDust(item).getIdentifier();
		} else {
			return null;
		}
	}

	/**
	 * Check if an item is blacklisted
	 * 
	 * @param item
	 * @return Boolean of blacklist status or null if invalid
	 */
	public Boolean isBlacklisted(ItemStack item) {
		if (RandomDupe.rpapi.valueOfCustomEnchant(item) != null) {
			return blacklist.contains("ENCHANT:" + RandomDupe.rpapi.valueOfCustomEnchant(item).getIdentifier());
		} else if (RandomDupe.rpapi.valueOfCustomEnchantRarity(item) != null) {
			return blacklist.contains("RARITY:" + RandomDupe.rpapi.valueOfCustomEnchantRarity(item).getIdentifier());
		} else if (RandomPackageAPI.api.valueOfTransmogScroll(item) != null) {
			return blacklist.contains(RandomPackageAPI.api.valueOfTransmogScroll(item).getIdentifier());
		} else if (RandomPackageAPI.api.valueOfMagicDust(item) != null) {
			return blacklist.contains("DUST:" + RandomPackageAPI.api.valueOfMagicDust(item).getIdentifier());
		} else if (getType(item).equals(TYPES.WHITE_SCROLL)) {
			return false;
		} else if (getType(item).equals(TYPES.BLACK_SCROLL)) {
			return false;
		} else {
			return null;
		}

	}
}