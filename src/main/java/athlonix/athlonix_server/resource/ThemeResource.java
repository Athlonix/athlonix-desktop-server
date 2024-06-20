package athlonix.athlonix_server.resource;

import athlonix.athlonix_server.model.Theme;
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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.get;

@RestController
@RequestMapping("/theme")
public class ThemeResource {
    private static final String THEME_DIRECTORY = "./themes";
    private static final String THEME_IMAGE_DIRECTORY = "./themes_images";


    @GetMapping
    public List<Theme> getThemes() throws IOException {
        ThemeRepository themeRepository = new ThemeRepository();
        return themeRepository.getAllThemes();
    }

    @GetMapping("/image/{theme}")
    public void getThemeImage(HttpServletResponse response, @PathVariable("theme") String theme) throws IOException {
        String themeImagePath = theme + ".png";
        Path imageFilePath = get(THEME_IMAGE_DIRECTORY).toAbsolutePath().normalize().resolve(themeImagePath);
        if(!Files.exists(imageFilePath)) {
            throw new FileNotFoundException(theme + " file not found");
        }

        Resource imageResource = new UrlResource(imageFilePath.toUri());

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        imageResource.getInputStream().transferTo(response.getOutputStream());
    }

    @GetMapping("{theme}")
    public ResponseEntity<Resource> downloadTheme(@PathVariable("theme") String theme) throws IOException {
        String themeFile = theme + ".css";
        Path filePath = get(THEME_DIRECTORY).toAbsolutePath().normalize().resolve(themeFile);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(theme + " file not found");
        }
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", themeFile);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(headers).body(resource);
    }

}
