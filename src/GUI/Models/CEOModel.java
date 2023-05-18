package GUI.Models;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ICEOManager;
import BLL.Managers.CEOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CEOModel {
    private ICEOManager ceoManager;

    private List<User> allUsers;
    private List<Project> allProjects;
    private List<DeviceType> allDeviceTypes;
    private ObservableList<Project> projectsObservableList;
    private ObservableList<User> userObservableList;
    private ObservableList<User> userOnCurrentProject;

    private ObservableList<DeviceType> deviceTypeObservableList;
    public CEOModel() throws IOException {
        ceoManager = new CEOManager();
        allUsers = new ArrayList<>();
        allProjects = new ArrayList<>();
        allDeviceTypes = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
        userObservableList = FXCollections.observableArrayList();
        deviceTypeObservableList = FXCollections.observableArrayList();
        userOnCurrentProject = FXCollections.observableArrayList();
    }

    public ObservableList<Project> getProjectsObservableList() {
        return projectsObservableList;
    }

    public ObservableList<User> getUserOnCurrentProject() {
        return userOnCurrentProject;
    }

    public List<User> getAllUsersList(){
        return allUsers;
    }

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

    /**
     * Gets all projects.
     * @throws Exception
     */
    public void getAllProjects() throws Exception {
        List<Project> projects = ceoManager.getAllProjects();
        projectsObservableList.clear();
        allProjects.clear();
        projectsObservableList.addAll(projects);
        allProjects.addAll(projects);
    }

    /**
     * Gets all users
     * @throws Exception
     */
    public void getAllUsers() throws Exception {
        List<User> users = ceoManager.getAllUsers();
        allUsers.clear();
        userObservableList.clear();
        allUsers.addAll(users);
        userObservableList.addAll(users);
    }

    /**
     * Gets all DeviceTypes
     * @throws Exception
     */
    public void getAllDeviceTypes() throws Exception {
        List<DeviceType> deviceTypes = ceoManager.getAllDeviceTypes();
        for (DeviceType d: deviceTypes){
            if (d.getType().equals("Custom")) {
                deviceTypes.remove(d);
                break;
            }
        }
        allDeviceTypes.clear();
        deviceTypeObservableList.clear();
        deviceTypeObservableList.addAll(deviceTypes);
        allDeviceTypes.addAll(deviceTypes);
    }

    /**
     * Sends a user to the CEOManager and adds said user to the userObservableList and the allUsers
     * @param user
     * @throws Exception
     */
    public void createUser(User user) throws Exception{
        User newUser = ceoManager.createUser(user);
        userObservableList.add(newUser);
        allUsers.add(newUser);
    }

    /**
     * Sends a user to the CEOManager and updates the lists where the user is found.
     * @param updatedUser
     * @param oldUser
     * @throws Exception
     */
    public void updateUser(User updatedUser, User oldUser) throws Exception{
        userObservableList.remove(oldUser);
        allUsers.remove(oldUser);
        ceoManager.updateUser(updatedUser);
        userObservableList.add(updatedUser);
        allUsers.add(updatedUser);
    }

    /**
     * Sends a username to the CEOManager.
     * @param userName
     * @return the result from the CEOManager
     * @throws Exception
     */
    public boolean checkUserName(String userName) throws Exception{
        return ceoManager.checkUserName(userName);
    }

    /**
     * Sends a user to the CEOManager and removes said user from all the lists it exists in.
     * @param user
     * @throws Exception
     */
    public void deleteUser(User user) throws Exception {
        userObservableList.remove(user);
        allUsers.remove(user);
        user.setDeleted(true);
        ceoManager.updateUser(user);
    }

    /**
     * Sends a user to the CEOManager and deletes the old one from all the lists it exists in
     * @param updatedUser
     * @param oldUser
     * @throws Exception
     */
    public void updateUsernameOrClass(User updatedUser, User oldUser) throws Exception {
        userObservableList.remove(oldUser);
        allUsers.remove(oldUser);
        ceoManager.updateUser(updatedUser);
        User user = getUserFromId(oldUser.getUserID());
        userObservableList.add(user);
        allUsers.add(user);
    }

    /**
     * Gets a user from their id.
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserFromId(int id) throws Exception {
        return ceoManager.getUserFromId(id);
    }

    /**
     * Sends a Project to the CEOManager and deletes the project from the list
     * @param updateProject
     * @param oldProject
     * @throws Exception
     */
    public void updateProject(Project updateProject, Project oldProject) throws Exception {
        projectsObservableList.remove(oldProject);
        allProjects.remove(oldProject);
        ceoManager.updateProject(updateProject);
        projectsObservableList.add(updateProject);
        allProjects.add(updateProject);
    }

    /**
     * Sends a project to the CEOManager and removes it from the list.
     * @param project
     * @throws Exception
     */
    public void deleteProject(Project project) throws Exception {
        projectsObservableList.remove(project);
        allProjects.remove(project);
        project.setProjectIsDeleted(true);
        ceoManager.updateProject(project);
    }

    /**
     * Sends a DeviceType to the CEOManager and adds it to the device list
     * @param deviceType
     * @throws Exception
     */
    public void createDeviceType(DeviceType deviceType) throws Exception {
        DeviceType deviceType1 = ceoManager.createDeviceType(deviceType);
        deviceTypeObservableList.add(deviceType1);
        allDeviceTypes.add(deviceType1);
    }

    /**
     * Gets all users that are working on a specific project
     * @param project
     * @return
     * @throws Exception
     */
    public List<User> getUsersWorkingOnProject(Project project) throws Exception{
        List<Integer> allUsersId = ceoManager.getUsersWorkingOnProject(project);

        List<User> allUsersOnProject = new ArrayList<>();

        for (Integer i:allUsersId){
            allUsersOnProject.add(getLocalUserFromId(i));
        }
        return allUsersOnProject;
    }

    /**
     * gets a user from the allUsers list by looking for their id
     * @param id
     * @return
     */
    public User getLocalUserFromId(int id){
        for (User u: allUsers){
            if (u.getUserID() == id)
                return u;
        }
        return null;
    }

    /**
     * Sends a list of users to the CEOManager and adds them to the userOnCurrentProject
     * @param usersToBeAdded
     * @param project
     * @throws Exception
     */
    public void addUsersWorkingOnProject(List<User> usersToBeAdded, Project project) throws Exception {
        ceoManager.addUsersToWorkingOnProject(usersToBeAdded, project);
        userOnCurrentProject.addAll(usersToBeAdded);
    }

    /**
     * Sends a list of users to the CEOManager (this only gets called if a CEO or ProjectManager creates a new project)
     * @param usersToBeAdded
     * @param project
     * @throws Exception
     */
    public void addUsersWorkingOnNewProject(List<User> usersToBeAdded, Project project) throws Exception {
        ceoManager.addUsersToWorkingOnProject(usersToBeAdded, project);
    }

    /**
     * Sends a user and a project to the CEOManager and removes the user from the userOnCurrentProject
     * @param user
     * @param project
     * @throws Exception
     */
    public void deleteFromWorkingOnProject(User user, Project project) throws Exception {
        ceoManager.deleteFromWorkingOnProject(user, project);
        userOnCurrentProject.remove(user);
    }

    /**
     * Clears the search query of Users.
     */
    public void clearSearch() {
        userObservableList.clear();
        userObservableList.addAll(allUsers);
        deviceTypeObservableList.clear();
        deviceTypeObservableList.addAll(allDeviceTypes);
        projectsObservableList.clear();
        projectsObservableList.addAll(allProjects);
    }

    /**
     * Searches for users with given query.
     * @param query The query to search for.
     */
    public void searchUsers (String query) {
        if (allUsers.isEmpty())
            allUsers.addAll(userObservableList);
        else
            userObservableList.clear();

        for (User m: allUsers)
        {
            boolean nameContains = m.getName().toLowerCase().contains(query);
            if (nameContains)
                userObservableList.add(m);
        }
    }

    /**
     * Searches for Project addresses with given query.
     * @param query The query to search for.
     */
    public void searchProject (String query, boolean name, boolean address, boolean date) {
        if (allProjects.isEmpty())
            allProjects.addAll(projectsObservableList);
        else
            projectsObservableList.clear();

        for (Project m: allProjects)
        {
            if (name && address && date){
                boolean containsName = m.getCostumerName().toLowerCase().contains(query);
                boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
                boolean containsDate = (m.getProjectDate()).toString().contains(query);

                if (containsName || containsAddress || containsDate){
                    projectsObservableList.add(m);
                }

            }
            else if (name){
                boolean containsName = m.getCostumerName().toLowerCase().contains(query);
                if (containsName)
                    projectsObservableList.add(m);
            }

            else if (address){
                boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
                if (containsAddress)
                    projectsObservableList.add(m);
            }

            else if (date){
                boolean containsDate = (m.getProjectDate()).toString().contains(query);
                if (containsDate)
                    projectsObservableList.add(m);
            }
        }
    }

    /**
     * Sends a DeviceType to the CEOManager
     * @param deviceType
     * @return the result from the CEOManager
     * @throws Exception
     */
    public boolean checkIfDeviceTypeNameIsDuplicate(DeviceType deviceType) throws Exception {
        return ceoManager.checkIfDeviceTypeNameIsDuplicate(deviceType);
    }
}
