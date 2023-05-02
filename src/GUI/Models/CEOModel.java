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

    public ObservableList getProjectsObservableList() {
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

    public ObservableList<DeviceType> getDeviceTypeObservableList() {
        return deviceTypeObservableList;
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = ceoManager.getAllProjects();
        projectsObservableList.clear();
        allProjects.clear();
        projectsObservableList.addAll(projects);
        allProjects.addAll(projects);
    }

    public void getAllUsers() throws Exception {
        List<User> users = ceoManager.getAllUsers();
        allUsers.clear();
        userObservableList.clear();
        allUsers.addAll(users);
        userObservableList.addAll(users);
    }

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

    public void createUser(User user) throws Exception{
        User newUser = ceoManager.createUser(user);
        userObservableList.add(newUser);
        allUsers.add(newUser);
    }

    public void updateUser(User updatedUser, User oldUser) throws Exception{
        userObservableList.remove(oldUser);
        allUsers.remove(oldUser);
        ceoManager.updateUser(updatedUser);
        userObservableList.add(updatedUser);
        allUsers.add(updatedUser);
    }

    public boolean checkUserName(String userName) throws Exception{
        return ceoManager.checkUserName(userName);
    }

    public void deleteUser(User user) throws Exception {
        userObservableList.remove(user);
        allUsers.remove(user);
        user.setDeleted(true);
        ceoManager.updateUser(user);
    }

    public void updateUsernameOrClass(User updatedUser, User oldUser) throws Exception {
        userObservableList.remove(oldUser);
        allUsers.remove(oldUser);
        ceoManager.updateUser(updatedUser);
        User user = getUserFromId(oldUser.getUserID());
        userObservableList.add(user);
        allUsers.add(user);
    }

    public User getUserFromId(int id) throws Exception {
        return ceoManager.getUserFromId(id);
    }

    public void updateProject(Project updateProject, Project oldProject) throws Exception {
        projectsObservableList.remove(oldProject);
        allProjects.remove(oldProject);
        ceoManager.updateProject(updateProject);
        projectsObservableList.add(updateProject);
        allProjects.add(updateProject);
    }

    public void deleteProject(Project project) throws Exception {
        projectsObservableList.remove(project);
        allProjects.remove(project);
        project.setProjectIsDeleted(true);
        ceoManager.updateProject(project);
    }

    public void deleteDeviceType(DeviceType deviceType) throws Exception {
        deviceTypeObservableList.remove(deviceType);
        allDeviceTypes.remove(deviceType);
        deviceType.setIsDeleted(true);
        ceoManager.updateDeviceType(deviceType);
    }

    public void createDeviceType(DeviceType deviceType) throws Exception {
        DeviceType deviceType1 = ceoManager.createDeviceType(deviceType);
        deviceTypeObservableList.add(deviceType1);
        allDeviceTypes.add(deviceType1);
    }


    public List<User> getUsersWorkingOnProject(Project project) throws Exception{
        List<Integer> allUsersId = ceoManager.getUsersWorkingOnProject(project);

        List<User> allUsersOnProject = new ArrayList<>();

        for (Integer i:allUsersId){
            allUsersOnProject.add(getLocalUserFromId(i));
        }
        return allUsersOnProject;
    }

    public User getLocalUserFromId(int id){
        for (User u: allUsers){
            if (u.getUserID() == id)
                return u;
        }
        return null;
    }

    public void addUsersWorkingOnProject(List<User> usersToBeAdded, Project project) throws Exception {
        ceoManager.addUsersToWorkingOnProject(usersToBeAdded, project);
        userOnCurrentProject.addAll(usersToBeAdded);
    }

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
    public void searchProject (String query) {
        if (allProjects.isEmpty())
            allProjects.addAll(projectsObservableList);
        else
            projectsObservableList.clear();

        for (Project m: allProjects)
        {
            boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
            boolean containsName = m.getCostumerName().toLowerCase().contains(query);
            if (containsAddress || containsName)
                projectsObservableList.add(m);
        }
    }

    /**
     * Searches for users with given query.
     * @param query The query to search for.
     */
    public void searchDeviceTypes (String query) {
        if (allDeviceTypes.isEmpty())
            allDeviceTypes.addAll(deviceTypeObservableList);
        else
            deviceTypeObservableList.clear();

        for (DeviceType d: allDeviceTypes)
        {
            boolean nameContains = d.getType().toLowerCase().contains(query);
            if (nameContains)
                deviceTypeObservableList.add(d);
        }
    }
}
