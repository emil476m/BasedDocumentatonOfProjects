package BLL.Managers;

import BE.DeviceType;
import BE.Project;
import BLL.Interfaces.IProjectManagerManager;
import DAL.DB.DeviceDAO_DB;
import DAL.DB.ProjectDAO_DB;
import DAL.Interface.IDeviceDAO;
import DAL.Interface.IProjectDAO;

import java.io.IOException;
import java.util.List;

public class ProjectManagerManager implements IProjectManagerManager {
    IProjectDAO projectDAO;
    IDeviceDAO deviceDAO;

    public ProjectManagerManager() throws IOException {
        projectDAO = new ProjectDAO_DB();
        deviceDAO = new DeviceDAO_DB();
    }
    @Override
    public List<Project> getAllProjects() throws Exception {
        return projectDAO.getAllProjects();
    }

    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDAO.getAllDeviceTypes();
    }
}
