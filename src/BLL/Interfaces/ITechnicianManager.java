package BLL.Interfaces;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;

import java.util.List;

public interface ITechnicianManager {

    List<Project> getAllProjects() throws Exception;

    List<Project> getMyProjects(User user) throws Exception;

    List<DeviceType> getAllDeviceTypes() throws Exception;
}
