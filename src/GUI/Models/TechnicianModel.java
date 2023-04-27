package GUI.Models;

import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.ITechnicianManager;
import BLL.Managers.TechnicianManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class TechnicianModel {

    private ObservableList<Project> projectsObservableList;
    private ObservableList<Project> myProjectsObservableList;

    ITechnicianManager technicianManager;
    public TechnicianModel() throws IOException {
        technicianManager = new TechnicianManager();
        projectsObservableList = FXCollections.observableArrayList();
        myProjectsObservableList = FXCollections.observableArrayList();
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
}
