package GUI.Models;

import BE.CostumerType;
import BE.Device;
import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import BLL.Interfaces.IDocumentationManager;
import BLL.Managers.DocumentationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocumentationModel {
    private IDocumentationManager documentationManager;
    ObservableList<Device> devices;
    ObservableList<File> images;


    List<CostumerType> costumerTypes;
    List<DeviceType> deviceTypes;


    public DocumentationModel() throws IOException {
        documentationManager = new DocumentationManager();
        costumerTypes = new ArrayList<>();
        devices = FXCollections.observableArrayList();
        deviceTypes = new ArrayList<>();
    }

    public void getAllCostumerTypes() throws Exception {
        costumerTypes = documentationManager.getAllCostumerTypes();
    }

    public List<CostumerType> getCostumerTypes()
    {
        return costumerTypes;
    }

    public Project saveNewProject(Project project, List<Device> devices) throws SQLException {
       return documentationManager.createProject(project, devices);
    }

    public void sentToProjectManager(Project project) throws Exception {
        documentationManager.sendToPMOrTech(project);
    }

    public void getAllDevicesForProject(Project project) throws Exception {
        devices.clear();
        List<Device> deviceList = documentationManager.devicesForProject(project);
        devices.addAll(deviceList);
    }

    public ObservableList getDevices()
    {
        return devices;
    }

    public void saveproject(Project project, ObservableList<Device> devices) throws Exception {
        List<Device> devicesOnProject = documentationManager.devicesForProject(project);
        List<Device> newDevices = new ArrayList<>();
        for (Device dev: devices)
        {
            newDevices.add(dev);
        }
        for (Device d: devicesOnProject) {
            Iterator ndi = newDevices.iterator();
            while (ndi.hasNext())
            {
                Device nd = (Device) ndi.next();
                if(d.getDeviceId() == nd.getDeviceId())
                {
                    ndi.remove();
                }
            }
        }
        documentationManager.updateProjectAndDevices(project,newDevices);
    }


    public boolean lastProjectEditMatch(Project project) throws Exception {
        return documentationManager.lastProjectEditMatch(project);
    }


    public Project getProjectFromId(Project project) throws Exception {
        return documentationManager.getProjectFromId(project);
    }

    public void getAllDeviceFromDB() throws Exception {
        deviceTypes = documentationManager.getAllDeviceTypes();
    }

    public List<DeviceType> getDeviceTypes() {return deviceTypes;}

    public void addDeviceToList(Device device, DeviceType deviceType) throws Exception {
        for (DeviceType dt: deviceTypes)
        {
            if(dt.getType().equals(deviceType.getType()))
            {
                documentationManager.createCustomDeviceType(deviceType);
            }
        }

        devices.add(device);
    }

    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception{
        List<Integer> allUsersId = documentationManager.getUsersWorkingOnProject(project);
        return allUsersId;
    }

}
