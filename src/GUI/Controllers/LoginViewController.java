package GUI.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import java.awt.*;

public class LoginViewController extends BaseController{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pfPasswordField;
    @Override
    public void setup() {
    }


    public void loginAction(ActionEvent actionEvent) {
        getModelsHandler().getLoginModel().loginAction(txtUsername.getText(),pfPasswordField.getText());
    }
}
