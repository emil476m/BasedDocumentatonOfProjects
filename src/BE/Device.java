package BE;

public class Device {
    private int deviceId;
    private int deviceTypeId;
    private String deviceUserName;
    private String devicePassWord;
    private String deviceTypeString;

    public Device(int deviceId, int deviceTypeId, String deviceUserName, String devicePassWord, String deviceTypeString) {
        this.deviceId = deviceId;
        this.deviceTypeId = deviceTypeId;
        this.deviceUserName = deviceUserName;
        this.devicePassWord = devicePassWord;
        this.deviceTypeString = deviceTypeString;
    }

    public Device(int deviceTypeId, String deviceUserName, String devicePassWord, String deviceTypeString) {
        this.deviceTypeId = deviceTypeId;
        this.deviceUserName = deviceUserName;
        this.devicePassWord = devicePassWord;
        this.deviceTypeString = deviceTypeString;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceUserName() {
        return deviceUserName;
    }

    public void setDeviceUserName(String deviceUserName) {
        this.deviceUserName = deviceUserName;
    }

    public String getDevicePassWord() {
        return devicePassWord;
    }

    public void setDevicePassWord(String devicePassWord) {
        this.devicePassWord = devicePassWord;
    }

    public String getDeviceTypeString() {return deviceTypeString;}

    @Override
    public String toString() {
        return deviceId + "\t" + "Username: " + deviceUserName + "\n\t" + "Password: " + devicePassWord + "\n\t" + "Type: " + deviceTypeString;
    }
}
