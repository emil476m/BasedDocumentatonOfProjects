package GUI.Models;

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
    public CEOModel() throws IOException {
        ceoManager = new CEOManager();
        projectsObservableList = FXCollections.observableArrayList();
        userObservableList = FXCollections.observableArrayList();

    }

    public ObservableList getProjectsObservableList() {
        return projectsObservableList;
    }

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

    public void getAllProjects() throws Exception {
        List<Project> projects = ceoManager.getAllProjects();
        projectsObservableList.addAll(projects);
    }

    public void getAllUsers() throws Exception {
        List<User> users = ceoManager.getAllUsers();
        userObservableList.addAll(users);
    }
}
