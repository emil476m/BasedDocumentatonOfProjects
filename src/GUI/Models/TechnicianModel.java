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
    private List<Project> allProjects;
    private List<Project> allMyProjects;

    ITechnicianManager technicianManager;
    public TechnicianModel() throws IOException {
        technicianManager = new TechnicianManager();
        allProjects = new ArrayList<>();
        allMyProjects = new ArrayList<>();
        projectsObservableList = FXCollections.observableArrayList();
        myProjectsObservableList = FXCollections.observableArrayList();
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

    /**
     * Clears the search query.
     */
    public void clearSearch() {
        projectsObservableList.clear();
        projectsObservableList.addAll(allProjects);
        myProjectsObservableList.clear();
        myProjectsObservableList.addAll(allMyProjects);
    }

    /**
     * Searches for MyProjects with given query.
     * @param query The query to search for.
     */
    public void searchMyProject (String query, boolean name, boolean address, boolean date) {
        if (allMyProjects.isEmpty())
            allMyProjects.addAll(myProjectsObservableList);
        else
            myProjectsObservableList.clear();

        for (Project m: allMyProjects)
        {
            if (name && address && date){
                boolean containsName = m.getCostumerName().toLowerCase().contains(query);
                boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
                boolean containsDate = (m.getProjectDate()).toString().contains(query);

                if (containsName || containsAddress || containsDate){
                    myProjectsObservableList.add(m);
                }

            }
            else if (name){
                boolean containsName = m.getCostumerName().toLowerCase().contains(query);
                if (containsName)
                    myProjectsObservableList.add(m);
            }

            else if (address){
                boolean containsAddress = (m.getAddress()+m.getZipCode()).toLowerCase().contains(query);
                if (containsAddress)
                    myProjectsObservableList.add(m);
            }

            else if (date){
                boolean containsDate = (m.getProjectDate()).toString().contains(query);
                if (containsDate)
                    projectsObservableList.add(m);
            }
        }
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
