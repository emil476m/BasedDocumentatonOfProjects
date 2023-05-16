package GUI.Models;

import BE.CostumerType;
import BE.Device;
import BE.DeviceType;
import BE.Project;
import BLL.Interfaces.IDocumentationManager;
import BLL.Interfaces.IDropBoxAPIManager;
import BLL.Managers.DocumentationManager;
import GUI.Util.PDFGenerator;
import BLL.Managers.DropBoxAPIManager;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocumentationModel {
    private IDocumentationManager documentationManager;
    private IDropBoxAPIManager dropBoxAPIManager;
    ObservableList<Device> devicesObservableList;
    ObservableList<File> imagesObservableList;


    List<CostumerType> costumerTypes;
    List<DeviceType> deviceTypes;

    List<File> imagesToBeSaved;

    File pdf = new File("PdfFiles//project.pdf");


    public DocumentationModel() throws IOException {
        documentationManager = new DocumentationManager();
        dropBoxAPIManager = new DropBoxAPIManager();
        costumerTypes = new ArrayList<>();
        devicesObservableList = FXCollections.observableArrayList();
        deviceTypes = new ArrayList<>();
        imagesObservableList = FXCollections.observableArrayList();
        imagesToBeSaved = new ArrayList<>();
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
        devicesObservableList.clear();
        List<Device> deviceList = documentationManager.devicesForProject(project);
        devicesObservableList.addAll(deviceList);
    }

    public ObservableList getDevicesObservableList()
    {
        return devicesObservableList;
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

        devicesObservableList.add(device);
    }


    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception{
        List<Integer> allUsersId = documentationManager.getUsersWorkingOnProject(project);
        return allUsersId;
    }

    public ObservableList<File> getImagesObservableList()
    {
        return imagesObservableList;
    }

    /**
     * Creates a pdf file for a project
     * @param project
     * @param devices
     * @param files
     * @throws IOException
     */
    public void createPdf(Project project, ObservableList<Device> devices, ObservableList<File> files) throws IOException {
        PDFGenerator pdfGenerator = new PDFGenerator(project,devices,files);
        pdfGenerator.createDocument();
    }

    public Path getPdf() {return pdf.toPath();}
    public void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException {
        System.out.println("FilePath: " + dropBoxFilePath);
        dropBoxAPIManager.deleteFilesFromDropBox(dropBoxFilePath);
    }

    public void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException {
        dropBoxAPIManager.uploadFilesFromDropBox(localFilePath, dropBoxFilePath);
    }

    public void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException {
        dropBoxAPIManager.downloadFilesFromDropBox(dropBoxFilePath, fileName);
    }

    public void downloadProjectFilesFromDropBox(int projectId) throws DbxException {
        getImagesObservableList().clear();
        for (Metadata m: readFilesFromDropBox(projectId)){
            downloadFilesFromDropBox(m.getPathLower(), m.getName());
            File file = new File("DownloadedDropBoxFiles//" + m.getName());
            getImagesObservableList().add(file);
        }
    }

    public List<Metadata> readFilesFromDropBox(int projectId) throws DbxException {
        return dropBoxAPIManager.readFilesFromDropBox("/"+projectId);
    }

    public List<Metadata> readAllFilesFromDropBox() throws DbxException {
        return dropBoxAPIManager.readAllFilesFromDropBox();
    }

    public void deleteLocalFiles(List<String> localFilePaths) throws Exception {
        dropBoxAPIManager.deleteLocalFiles(localFilePaths);
    }

    public File createLocalFile(File file) throws Exception {
        return dropBoxAPIManager.createLocalFile(file);
    }

    public List<File> getImagesToBeSaved() {
        return imagesToBeSaved;
    }
}
