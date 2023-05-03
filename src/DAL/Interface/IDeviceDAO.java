package DAL.Interface;

import BE.Device;
import BE.DeviceType;
import BE.Project;

import java.util.List;

public interface IDeviceDAO {
    List<DeviceType> getAllDeviceTypes() throws Exception;

    DeviceType createDeviceType(DeviceType deviceType) throws Exception;

    void updateDeviceType(DeviceType deviceType) throws Exception;

    List<Device> getAllDevicesForProject(Project project) throws Exception;

    boolean checkIfDeviceTypeNameIsDuplicate(DeviceType deviceType) throws Exception;
}
