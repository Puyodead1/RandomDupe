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

		Updater spu = new Updater(this, "http://185.230.160.95/plugins/phd/phd.html");
		spu.enableOut();
		if(spu.needsUpdate())
			spu.update();

		instance = this;
		
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
