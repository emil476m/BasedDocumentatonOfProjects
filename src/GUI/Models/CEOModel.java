package GUI.Models;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ICEOManager;
import BLL.Managers.CEOManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class CEOModel {
    private ICEOManager ceoManager;
    private ObservableList projectsObservableList;
    private ObservableList<User> userObservableList;

    private ObservableList<DeviceType> deviceTypeObservableList;
    public CEOModel() throws IOException {
        ceoManager = new CEOManager();
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
}
