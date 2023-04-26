package BE;

public class Device {
    private int deviceId;
    private int deviceTypeId;
    private String deviceUserName;
    private String devicePassWord;

    public Device(int deviceId, int deviceTypeId, String deviceUserName, String devicePassWord) {
        this.deviceId = deviceId;
        this.deviceTypeId = deviceTypeId;
        this.deviceUserName = deviceUserName;
        this.devicePassWord = devicePassWord;
    }

    public Device(int deviceTypeId, String deviceUserName, String devicePassWord) {
        this.deviceTypeId = deviceTypeId;
        this.deviceUserName = deviceUserName;
        this.devicePassWord = devicePassWord;
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
}
