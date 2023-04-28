package DAL.Interface;

import BE.DeviceType;

import java.util.List;

public interface IDeviceDAO {
    List<DeviceType> getAllDeviceTypes() throws Exception;

    DeviceType createDeviceType(DeviceType deviceType) throws Exception;

    void updateDeviceType(DeviceType deviceType) throws Exception;
}
