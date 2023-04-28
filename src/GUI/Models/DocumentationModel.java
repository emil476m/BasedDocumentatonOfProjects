package GUI.Models;

import BE.CostumerType;
import BE.Device;
import BE.Project;
import BLL.Interfaces.IDocumentationManager;
import BLL.Managers.DocumentationManager;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentationModel {
    private IDocumentationManager documentationManager;
    ObservableList<Device> devices;
    ObservableList<File> images;

    List<CostumerType> costumerTypes;


    public DocumentationModel() throws IOException {
        documentationManager = new DocumentationManager();
        costumerTypes = new ArrayList<>();
    }

    public void getAllCostumerTypes() throws Exception {
        costumerTypes = documentationManager.getAllCostumerTypes();
    }

    public List<CostumerType> getCostumerTypes()
    {
        return costumerTypes;
    }

    public void saveNewProject(Project project, List<Device> devices) throws SQLException {
        documentationManager.createProject(project, devices);
    }
}
