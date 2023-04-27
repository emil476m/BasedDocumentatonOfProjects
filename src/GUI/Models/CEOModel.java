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
    private ObservableList projectsObservableList;
    private ObservableList<User> userObservableList;

    private ObservableList<DeviceType> deviceTypeObservableList;
    public CEOModel() throws IOException {
        ceoManager = new CEOManager();
        allUsers = new ArrayList<>();
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
        projectsObservableList.addAll(projects);
    }

    public void getAllUsers() throws Exception {
        List<User> users = ceoManager.getAllUsers();
        userObservableList.addAll(users);
    }

    public void getAllDeviceTypes() throws Exception {
        List<DeviceType> deviceTypes = ceoManager.getAllDeviceTypes();
        deviceTypeObservableList.addAll(deviceTypes);
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
}
