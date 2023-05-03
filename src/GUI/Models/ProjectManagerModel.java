package GUI.Models;

import BE.DeviceType;
import BE.Project;
import BLL.Interfaces.IProjectManagerManager;
import BLL.Managers.ProjectManagerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManagerModel {
    IProjectManagerManager projectManagerManager;
    ObservableList<Project> allProjectsObservablelist;
    ObservableList<DeviceType> deviceTypesObservablelist;

    private List<DeviceType> allDeviceTypes;
    private List<Project> allProjects;
    public ProjectManagerModel() throws IOException {
        projectManagerManager = new ProjectManagerManager();
        allDeviceTypes = new ArrayList<>();
        allProjects = new ArrayList<>();
        allProjectsObservablelist = FXCollections.observableArrayList();
        deviceTypesObservablelist = FXCollections.observableArrayList();
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = projectManagerManager.getAllProjects();
        allProjectsObservablelist.clear();
        allProjects.clear();
        allProjectsObservablelist.addAll(projects);
        allProjects.addAll(projects);
    }

    public void getAllDeviceTypes() throws Exception {
        List<DeviceType> deviceTypes = projectManagerManager.getAllDeviceTypes();
        for (DeviceType d: deviceTypes){
            if (d.getType().equals("Custom")) {
                deviceTypes.remove(d);
                break;
            }
        }
        allDeviceTypes.clear();
        deviceTypesObservablelist.clear();
        deviceTypesObservablelist.addAll(deviceTypes);
        allDeviceTypes.addAll(deviceTypes);
    }

    public ObservableList<Project> getAllProjectsObservablelist() {
        return allProjectsObservablelist;
    }

    public ObservableList<DeviceType> getDeviceTypesObservablelist() {
        return deviceTypesObservablelist;
    }

    /**
     * Clears the search query.
     */
    public void clearSearch() {
        deviceTypesObservablelist.clear();
        deviceTypesObservablelist.addAll(allDeviceTypes);
        allProjectsObservablelist.clear();
        allProjectsObservablelist.addAll(allProjects);
    }


    /**
     * Searches for Project addresses with given query.
     * @param query The query to search for.
     */
    public void searchProject (String query) {
        if (allProjects.isEmpty())
            allProjects.addAll(allProjectsObservablelist);
        else
            allProjectsObservablelist.clear();

        for (Project m: allProjects)
        {
            boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
            boolean containsName = m.getCostumerName().toLowerCase().contains(query);
            if (containsAddress || containsName)
                allProjectsObservablelist.add(m);
        }
    }

    /**
     * Searches for devices with given query.
     * @param query The query to search for.
     */
    public void searchDeviceTypes (String query) {
        if (allDeviceTypes.isEmpty())
            allDeviceTypes.addAll(deviceTypesObservablelist);
        else
            deviceTypesObservablelist.clear();

        for (DeviceType d: allDeviceTypes)
        {
            boolean nameContains = d.getType().toLowerCase().contains(query);
            if (nameContains)
                deviceTypesObservablelist.add(d);
        }
    }
}
