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

    private ObservableList<DeviceType> deviceTypeObservableList;
    public CEOModel() throws IOException {
        ceoManager = new CEOManager();
        allUsers = new ArrayList<>();
        allProjects = new ArrayList<>();
        allDeviceTypes = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
        userObservableList = FXCollections.observableArrayList();
        deviceTypeObservableList = FXCollections.observableArrayList();

    }

    public ObservableList getProjectsObservableList() {
        return projectsObservableList;
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
}
