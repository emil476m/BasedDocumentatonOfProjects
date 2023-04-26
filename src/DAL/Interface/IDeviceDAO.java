package DAL.Interface;

import BE.DeviceType;

import java.util.List;

public interface IDeviceDAO {
    List<DeviceType> getAllDeviceTypes() throws Exception;
}
