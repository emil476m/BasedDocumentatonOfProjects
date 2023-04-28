package BE;

public class DeviceType {
    private int id;
    private String type;

    private boolean isDeleted;

    public DeviceType(int id, String type, boolean isDeleted) {
        this.id = id;
        this.type = type;
        this.isDeleted = isDeleted;
    }

    public DeviceType(String type, boolean isDeleted) {
        this.type = type;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
