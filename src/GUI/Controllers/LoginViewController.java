package GUI.Controllers;

import BE.UserTypes.CEO;
import BE.UserTypes.ProjectManager;
import BE.UserTypes.SalesPerson;
import BE.UserTypes.Technician;
import GUI.Models.ModelsHandler;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
      login();
    }

    /**
     * Logs a user in if the username and password is correct.
     */
    private void login()
    {
        try {
            try
            {
                getModelsHandler().getLoginModel().loginAction(txtUsername.getText(),pfPasswordField.getText());
            } catch (SQLException e) {
                ExceptionHandler.displayError(e);
            }
            if(getModelsHandler().getLoginModel().getUser() == null)
            {
                AlertOpener.confirm("Login failed","Username or password was incorrect please try again.");
            }
            else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
            {
                openCEO();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }
            else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
            {
                openTechnician();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }
            else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
            {
                openProjectManager();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }
            else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(SalesPerson.class.getSimpleName()))
            {
                openSalesPerson();
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new IOException("Failed to open new window", e));
        }
    }

    /**
     * Opens the CEO window.
     * @throws IOException
     */
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

    /**
     * Opens the Technician window.
     * @throws IOException
     */
    private void openTechnician() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/TechnicianView.fxml"));

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
        stage.setTitle("WUAV Documentation Technician");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.show();
    }

    /**
     * Opens the ProjectManager window.
     * @throws IOException
     */
    private void openProjectManager() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/ProjectManagerView.fxml"));

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
        stage.setTitle("WUAV Documentation ProjectManager");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.show();
    }

    /**
     * Opnes the SalesPerson window.
     * @throws IOException
     */
    private void openSalesPerson() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/SalesPersonView.fxml"));

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
        stage.setTitle("WUAV Documentation SalesPerson");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.show();
    }

    /**
     * Closes the application.
     * @param actionEvent
     */
    public void closeAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * logs in with a press of the enter key.
     * @param keyEvent
     */
    @FXML
    public void handleEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            login();
        }
    }
}
