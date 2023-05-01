package GUI.Controllers;

import BE.Project;
import GUI.Controllers.BaseController;
import GUI.Models.ModelsHandler;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SalesPersonController extends BaseController {
    @FXML
    private BorderPane borderPaneTechnician;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnShowInstallations;
    @FXML
    private Button btnShowDevices;
    @FXML
    private Button btnOpen;
    @FXML
    private Text txtViewName;
    @FXML
    private TableView tbvInstallationlist;
    @FXML
    private TableColumn clmINSId;
    @FXML
    private TableColumn clmCostumerName;
    @FXML
    private TableColumn<Project, String> clmINSAddress;
    @FXML
    private TableColumn clmINSDate;
    @FXML
    private TableView tbvDevicelist;
    @FXML
    private TableColumn clmDeviceId;
    @FXML
    private TableColumn clmDeviceName;

    @Override
    public void setup() {
        toggleViews(true, false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up tableviews", e));
        }
    }


    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true, false);
    }



    public void handleOpen(ActionEvent actionEvent) {
    }

    public void handleLogout(ActionEvent actionEvent) {
        try{
            String title = "Error Message";
            String contextText = "Are you sure want to logout?";
            if (AlertOpener.confirm(title, contextText)){
                // Link your login form and show it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/LoginView.fxml"));
                Parent root = loader.load();

                Stage stage1 = new Stage();
                Scene scene = new Scene(root);

                BaseController controller = loader.getController();
                getModelsHandler().shutdownModelsHandelder();
                controller.setModel(ModelsHandler.getInstance());
                controller.setup();

                stage1.setTitle("ProjectLog");
                stage1.initStyle(StageStyle.UNDECORATED);
                stage1.getIcons().add(new Image("GUI/Images/WUAV.png"));
                stage1.setScene(scene);
                stage1.show();

                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new Exception("Failed to logout", e));
        }
    }


    private void setUpTableViews() throws Exception {
        getModelsHandler().getSalesPersonModel().getAllProjects();;
        getModelsHandler().getSalesPersonModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getSalesPersonModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));


        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getSalesPersonModel().getDeviceTypeObservableList());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void toggleViews(boolean installations, boolean devices){
        if (installations == true){
            tbvInstallationlist.setVisible(true);
            txtViewName.setText("Installations:");
            btnShowInstallations.setDisable(true);
            btnOpen.setVisible(true);
        }
        else {
            btnShowInstallations.setDisable(false);
            tbvInstallationlist.setVisible(false);
        }

        if (devices == true){
            tbvDevicelist.setVisible(true);
            txtViewName.setText("DeviceTypes:");
            btnShowDevices.setDisable(true);
            btnOpen.setVisible(false);
        }
        else {
            btnShowDevices.setDisable(false);
            tbvDevicelist.setVisible(false);
        }
    }

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false, true);
    }
}
