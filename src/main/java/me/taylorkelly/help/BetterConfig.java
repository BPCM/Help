package me.taylorkelly.help;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
//import org.bukkit.util.config.Configuration;

public class BetterConfig extends Help {

    public BetterConfig(File file) throws IOException, InvalidConfigurationException {
        this.getConfig().load(file);
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


    public BetterNode getNode(String path) {
        if (this.getConfig().get(path) == null || !(this.getConfig().get(path) instanceof Map)) {
            BetterNode node = new BetterNode();
            this.getConfig().set(path, new HashMap<String, Object>());
            return node;
        } else {
            Object raw = this.getConfig().get(path);
            return new BetterNode((Map<String, Object>) raw);
        }

    }

    public void load(){
        this.reloadConfig();
    }

    public void setProperty(String path, Object command) {
        this.getConfig().set(path,command);
    }


    public void save() {
        this.saveConfig();
    }
}
