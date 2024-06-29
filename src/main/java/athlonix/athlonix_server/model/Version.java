package athlonix.athlonix_server.model;

public class Version {
    private String name;

    public Version(String last) {
        this.name = last;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
