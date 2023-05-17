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

    /**
     * Gets a list of all projects from the database
     * @return a list of all projects
     * @throws Exception
     */
    @Override
    public List<Project> getAllProjects() throws Exception {
        return projectDAO.getAllProjects();
    }

    /**
     * Sends a porject to the database to update it.
     * @param project
     * @throws Exception
     */
    @Override
    public void updateProject(Project project) throws Exception{
        projectDAO.updateProject(project);
    }

    /**
     * Gets a list of all DeviceTypes from the database
     * @return a list of all DeviceTypes
     * @throws Exception
     */
    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDAO.getAllDeviceTypes();
    }
}
