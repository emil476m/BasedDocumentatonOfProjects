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


    /**
     * Gets a list of all projects from the database.
     * @return a list of all projects
     * @throws Exception
     */
    @Override
    public List<Project> getAllProjects() throws Exception {
        return projectDatabaseAccess.getAllProjects();
    }


    /**
     * Gets a list of all users form the database
     * @return a list of all users
     * @throws Exception
     */
    @Override
    public List<User> getAllUsers() throws Exception {
        return userDatabaseAccess.getAllUsers();
    }

    /**
     * gets a list of all DeviceTypes from the database.
     * @return a list of all DeviceTypes
     * @throws Exception
     */
    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        return deviceDatabaseAccess.getAllDeviceTypes();
    }

    /**
     * sends a user to the database to create them there.
     * @param user
     * @return the user object with the id from the database
     * @throws Exception
     */
    @Override
    public User createUser(User user) throws Exception{
        return userDatabaseAccess.createUser(user);
    }

    /**
     * sends a user object to the database to update a users information
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUser(User user) throws Exception{
        userDatabaseAccess.updateUser(user);
    }

    /**
     * sends a username to the database to see if it exists in the database;
     * @param userName
     * @return the result from the database
     * @throws Exception
     */
    @Override
    public boolean checkUserName(String userName) throws Exception{
        return userDatabaseAccess.checkUserName(userName);
    }

    /**
     * gets a user by id from the database
     * @param userId
     * @return a user that matches the given id.
     * @throws Exception
     */
    @Override
    public User getUserFromId(int userId) throws Exception{
        return userDatabaseAccess.getUserFromId(userId);
    }

    /**
     * Sends a project to the database to be updated.
     * @param project
     * @throws Exception
     */
    @Override
    public void updateProject(Project project) throws Exception{
        projectDatabaseAccess.updateProject(project);
    }

    /**
     * Sends a DeviceType to the database to be updated.
     * @param deviceType
     * @throws Exception
     */
    @Override
    public void updateDeviceType(DeviceType deviceType) throws Exception {
        deviceDatabaseAccess.updateDeviceType(deviceType);
    }

    /**
     * Sends a DeviceType to the database to be created
     * @param deviceType
     * @return the DeviceType with an id.
     * @throws Exception
     */
    @Override
    public DeviceType createDeviceType(DeviceType deviceType) throws Exception {
        return deviceDatabaseAccess.createDeviceType(deviceType);
    }

    /**
     * Gets a list of all users working on a project
     * @param project
     * @return a list of all users working on a project
     * @throws Exception
     */
    @Override
    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception{
        return userDatabaseAccess.getUsersWorkingOnProject(project);
    }

    /**
     * Sends a list of users to the database so they can be added to working on the specific project.
     * @param userListToBeAdded
     * @param project
     * @throws Exception
     */
    @Override
    public void addUsersToWorkingOnProject(List<User> userListToBeAdded, Project project) throws Exception{
        userDatabaseAccess.addUsersToWorkingOnProject(userListToBeAdded, project);
    }

    /**
     * Sends a user to the database to delete them from working on the specific project
     * @param user
     * @param project
     * @throws Exception
     */
    @Override
    public void deleteFromWorkingOnProject(User user, Project project) throws Exception{
        userDatabaseAccess.deleteFromWorkingOnProject(user, project);
    }

    /**
     * Sends a DeviceType to the database to check if it already exists
     * @param deviceType
     * @return result from database
     * @throws Exception
     */
    @Override
    public boolean checkIfDeviceTypeNameIsDuplicate(DeviceType deviceType) throws Exception {
        return deviceDatabaseAccess.checkIfDeviceTypeNameIsDuplicate(deviceType);
    }
}