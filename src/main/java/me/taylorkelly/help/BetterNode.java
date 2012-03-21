
package me.taylorkelly.help;

import java.util.HashMap;
import java.util.Map;
//import org.bukkit.util.config.ConfigurationNode;

public class BetterNode extends Help{

    protected BetterNode(Map<String, Object> root) {
        this.getConfig().addDefaults(root);
       }

    public BetterNode() {
        this(new HashMap<String, Object>());
    }


    public int getInt(String path, int defaultValue) {
        if (this.getConfig().get(path) == null) {
            this.getConfig().set(path, defaultValue);
        }
        return this.getConfig().getInt(path, defaultValue);
    }


    public String getString(String path, String defaultValue) {
        if (this.getConfig().get(path) == null) {
            this.getConfig().set(path, defaultValue);
        }
        return this.getConfig().getString(path, defaultValue);
    }


    public boolean getBoolean(String path, boolean defaultValue) {
        if (this.getConfig().get(path) == null) {
            this.getConfig().set(path, defaultValue);
        }
        return this.getConfig().getBoolean(path, defaultValue);
    }
}
