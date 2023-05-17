package BLL.Interfaces;

import BE.DeviceType;
import BE.Project;

import java.util.List;

public interface IProjectManagerManager {

    List<Project> getAllProjects() throws Exception;

    void updateProject(Project project) throws Exception;

    List<DeviceType> getAllDeviceTypes() throws Exception;
}
