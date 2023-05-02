package GUI.Controllers;

import BE.DeviceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class createDeviceTypeController extends BaseController{
    public Button btnExit;
    @FXML
    private TextField txtfDeviceTypeName;

    @FXML
    private void handleExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCreateDeviceType(ActionEvent actionEvent) {
        if (!txtfDeviceTypeName.getText().isEmpty() && txtfDeviceTypeName != null){
            try {
                DeviceType deviceType = new DeviceType(txtfDeviceTypeName.getText(), false, false);
                getModelsHandler().getCeoModel().createDeviceType(deviceType);
                handleExit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void setup() {

    }
}
