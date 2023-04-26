package BLL.Managers;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ICEOManager;
import DAL.DB.DeviceDAO_DB;
import DAL.DB.ProjectDAO_DB;
import DAL.DB.UserDAO_DB;
import DAL.Interface.IDeviceDAO;
import DAL.Interface.IProjectDAO;
import DAL.Interface.IUserDAO;

import java.io.IOException;
import java.util.List;

public class CEOManager implements ICEOManager {
    private IProjectDAO projectDatabaseAccess;
    private IUserDAO userDatabaseAccess;
    private IDeviceDAO deviceDatabaseAccess;


    public CEOManager() throws IOException {
        userDatabaseAccess = new UserDAO_DB();
        projectDatabaseAccess = new ProjectDAO_DB();
        deviceDatabaseAccess = new DeviceDAO_DB();
    }

    @Override
    public List<Project> getAllProjects() throws Exception {
        return projectDatabaseAccess.getAllProjects();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userDatabaseAccess.getAllUsers();
    }

    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDatabaseAccess.getAllDeviceTypes();
    }
}
