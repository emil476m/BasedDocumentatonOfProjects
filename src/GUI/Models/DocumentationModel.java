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

    /**
     * Gets all CostumerTypes.
     * @throws Exception
     */
    public void getAllCostumerTypes() throws Exception {
        costumerTypes = documentationManager.getAllCostumerTypes();
    }

    public List<CostumerType> getCostumerTypes() {return costumerTypes;}

    /**
     * Sends a project and list of devices to the documentationManager.
     * @param project
     * @param devices
     * @return the result from the documentation manager.
     * @throws SQLException
     */
    public Project saveNewProject(Project project, List<Device> devices) throws SQLException {
       return documentationManager.createProject(project, devices);
    }

    /**
     * Sends a project to the documentationManager to get a project updated.
     * @param project
     * @throws Exception
     */
    public void sentToProjectManager(Project project) throws Exception {
        documentationManager.sendToPMOrTech(project);
    }

    /**
     * Gets all devices for the opned project
     * @param project
     * @throws Exception
     */
    public void getAllDevicesForProject(Project project) throws Exception {
        devicesObservableList.clear();
        List<Device> deviceList = documentationManager.getDevicesForProject(project);
        devicesObservableList.addAll(deviceList);
    }

    public ObservableList getDevicesObservableList() {return devicesObservableList;}

    /**
     * Sends a project and a list of new devices to the documentation manager to get the project and devices for the project updated.
     * @param project
     * @param devices
     * @throws Exception
     */
    public void saveproject(Project project, ObservableList<Device> devices) throws Exception {
        List<Device> devicesOnProject = documentationManager.getDevicesForProject(project);
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


    public boolean lastProjectEditMatch(Project project) throws Exception {return documentationManager.lastProjectEditMatch(project);}


    public Project getProjectFromId(Project project) throws Exception {return documentationManager.getProjectFromId(project);}

    public void getAllDeviceFromDB() throws Exception {deviceTypes = documentationManager.getAllDeviceTypes();}

    public List<DeviceType> getDeviceTypes() {return deviceTypes;}

    /**
     * Adds a device to the device list and creates a new device type if the DeviceType does not exist
     * @param device
     * @param deviceType
     * @throws Exception
     */
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

    /**
     * Gets all the ids from users that are working on a project
     * @param project
     * @return a list of all ids from users that are working on a project
     * @throws Exception
     */
    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception{
        List<Integer> allUsersId = documentationManager.getUsersWorkingOnProject(project);
        return allUsersId;
    }

    public ObservableList<File> getImagesObservableList()
    {return imagesObservableList;}

    /**
     * Creates a pdf file for a project
     * @param project
     * @param devices
     * @param files
     * @throws IOException
     */
    public void createPdf(Project project, ObservableList<Device> devices, ObservableList<File> files) throws Exception {
        PDFGenerator pdfGenerator = new PDFGenerator(project,devices,files);
        pdfGenerator.createDocument();
    }

    public Path getPdf() {return pdf.toPath();}

    /**
     * Sends dropBoxFilePath to dropBoxApiManager
     * @param dropBoxFilePath
     * @throws DbxException
     */
    public void deleteFilesFromDropBox(String dropBoxFilePath) throws DbxException {
        dropBoxAPIManager.deleteFilesFromDropBox(dropBoxFilePath);
    }

    /**
     * Sends localFilePath and dropBoxFilePath to dropBoxApiManager.
     * @param localFilePath
     * @param dropBoxFilePath
     * @throws DbxException
     */
    public void uploadFilesFromDropBox(String localFilePath, String dropBoxFilePath) throws DbxException {
        dropBoxAPIManager.uploadFilesFromDropBox(localFilePath, dropBoxFilePath);
    }

    /**
     * Sends localFilePath and dropBoxFilePath to dropBoxApiManager.
     * @param dropBoxFilePath
     * @param fileName
     * @throws DbxException
     */
    public void downloadFilesFromDropBox(String dropBoxFilePath, String fileName) throws DbxException {
        dropBoxAPIManager.downloadFilesFromDropBox(dropBoxFilePath, fileName);
    }

    /**
     * get all the downloaded files from dropbox for a project and adds them to the device list.
     * @param projectId
     * @throws DbxException
     */
    public void downloadProjectFilesFromDropBox(int projectId) throws DbxException {
        getImagesObservableList().clear();
        for (Metadata m: readFilesFromDropBox(projectId)){
            downloadFilesFromDropBox(m.getPathLower(), m.getName());
            File file = new File("DownloadedDropBoxFiles//" + m.getName());
            getImagesObservableList().add(file);
        }
    }

    /**
     * Reads the files from dropbox
     * @param projectId
     * @return the files from dropbox.
     * @throws DbxException
     */
    public List<Metadata> readFilesFromDropBox(int projectId) throws DbxException {
        return dropBoxAPIManager.readFilesFromDropBox("/"+projectId);
    }

    /**
     * Reads all files from dropbox
     * @return all files from dropbox
     * @throws DbxException
     */
    public List<Metadata> readAllFilesFromDropBox() throws DbxException {
        return dropBoxAPIManager.readAllFilesFromDropBox();
    }

    /**
     * sends a list of localFilePaths to the dropboxApiManager to get them deleted
     * @param localFilePaths
     * @throws Exception
     */
    public void deleteLocalFiles(List<String> localFilePaths) throws Exception {
        dropBoxAPIManager.deleteLocalFiles(localFilePaths);
    }

    /**
     * Creates a new local file.
     * @param file
     * @return a local file
     * @throws Exception
     */
    public File createLocalFile(File file) throws Exception {return dropBoxAPIManager.createLocalFile(file);}

    public List<File> getImagesToBeSaved() {return imagesToBeSaved;}

    /**
     * Sends a list of devices to the manager to get them deleted.
     * @param devices
     * @throws SQLException
     */
    public void deleteDevices(List<Integer> devices) throws SQLException {
        documentationManager.deleteDevices(devices);
    }
}
