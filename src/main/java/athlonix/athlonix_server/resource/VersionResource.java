package athlonix.athlonix_server.resource;

import athlonix.athlonix_server.model.Version;
import athlonix.athlonix_server.repository.VersionRepository;
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

import static java.nio.file.Paths.get;

@RestController
@RequestMapping("/version")
public class VersionResource {

    private final String VERSION_DIRECTORY = "./desktop_versions";

    @GetMapping
    public Version getMostRecentVersion() throws IOException {
        VersionRepository versionRepository = new VersionRepository();
        return versionRepository.getMostRecentVersion();
    }

    @GetMapping("{version}")
    public ResponseEntity<Resource> downloadVersion(@PathVariable("version") String version) throws IOException {
        String versionFile = version + ".zip";
        Path filePath = get(VERSION_DIRECTORY).toAbsolutePath().normalize().resolve(versionFile);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(version + " file not found");
        }
        Resource resource = new UrlResource(filePath.toUri());

        String t = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();
        headers.add("filename", versionFile);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(headers).body(resource);
    }

}
