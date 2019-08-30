package me.puyodead1.randomdupe;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.puyodead1.randomdupe.commands.IdentifierCommand;
import me.puyodead1.randomdupe.commands.RandomDupeCommand;

public class RandomDupe extends JavaPlugin {

	public static RandomDupe instance;

	public static RandomDupe getPlugin() {
		return instance;
	}

	public void onEnable() {
		final long started = System.currentTimeMillis();
		instance = this;

//		Updater updater = new Updater(this);
//		updater.enableOut();
//		if(updater.needsUpdate()) {
//		    updater.update();
//        }

		Bukkit.getConsoleSender().sendMessage(Utils
				.formatString("&d[&bRandomDupe&d] &dRandomDupe version: &e" + getDescription().getVersion()));
		
		Bukkit.getConsoleSender().sendMessage(Utils
				.formatString("&d[&bRandomDupe&d] &dAuthor: &ePuyodead1"));
		

		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		getCommand("dupe").setExecutor(new RandomDupeCommand());
		getCommand("identifier").setExecutor(new IdentifierCommand());
		Bukkit.getConsoleSender().sendMessage(Utils
				.formatString("&d[&bRandomDupe&d] &dStarted in &e" + (System.currentTimeMillis() - started) + "ms."));
	}

	public void onDisable() {

	}

}
