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

    /**
     * Adds all textFields to an arrayList.
     */
    private void addTextFieldstoList()
    {
        textFields.add(txtDeviceType);
        textFields.add(txtPassword);
        textFields.add(txtUsername);
        textFields.add(txtCDeviceType);
    }

    /**
     * Checks all the TextFields if they are empty or disabled
     * @return false if they are empty return true if they are not empty or if they are disabled.
     */
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

    /**
     * Adds a menuItem for each DeviceType found.
     * @throws Exception
     */
    private void addMenuItems() throws Exception {
        getModelsHandler().getDocumentationModel().getAllDeviceFromDB();
        deviceTypes = getModelsHandler().getDocumentationModel().getDeviceTypes();
        for (DeviceType dt: deviceTypes)
        {
            menuType.getItems().add(createMenuItems(dt));
        }
    }

    /**
     * Creates menuItems for the DeviceType
     * @param deviceType
     * @return a menuItem with an onAction.
     */
    private MenuItem createMenuItems(DeviceType deviceType)
    {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(deviceType.getType());
        menuItem.setOnAction(event -> {
            txtDeviceType.setText(menuItem.getText());
        });
        return menuItem;
    }

    /**
     * Closes the window if the button is pressed.
     * @param actionEvent
     */
    public void handleCancle(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnCancle.getScene().getWindow();
        stage.close();
    }

    /**
     * checks on the deviceType textfield if it says custom or not.
     */
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

    /**
     * Adds a device to the project that is either being made or being edited.
     * @param actionEvent
     */
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

    /**
     * Findes the right deviceType that fits the text form the deviceType textfield
     * @param type
     * @return
     */
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
