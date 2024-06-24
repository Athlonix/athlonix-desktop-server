package athlonix.athlonix_server.resource;

import athlonix.athlonix_server.model.Plugin;
import athlonix.athlonix_server.model.Theme;
import athlonix.athlonix_server.repository.PluginRepository;
import athlonix.athlonix_server.repository.ThemeRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static athlonix.athlonix_server.repository.ThemeRepository.THEME_DIRECTORY;
import static java.nio.file.Paths.get;
@RestController
@RequestMapping("/plugin")
public class PluginResource {
    private static final String PLUGINS_DIRECTORY = "./plugins";


    @GetMapping
    public List<Plugin> getPlugins() throws IOException {
        PluginRepository pluginRepository = new PluginRepository();
        return pluginRepository.getAllPlugins();
    }

    @GetMapping("{plugin}")
    public ResponseEntity<Resource> downloadPlugin(@PathVariable("plugin") String plugin) throws IOException {
        String pluginFile = plugin + ".jar";
        Path filePath = get(PLUGINS_DIRECTORY).toAbsolutePath().normalize().resolve(pluginFile);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(plugin + " file not found");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", pluginFile);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(headers).body(resource);
    }
}
