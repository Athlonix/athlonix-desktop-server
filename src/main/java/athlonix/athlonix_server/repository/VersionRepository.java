package athlonix.athlonix_server.repository;

import athlonix.athlonix_server.model.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Paths.get;

public class VersionRepository {

    public static final String VERSION_DIRECTORY = "./desktop_versions";
    public Version getMostRecentVersion() {
        File versionDirectoryPath = new File(VERSION_DIRECTORY);
        if(!versionDirectoryPath.isDirectory()) {
            throw new RuntimeException("version directory does not exist");
        }

        List<String> existingVersions = new ArrayList<>();
        for(String file : Objects.requireNonNull(versionDirectoryPath.list())) {
            int dotIndex = file.lastIndexOf('.');
            if(dotIndex != -1) {
                String fileName = file.substring(0, dotIndex);
                existingVersions.add(fileName);
            }
        }

        existingVersions.sort(String::compareTo);

        return new Version(existingVersions.getLast());
    }
}
