package GUI.Controllers;

import BE.DeviceType;
import BE.Project;
import GUI.Models.ModelsHandler;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;


public class ProjectManagerViewController extends BaseController{

    @FXML
    private Button btnShowDevices,btnshowInstallations, btnLogout;

    @FXML
    private TableView tbvInstallationlist,tbvDevicelist;
    @FXML
    private TableColumn<Project, Integer> clmINSId;
    @FXML
    private TableColumn<Project, String> clmCostumerName,clmINSAddress;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate;

    @FXML
    private TableColumn<DeviceType, Integer> clmDeviceId;

    @FXML
    private TableColumn<DeviceType, String> clmDeviceName;
    @FXML
    private Text txtViewName;

    @Override
    public void setup() {
        toggleViews(true,false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to setup tableviews", e));
        }
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true,false);
    }

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false,true);
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

    public void handleCreate(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleOpen(ActionEvent actionEvent) {
    }

    private void toggleViews(boolean projects, boolean devices){

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
        getModelsHandler().getProjectManagerModel().getAllProjects();
        getModelsHandler().getProjectManagerModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(new PropertyValueFactory<>("projectLocation"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));


        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getProjectManagerModel().getDeviceTypesObservablelist());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
    }
}
