package GUI.Models;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ITechnicianManager;
import BLL.Managers.TechnicianManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesPersonModel {
    private ObservableList<Project> projectsObservableList;
    private ObservableList<Project> myProjectsObservableList;

    private ObservableList<DeviceType> deviceTypeObservableList;

    private List<DeviceType> allDeviceTypes;

    ITechnicianManager technicianManager;
    public SalesPersonModel() throws IOException {
        technicianManager = new TechnicianManager();
        allDeviceTypes = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
        myProjectsObservableList = FXCollections.observableArrayList();
        deviceTypeObservableList = FXCollections.observableArrayList();
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = technicianManager.getAllProjects();
        projectsObservableList.addAll(projects);
    }

    public void getAllMyProjects(User user) throws Exception {
        List<Project> myProjects = technicianManager.getMyProjects(user);
        myProjectsObservableList.addAll(myProjects);
    }

    public ObservableList<Project> getProjectsObservableList()
    {
        return projectsObservableList;
    }

    public ObservableList<Project> getMyProjectsObservableList()
    {
        return myProjectsObservableList;
    }

    public void getAllDeviceTypes() throws Exception {
        List<DeviceType> deviceTypes = technicianManager.getAllDeviceTypes();
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

    public ObservableList getDeviceTypeObservableList() {
        return deviceTypeObservableList;
    }
}
