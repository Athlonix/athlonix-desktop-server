package athlonix.athlonix_server.repository;

import athlonix.athlonix_server.model.Theme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Paths.get;

public class ThemeRepository {

    public static final String THEME_DIRECTORY = "./themes";
    private final List<Theme> themes = new ArrayList<>();

    public ThemeRepository() throws IOException {
        List<String> themesNames = List.of("cupertino-dark","cupertino-light","dracula","nord-dark","nord-light","primer-dark","primer-light");

        for (String themeName: themesNames) {
            String themeFile = themeName + ".css";
            Path filePath = get(THEME_DIRECTORY).toAbsolutePath().normalize().resolve(themeFile);
            long size = Files.size(filePath);
            String formatedSize = formatFileSize(size);
            Theme currentTheme = new Theme(themeName,formatedSize);

            this.themes.add(currentTheme);
        }

    }

    public List<Theme> getAllThemes() {
        return themes;
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
