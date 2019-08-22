package me.puyodead1.randomdupe;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.randomhashtags.randompackage.addons.CustomEnchant;
import me.randomhashtags.randompackage.addons.MagicDust;
import me.randomhashtags.randompackage.api.enchantAddons.BlackScrolls;
import me.randomhashtags.randompackage.api.enchantAddons.TransmogScrolls;
import me.randomhashtags.randompackage.api.enchantAddons.WhiteScrolls;
import me.randomhashtags.randompackage.utils.RPStorage;

public class Utils extends RPStorage {

	private FileConfiguration rdConfig = RandomDupe.getPlugin().getConfig();
	private List<String> blacklist = rdConfig.getStringList("blacklist");

	public static String formatString(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public WhiteScrolls getWhiteScrolls() {
		return new WhiteScrolls();
	}

	public BlackScrolls getBlackScrolls() {
		return new BlackScrolls();
	}

	public TransmogScrolls getTransmogScrolls() {
		return new TransmogScrolls();
	}

	/**
	 * Returns type of itemstack
	 * 
	 * @param item
	 * @return Type
	 */
	public TYPES getType(ItemStack item) {
		if (item != null) {

			if (CustomEnchant.valueOf(item) != null) {
				return TYPES.ENCHANT;
			} else if (valueOfEnchantRarity(item) != null) {
				return TYPES.RARITY;
			} else if (getWhiteScrolls().valueOf(item) != null) {
				return TYPES.WHITE_SCROLL;
//			} else if(getBlackScrolls().valueOf(null) != null) {
//				return TYPES.BLACK_SCROLL;
			} else if (getTransmogScrolls().valueOf(item) != null) {
				return TYPES.TRANSMOG_SCROLL;
			} else if (MagicDust.valueOf(item) != null) {
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
	 * @param item
	 * @return String identifier
	 */
	public String getIdentifier(ItemStack item) {

		if (CustomEnchant.valueOf(item) != null) {
			return CustomEnchant.valueOf(item).getIdentifier();
		} else if (valueOfEnchantRarity(item) != null) {
			return valueOfEnchantRarity(item).getIdentifier();
		} else if (getWhiteScrolls().valueOf(item) != null) {
			return getWhiteScrolls().valueOf(item).getIdentifier();
		} else if (getTransmogScrolls().valueOf(item) != null) {
			return getTransmogScrolls().valueOf(item).getIdentifier();
		} else if (MagicDust.valueOf(item) != null) {
			return MagicDust.valueOf(item).getIdentifier();
		} else {
			return null;
		}
	}
	
	public Boolean isBlacklisted(ItemStack item) {
		if (CustomEnchant.valueOf(item) != null) {
			return blacklist.contains("ENCHANT:" + CustomEnchant.valueOf(item).getIdentifier());
		} else if (valueOfEnchantRarity(item) != null) {
			return blacklist.contains("RARITY:" + valueOfEnchantRarity(item).getIdentifier());
		} else if (getWhiteScrolls().valueOf(item) != null) {
			return blacklist.contains(getWhiteScrolls().valueOf(item).getIdentifier());
		} else if (getTransmogScrolls().valueOf(item) != null) {
			return blacklist.contains(getTransmogScrolls().valueOf(item).getIdentifier());
		} else if (MagicDust.valueOf(item) != null) {
			return blacklist.contains("DUST:" + MagicDust.valueOf(item).getIdentifier());
		} else {
			return null;
		}
	}
}