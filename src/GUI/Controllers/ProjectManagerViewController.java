package GUI.Controllers;

import BE.DeviceType;
import BE.Project;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.time.LocalDate;


public class ProjectManagerViewController extends BaseController{

    @FXML
    private Button btnShowDevices,btnshowInstallations;

    @FXML
    private TableView tbvInstallationlist,tbvDevicelist;
    @FXML
    private TableColumn<Project, Integer> clmINSId;
    @FXML
    private TableColumn<Project, String> clmCostumerName,clmINSAddress;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate;

    @FXML
    private TableColumn<DeviceType, Integer> clmDeviceId;

    @FXML
    private TableColumn<DeviceType, String> clmDeviceName;
    @FXML
    private Text txtViewName;

    @Override
    public void setup() {
        toggleViews(true,false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to setup tableviews", e));
        }
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true,false);
    }

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false,true);
    }

    public void handleLogout(ActionEvent actionEvent) {
    }

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleOpen(ActionEvent actionEvent) {
    }

    private void toggleViews(boolean projects, boolean devices){

        if (projects == true){
            tbvInstallationlist.setVisible(true);
            txtViewName.setText("Installations:");
            btnshowInstallations.setDisable(true);
        }
        else {
            btnshowInstallations.setDisable(false);
            tbvInstallationlist.setVisible(false);
        }

        if (devices == true){
            tbvDevicelist.setVisible(true);
            txtViewName.setText("DeviceTypes:");
            btnShowDevices.setDisable(true);
        }
        else {
            btnShowDevices.setDisable(false);
            tbvDevicelist.setVisible(false);
        }
    }

    private void setUpTableViews() throws Exception {
        getModelsHandler().getProjectManagerModel().getAllProjects();
        getModelsHandler().getProjectManagerModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(new PropertyValueFactory<>("projectLocation"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));


        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getProjectManagerModel().getDeviceTypesObservablelist());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
    }
}
