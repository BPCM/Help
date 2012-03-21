package me.taylorkelly.help;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public final class HelpSettings {

    private static final String settingsFile = "Help.yml";
    public boolean allowPluginOverride = false,
            allowPluginHelp = true, // if plugins can pass Help custom entries
            savePluginHelp = false, // if the help entries registered should be saved
            sortPluginHelp = true, // if added entries should also be sorted (by command string)
            shortenEntries = false, // entries shown on only one line
            useWordWrap = true, // smart(er) word wrapping
            wordWrapRight = true; // wrap to the right
    public int entriesPerPage = 9;
    ChatColor commandColor = ChatColor.RED,
            commandBracketColor = ChatColor.GRAY,
            descriptionColor = ChatColor.WHITE,
            introDashColor = ChatColor.GOLD,
            introTextColor = ChatColor.WHITE;

    public HelpSettings() {
    }

    public HelpSettings(File dataFolder) {
        initialize(dataFolder);
    }

    public void initialize(File dataFolder) {
        try {
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File configFile = new File(dataFolder, settingsFile);
            //BetterConfig betterConfig = new BetterConfig(configFile);
            //betterConfig.load();
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.load(configFile);

            entriesPerPage = yamlConfiguration.getInt("entriesPerPage", entriesPerPage);
            allowPluginOverride = yamlConfiguration.getBoolean("allowPluginOverride", allowPluginOverride);
            allowPluginHelp = yamlConfiguration.getBoolean("allowPluginHelp", allowPluginHelp);
            savePluginHelp = yamlConfiguration.getBoolean("savePluginHelp", savePluginHelp);
            sortPluginHelp = yamlConfiguration.getBoolean("sortPluginHelp", sortPluginHelp);

            shortenEntries = yamlConfiguration.getBoolean("shortenEntries", shortenEntries);
            useWordWrap = yamlConfiguration.getBoolean("useWordWrap", useWordWrap);
            wordWrapRight = yamlConfiguration.getBoolean("wordWrapRight", wordWrapRight);

            yamlConfiguration.save(configFile);
        } catch (Exception ex) {
            HelpLogger.Log(Level.SEVERE, "Error loading Settings", ex);
        }
    }
}
