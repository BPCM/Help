package me.taylorkelly.help;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

public class LegacyHelpLoader {

    public static void load(File dataFolder, HelpList list) {
        FileInputStream input = null;

        try {
            File extraHelp = new File(dataFolder, "ExtraHelp.yml");
            final Yaml yaml = new Yaml(new SafeConstructor());
            Map<String, Object> root;
            if (!extraHelp.exists()) {
                //HelpLogger.info("No extra help entries loaded");
                return;
            }
            input = new FileInputStream(extraHelp);
            root = (Map<String, Object>) yaml.load(new UnicodeReader(input));
            int count = 0;

            for (String helpKey : root.keySet()) {
                Map<String, Object> helpNode = (Map<String, Object>) root.get(helpKey);

                if (!helpNode.containsKey("command")) {
                    HelpLogger.warning("A Help entry node is missing a command name");
                    continue;
                }
                String command = helpNode.get("command").toString();
                if (!helpNode.containsKey("description")) {
                    HelpLogger.warning(command + "'s Help entry is missing a description");
                    continue;
                }
                String description = helpNode.get("description").toString();
                if (!helpNode.containsKey("plugin")) {
                    HelpLogger.warning(command + "'s Help entry is missing a plugin");
                    continue;
                }
                String plugin = helpNode.get("plugin").toString();

                boolean main = false;
                if (helpNode.containsKey("main")) {
                    if (helpNode.get("main") instanceof Boolean) {
                        main = (Boolean) helpNode.get("main");
                    } else {
                        HelpLogger.warning(command + "'s Help entry has main as a non-boolean. Defaulting to false");
                    }
                }

                ArrayList<String> permissions = new ArrayList<String>();
                if (helpNode.containsKey("permissions")) {
                    if (helpNode.get("permissions") instanceof List) {
                        for (Object permission : (List) helpNode.get("permissions")) {
                            permissions.add(permission.toString());
                        }
                    } else {
                        permissions.add(helpNode.get("permissions").toString());
                    }
                }
                convert(command, description, plugin, main, permissions);
                count++;
            }
            HelpLogger.info(count + " extra help entries converted!");
            extraHelp.delete();
        } catch (Exception ex) {
            HelpLogger.severe("Error!", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    //HelpLogger.Log(Level.SEVERE, ex);
                }
            }
        }
    }

    private static void convert(String command, String description, String plugin, boolean main, ArrayList<String> permissions) {
        File folder = new File(new File("plugins", "Help"), "ExtraHelp");
        File file = new File(folder, plugin + ".yml");
        /*if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(LegacyHelpLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        BetterConfig betterConfig = null;
        try {
            betterConfig = new BetterConfig(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        assert betterConfig != null;
        betterConfig.load();

        String node = command.replace(" ", "");
        betterConfig.setProperty(node + ".command", command);
        betterConfig.setProperty(node + ".description", description);
        betterConfig.setProperty(node + ".plugin", plugin);
        if (main) {
            betterConfig.setProperty(node + ".main", main);
        }
        if (!permissions.isEmpty()) {
            betterConfig.setProperty(node + ".permissions", permissions);
        }
        betterConfig.setProperty(node + ".visible", true);
        betterConfig.save();
    }
}
