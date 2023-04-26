package GUI.Controllers;

import BE.UserTypes.CEO;
import GUI.Models.ModelsHandler;
import GUI.Util.ExeptionHandeler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class LoginViewController extends BaseController{
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pfPasswordField;
    @FXML
    private Button btnLogin, btnClose;
    @Override
    public void setup() {
    }


    public void loginAction(ActionEvent actionEvent) {
        try {
            getModelsHandler().getLoginModel().loginAction(txtUsername.getText(),pfPasswordField.getText());
            if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
            {
                openCEO();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            ExeptionHandeler.displayError(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void openCEO() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/CEOView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        BaseController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("WUAV Documentation CEO");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.show();
    }

    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
