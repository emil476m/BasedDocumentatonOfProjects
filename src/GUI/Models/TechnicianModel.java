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

public class TechnicianModel {

    private ObservableList<Project> projectsObservableList;
    private ObservableList<Project> myProjectsObservableList;

    private ObservableList<DeviceType> deviceTypeObservableList;

    private List<DeviceType> allDeviceTypes;
    private List<Project> allProjects;
    private List<Project> allMyProjects;

    ITechnicianManager technicianManager;
    public TechnicianModel() throws IOException {
        technicianManager = new TechnicianManager();
        allDeviceTypes = new ArrayList<>();
        allProjects = new ArrayList<>();
        allMyProjects = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
        myProjectsObservableList = FXCollections.observableArrayList();
        deviceTypeObservableList = FXCollections.observableArrayList();
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = technicianManager.getAllProjects();
        projectsObservableList.clear();
        allProjects.clear();
        projectsObservableList.addAll(projects);
        allProjects.addAll(projects);
    }

    public void getAllMyProjects(User user) throws Exception {
        List<Project> myProjects = technicianManager.getMyProjects(user);
        myProjectsObservableList.clear();
        allMyProjects.clear();
        myProjectsObservableList.addAll(myProjects);
        allMyProjects.addAll(myProjects);
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

    /**
     * Clears the search query.
     */
    public void clearSearch() {
        deviceTypeObservableList.clear();
        deviceTypeObservableList.addAll(allDeviceTypes);
        projectsObservableList.clear();
        projectsObservableList.addAll(allProjects);
        myProjectsObservableList.clear();
        myProjectsObservableList.addAll(allMyProjects);
    }

    /**
     * Searches for MyProjects with given query.
     * @param query The query to search for.
     */
    public void searchMyProject (String query) {
        if (allMyProjects.isEmpty())
            allMyProjects.addAll(myProjectsObservableList);
        else
            myProjectsObservableList.clear();

        for (Project m: allMyProjects)
        {
            boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
            boolean containsName = m.getCostumerName().toLowerCase().contains(query);
            if (containsAddress || containsName)
                myProjectsObservableList.add(m);
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
     * Searches for devices with given query.
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
