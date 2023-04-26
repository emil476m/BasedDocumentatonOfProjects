package BE;

public class DeviceType {
    private int id;
    private String type;

    public DeviceType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public DeviceType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
