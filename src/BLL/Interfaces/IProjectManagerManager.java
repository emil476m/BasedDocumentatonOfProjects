package BLL.Interfaces;

import BE.DeviceType;
import BE.Project;

import java.util.List;

public interface IProjectManagerManager {

    List<Project> getAllProjects() throws Exception;

    List<DeviceType> getAllDeviceTypes() throws Exception;
}
