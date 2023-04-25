package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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
    public TableView tbvUserlist;
    public TableColumn clmUserId;
    public TableColumn clmUserName;
    public TableColumn clmUserClass;
    public TableView tbvInstallationlist;
    public TableColumn clmINSId;
    public TableColumn clmCostumerName;
    public TableColumn clmINSAddress;
    public TableView tbvDevicelist;
    public TableColumn clmDeviceId;
    public TableColumn clmDeviceName;

    @Override
    public void setup() {
        toggleViews(true, false, false);

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
            txtViewName.setText("Devices:");
            btnShowDevices.setDisable(true);
        }
        else {
            btnShowDevices.setDisable(false);
            tbvDevicelist.setVisible(false);
        }
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
