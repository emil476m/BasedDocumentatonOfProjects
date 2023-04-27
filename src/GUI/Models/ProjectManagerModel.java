package GUI.Models;

import BE.DeviceType;
import BE.Project;
import BLL.Interfaces.IProjectManagerManager;
import BLL.Managers.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ProjectManagerModel {
    IProjectManagerManager projectManagerManager;
    ObservableList<Project> allProjectsObservablelist;
    ObservableList<DeviceType> deviceTypesObservablelist;
    public ProjectManagerModel() throws IOException {
        projectManagerManager = new ProjectManagerManager();
        allProjectsObservablelist = FXCollections.observableArrayList();
        deviceTypesObservablelist = FXCollections.observableArrayList();
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = projectManagerManager.getAllProjects();
        allProjectsObservablelist.addAll(projects);
    }

    public void getAllDeviceTypes() throws Exception {
        List<DeviceType> deviceTypes = projectManagerManager.getAllDeviceTypes();
        deviceTypesObservablelist.addAll(deviceTypes);
    }

    public ObservableList<Project> getAllProjectsObservablelist() {
        return allProjectsObservablelist;
    }

    public ObservableList<DeviceType> getDeviceTypesObservablelist() {
        return deviceTypesObservablelist;
    }
}
