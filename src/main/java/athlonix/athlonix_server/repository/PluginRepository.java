package athlonix.athlonix_server.repository;

import athlonix.athlonix_server.model.Plugin;
import athlonix.athlonix_server.model.Theme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;

public class PluginRepository {
    public static final String PLUGIN_DIRECTORY = "./plugins";
    private final List<Plugin> plugins = List.of(
            new Plugin("Calendrier","Un superbe calendrier facilitant la gestion des taches et activités")
    );

    public PluginRepository() throws IOException {

        for (Plugin plugin: plugins) {
            String pluginFile = plugin.getName() + ".jar";
            Path filePath = get(PLUGIN_DIRECTORY).toAbsolutePath().normalize().resolve(pluginFile);
            long size = Files.size(filePath);
            String formatedSize = formatFileSize(size);
            plugin.setSize(formatedSize);
        }
    }

    public List<Plugin> getAllPlugins() {
        return plugins;
    }
    private String formatFileSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < (1024 * 1024)) {
            return size / 1024 + " KB";
        } else if (size < (1024 * 1024 * 1024)) {
            return size / (1024 * 1024) + " MB";
        } else {
            return size / (1024 * 1024 * 1024) + " GB";
        }
    }
}
