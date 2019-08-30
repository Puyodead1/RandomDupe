package me.puyodead1.randomdupe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class Updater {

    private JsonParser parser = new JsonParser();

    private URL apiURL;
    private JavaPlugin plugin;

    private boolean cancelled = false;

    public Updater(JavaPlugin plugin) {
        try {
            apiURL = new URL("http://localhost/private/UpdateAPI.php?pluginName=" + plugin.getDescription().getName() + "&mode=getlatestversion");
        } catch (MalformedURLException e) {
            cancelled = true;
            plugin.getLogger().log(Level.WARNING, "Error: Bad URL while checking {0} !", plugin.getName());
        }
        this.plugin = plugin;
    }
    private String version = "";
    private boolean out = true;

    /**
     * Enable a console output if new Version is availible
     */
    public void enableOut() {
        out = true;
    }

    /**
     * Disable a console output if new Version is availible
     */
    public void disableOut() {
        out = false;
    }

    public boolean needsUpdate() {
        if (cancelled) {
            return false;
        }
        try {
            URLConnection con = apiURL.openConnection();

            JsonElement root = parser.parse(new InputStreamReader(con.getInputStream()));
            String version = root.getAsJsonObject().get("latest_version").getAsString();

            if (newVersionAvailiable(plugin.getDescription().getVersion(), version.replaceAll("[a-zA-z ]", ""))) {
                if (out) {
                    plugin.getLogger().log(Level.INFO, "New Version found: {0}", version.replaceAll("[a-zA-z ]", ""));
                }

                return true;
            } else {
                if (out) {
                    plugin.getLogger().log(Level.INFO, "No new version found.");
                }
            }

        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Error in checking update for ''{0}''!", plugin.getName());
            plugin.getLogger().log(Level.WARNING, "Error: ", e);
        }

        return false;
    }

    /**
     * Checks if there is a new Version
     *
     * @param oldv
     * @param newv
     * @return if it is newer
     */
    public boolean newVersionAvailiable(String oldv, String newv) {
        return !oldv.equals(newv);
    }

    /**
     * Executes the Update and trys to install it
     */
    public void update() {
        try {
            String url = apiURL + "?pluginName=" + plugin.getDescription().getName() + "&mode=getupdateurl";
            URL download = new URL(url);
            URLConnection con = download.openConnection();
            JsonElement root = parser.parse(new InputStreamReader(con.getInputStream()));
            String downloadURL = root.getAsJsonObject().get("asset_url").getAsString();

            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {
                if (out) {
                    plugin.getLogger().log(Level.INFO, "Trying to download from: " + downloadURL);
                }
                in = new BufferedInputStream(download.openStream());
                fout = new FileOutputStream("plugins/" + downloadURL.split("/")[downloadURL.split("/").length - 1]);

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }

            if (out) {
                plugin.getLogger().log(Level.INFO, "Succesfully downloaded file: {0}", downloadURL);
                plugin.getLogger().log(Level.INFO, "To install the new features you have to restart your server!");
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Unable to download update!", e);
        }
    }
}
