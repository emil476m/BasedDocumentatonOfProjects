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
    private List<Project> allProjects;
    public ProjectManagerModel() throws IOException {
        projectManagerManager = new ProjectManagerManager();
        allProjects = new ArrayList<>();
        allProjectsObservablelist = FXCollections.observableArrayList();
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
    }

    public ObservableList<Project> getAllProjectsObservablelist() {
        return allProjectsObservablelist;
    }

    /**
     * Clears the search query.
     */
    public void clearSearch() {
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
}