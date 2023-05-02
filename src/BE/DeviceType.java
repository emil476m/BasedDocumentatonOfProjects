package BE;

public class DeviceType {
    private int id;
    private String type;

    private boolean isCustom;

    private boolean isDeleted;

    public DeviceType(int id, String type, boolean isDeleted, boolean isCustom) {
        this.id = id;
        this.type = type;
        this.isDeleted = isDeleted;
        this.isCustom = isCustom;
    }

    public DeviceType(String type, boolean isDeleted, boolean isCustom) {
        this.type = type;
        this.isCustom = isCustom;
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

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean custom) {
        isCustom = custom;
    }
}
