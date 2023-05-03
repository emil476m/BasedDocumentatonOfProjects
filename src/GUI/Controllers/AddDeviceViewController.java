package GUI.Controllers;

import BE.CostumerType;
import BE.Device;
import BE.DeviceType;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddDeviceViewController extends BaseController{

    @FXML
    private TextField txtDeviceType, txtUsername, txtPassword, txtCDeviceType;
    @FXML
    private MenuButton menuType;
    @FXML
    private Button btnCancle, btnAddDevice;

    List<DeviceType> deviceTypes;

    List<TextField> textFields;

    @Override
    public void setup() {
        deviceTypes = new ArrayList<>();
        textFields = new ArrayList<>();
        addTextFieldstoList();
        txtDeviceType.setEditable(false);
        txtCDeviceType.setVisible(false);
        try {
            addMenuItems();
            checkDeviceType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addTextFieldstoList()
    {
        textFields.add(txtDeviceType);
        textFields.add(txtPassword);
        textFields.add(txtUsername);
        textFields.add(txtCDeviceType);
    }

    private Boolean checkTextFields()
    {
        for (TextField t: textFields)
        {
            if(t.getText().isEmpty())
            {
                return false;
            }
            else if(txtCDeviceType.isDisable())
            {
                return true;
            }
            else
            {
                return true;
            }
        }
        return null;
    }

    private void addMenuItems() throws Exception {
        getModelsHandler().getDocumentationModel().getAllDeviceFromDB();
        deviceTypes = getModelsHandler().getDocumentationModel().getDeviceTypes();
        for (DeviceType dt: deviceTypes)
        {
            menuType.getItems().add(createMenuItems(dt));
        }
    }
    private MenuItem createMenuItems(DeviceType deviceType)
    {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(deviceType.getType());
        menuItem.setOnAction(event -> {
            txtDeviceType.setText(menuItem.getText());
        });
        return menuItem;
    }

    public void handleCancle(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnCancle.getScene().getWindow();
        stage.close();
    }

    private void checkDeviceType()
    {
        txtDeviceType.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtDeviceType.getText().equals("Custom"))
            {
                txtCDeviceType.setVisible(true);
            }
            else
            {
                txtCDeviceType.setVisible(false);
            }
        });
    }

    public void handleAddDevice(ActionEvent actionEvent)
    {
        Device device = null;
        DeviceType deviceType = null;
        if(checkTextFields())
        {
            if(txtCDeviceType.isDisable())
            {
                deviceType = findDeviceType(txtDeviceType.getText());
                device = new Device(findDeviceType(txtDeviceType.getText()).getId(), txtUsername.getText(), txtPassword.getText(), txtDeviceType.getText());
            }
            else if(!txtCDeviceType.isDisable())
            {
                device = new Device(findDeviceType(txtDeviceType.getText()).getId(), txtUsername.getText(), txtPassword.getText(), txtDeviceType.getText());
                deviceType = new DeviceType(txtCDeviceType.getText(), false,true);
            }
            try {
                getModelsHandler().getDocumentationModel().addDeviceToList(device,deviceType);
                Stage stage = (Stage) btnAddDevice.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                ExceptionHandler.displayError(new Exception("Failed to add device please try again", e));
            }
        }
    }

    private DeviceType findDeviceType(String type)
    {
        for (DeviceType dt:deviceTypes)
        {
            if(dt.getType().equals(type))
            {
                return dt;
            }
        }
        return null;
    }
}
