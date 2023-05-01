package GUI.Models;

import BE.CostumerType;
import BE.Device;
import BE.Project;
import BLL.Interfaces.IDocumentationManager;
import BLL.Managers.DocumentationManager;
import javafx.collections.FXCollections;
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
        devices = FXCollections.observableArrayList();
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

    public void sentToProjectManager(Project project) throws Exception {
        documentationManager.sendToPMOrTech(project);
    }

    public void getAllDevicesForProject(Project project) throws Exception {
        List<Device> deviceList = documentationManager.devicesForProject(project);
        devices.clear();
        devices.addAll(deviceList);
    }

    public ObservableList getDevices()
    {
        return devices;
    }

    public void saveproject(Project project, ObservableList devices) throws Exception {
        List<Device> devicesOnProject = documentationManager.devicesForProject(project);
        List<Device> newDevices = devices;
        for (Device d: devicesOnProject)
        {
            if(newDevices.contains(d))
            {
                newDevices.remove(d);
            }
        }
        documentationManager.updateProjectAndDevices(project,newDevices);
    }
}
