package GUI.Controllers;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.Date;

public class CEOController extends BaseController{
    public BorderPane borderPaneCEO;
    public Button btnLogout;
    public Button btnShowDevices;
    public Button btnshowInstallations;
    public Button btnShowUsers;
    public Button btnCreate;
    public Button btnDelete;
    public Button btnOpen;
    public Text txtViewName;
    public TableView<User> tbvUserlist;
    public TableColumn<User, String> clmUserId;
    public TableColumn<User, String> clmUserName;
    public TableColumn<User, String> clmUserClass;
    public TableView<Project> tbvInstallationlist;
    public TableColumn<Project, Integer> clmINSId;
    public TableColumn<Project, String> clmCostumerName;
    public TableColumn<Project, String> clmINSAddress;
    public TableView<DeviceType> tbvDevicelist;
    public TableColumn<DeviceType, Integer> clmDeviceId;
    public TableColumn<DeviceType, String> clmDeviceName;
    public TableColumn<Project, LocalDate> clmINSDate;

    @Override
    public void setup() {
        toggleViews(true, false, false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CEOController(){

    }

    private void toggleViews(boolean users, boolean projects, boolean devices){
        if (users == true){
            tbvUserlist.setVisible(true);
            txtViewName.setText("Users:");
            btnShowUsers.setDisable(true);
        }
        else {
            btnShowUsers.setDisable(false);
            tbvUserlist.setVisible(false);
        }

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
        getModelsHandler().getCeoModel().getAllProjects();
        getModelsHandler().getCeoModel().getAllUsers();
        getModelsHandler().getCeoModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getCeoModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(new PropertyValueFactory<>("projectLocation"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));

        //User tableview
        tbvUserlist.setItems(getModelsHandler().getCeoModel().getUserObservableList());
        clmUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        clmUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmUserClass.setCellValueFactory(new PropertyValueFactory<>("userClass"));

        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getCeoModel().getDeviceTypeObservableList());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void handleLogout(ActionEvent actionEvent) {
    }

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false, false, true);
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(false, true, false);
    }

    public void handleShowUsers(ActionEvent actionEvent) {
        toggleViews(true, false, false);

    }

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleOpen(ActionEvent actionEvent) {
    }
}
