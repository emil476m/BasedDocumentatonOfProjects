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

    @Override
    public User createUser(User user) throws Exception{
        return userDatabaseAccess.createUser(user);
    }
    @Override
    public void updateUser(User user) throws Exception{
        userDatabaseAccess.updateUser(user);
    }
    @Override
    public boolean checkUserName(String userName) throws Exception{
        return userDatabaseAccess.checkUserName(userName);
    }

    @Override
    public User getUserFromId(int userId) throws Exception{
        return userDatabaseAccess.getUserFromId(userId);
    }

    @Override
    public void updateProject(Project project) throws Exception{
        projectDatabaseAccess.updateProject(project);
    }

    @Override
    public void updateDeviceType(DeviceType deviceType) throws Exception {
        deviceDatabaseAccess.updateDeviceType(deviceType);
    }

    @Override
    public DeviceType createDeviceType(DeviceType deviceType) throws Exception {
        return deviceDatabaseAccess.createDeviceType(deviceType);
    }
}
