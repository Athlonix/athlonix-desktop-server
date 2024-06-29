package athlonix.athlonix_server.resource;

import athlonix.athlonix_server.model.Version;
import athlonix.athlonix_server.repository.VersionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/version")
public class VersionResource {

    public Version getMostRecentVersion() throws IOException {
        VersionRepository versionRepository = new VersionRepository();
        return versionRepository.getMostRecentVersion();
    }

}
