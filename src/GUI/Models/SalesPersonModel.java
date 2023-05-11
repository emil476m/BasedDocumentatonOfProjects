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
    private List<Project> allProjects;

    ITechnicianManager technicianManager;
    public SalesPersonModel() throws IOException {
        technicianManager = new TechnicianManager();
        allProjects = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = technicianManager.getAllProjects();
        projectsObservableList.addAll(projects);
    }

    public ObservableList<Project> getProjectsObservableList()
    {
        return projectsObservableList;
    }

    /**
     * Clears the search query.
     */
    public void clearSearch() {
        projectsObservableList.clear();
        projectsObservableList.addAll(allProjects);
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
}