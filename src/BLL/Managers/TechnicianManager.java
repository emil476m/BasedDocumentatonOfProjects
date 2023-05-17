package BLL.Managers;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ITechnicianManager;
import DAL.DB.DeviceDAO_DB;
import DAL.DB.ProjectDAO_DB;
import DAL.Interface.IDeviceDAO;
import DAL.Interface.IProjectDAO;

import java.io.IOException;
import java.util.List;

public class TechnicianManager implements ITechnicianManager
{
    IProjectDAO projectDAO;
    IDeviceDAO deviceDAO;
    public TechnicianManager() throws IOException {
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
     * Gets a list of all projects that a user is working on
     * @param user
     * @return a list of all projects a user is working on
     * @throws Exception
     */
    @Override
    public List<Project> getMyProjects(User user) throws Exception {
        return projectDAO.getMyProjects(user);
    }

    /**
     * Gets all DeviceTypes from the database.
     * @return a list of all DeviceTypes.
     * @throws Exception
     */
    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDAO.getAllDeviceTypes();
    }
}
