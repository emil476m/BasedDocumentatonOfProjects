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
    public Button btnshowInstellations;
    public Button btnShowUsers;
    public Button btnCreate;
    public Button btnDelete;
    public Button btnOpen;
    public Text txtViewName;
    public TableView tbvUserlist;
    public TableColumn clmUserId;
    public TableColumn clmUserName;
    public TableColumn clmUserClass;
    public TableView tbvInstellationlist;
    public TableColumn clmINSId;
    public TableColumn clmCostumerName;
    public TableColumn clmINSAddress;
    public TableView tbvDevicelist;
    public TableColumn clmDeviceId;
    public TableColumn clmDeviceName;

    @Override
    public void setup() {

    }

    public void handleLogout(ActionEvent actionEvent) {
    }

    public void handleShowDevices(ActionEvent actionEvent) {
    }

    public void handleShowInstellations(ActionEvent actionEvent) {
    }

    public void handleShowUsers(ActionEvent actionEvent) {
    }

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleOpen(ActionEvent actionEvent) {
    }
}
