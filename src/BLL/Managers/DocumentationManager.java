package BLL.Managers;

import BE.CostumerType;
import BE.Device;
import BE.DeviceType;
import BE.Project;
import BLL.Interfaces.IDocumentationManager;
import DAL.DB.CostumerTypeDAO_DB;
import DAL.DB.DeviceDAO_DB;
import DAL.DB.ProjectDAO_DB;
import DAL.DB.UserDAO_DB;
import DAL.Interface.ICostumerTypeDAO;
import DAL.Interface.IDeviceDAO;
import DAL.Interface.IProjectDAO;
import DAL.Interface.IUserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DocumentationManager implements IDocumentationManager {
    private ICostumerTypeDAO costumerTypeDAO;
    private IProjectDAO projectDAO;
    private IDeviceDAO deviceDAO;
    private IUserDAO userDAO;

    public DocumentationManager() throws IOException {
        costumerTypeDAO = new CostumerTypeDAO_DB();
        projectDAO = new ProjectDAO_DB();
        deviceDAO = new DeviceDAO_DB();
        userDAO = new UserDAO_DB();
    }

    /**
     * Gets a list of all CostumerTypes from the database
     * @return a list of all CostumerTypes
     * @throws Exception
     */
    @Override
    public List<CostumerType> getAllCostumerTypes() throws Exception {
        return costumerTypeDAO.getAllCostumerTypes();
    }

    /**
     * Sends a Project and list of devices to the database to get created.
     * @param project
     * @param devices
     * @return the project with id from the database
     * @throws SQLException
     */
    @Override
    public Project createProject(Project project, List<Device> devices) throws SQLException {
        return projectDAO.createProject(project,devices);
    }

    /**
     * Sends a project to the database to have a boolean updated
     * @param project
     * @throws Exception
     */
    @Override
    public void sendToPMOrTech(Project project) throws Exception {
        projectDAO.updateProject(project);
    }

    /**
     * Gets all
     * @param project
     * @return
     * @throws Exception
     */
    @Override
    public List<Device> getDevicesForProject(Project project) throws Exception {
        return deviceDAO.getAllDevicesForProject(project);
    }

    /**
     *
     * @param project
     * @param newDevices
     * @throws SQLException
     */
    @Override
    public void updateProjectAndDevices(Project project, List<Device> newDevices) throws SQLException {
        projectDAO.UpdateProjectWithDevices(project,newDevices);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDAO.getAllDeviceTypes();
    }

    /**
     *
     * @param deviceType
     * @return
     * @throws Exception
     */
    @Override
    public DeviceType createCustomDeviceType(DeviceType deviceType) throws Exception {
        return deviceDAO.createDeviceType(deviceType);
    }

    /**
     *
     * @param project
     * @return
     * @throws Exception
     */
    @Override
    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception{
        return userDAO.getUsersWorkingOnProject(project);
    }

    /**
     *
     * @param project
     * @return
     * @throws Exception
     */
    @Override
    public boolean lastProjectEditMatch(Project project) throws Exception {
        return projectDAO.lastProjectEditMatch(project);
    }

    /**
     *
     * @param project
     * @return
     * @throws Exception
     */
    @Override
    public Project getProjectFromId(Project project) throws Exception {
        return projectDAO.getProjectFromId(project);
    }

    /**
     *
     * @param devices
     * @throws SQLException
     */
    @Override
    public void deleteDevices(List<Integer> devices) throws SQLException {
        deviceDAO.deleteDevice(devices);
    }
}
