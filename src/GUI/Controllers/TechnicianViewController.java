package GUI.Controllers;

import BE.Project;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class TechnicianViewController extends BaseController{
    public Button btnShowDevices;
    public BorderPane borderPaneTechnician;
    public TableView tbvDevicelist;
    public TableColumn clmDeviceId;
    public TableColumn clmDeviceName;
    @FXML
    private Text txtViewName;
    @FXML
    private TableColumn<Project, Integer> clmINSId,clmINSId1;
    @FXML
    private TableColumn<Project, String> clmCostumerName,clmINSAddress,clmCostumerName1,clmINSAddress1;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate,clmINSDate1;
    @FXML
    private TableView tbvMyInstallationlist, tbvInstallationlist;

    @FXML
    private Button btnShowInstallations,btnshowMyInstallations, btnLogout;

    @Override
    public void setup() {
        toggleViews(true,false, false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up tableviews", e));
        }
    }

    public void handleShowMyInstallations(ActionEvent actionEvent) {
        toggleViews(false,true, false);
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true,false, false);
    }

    public void handleCreate(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        DocumentationViewController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("WUAV Documentation Documentation window");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleOpen(ActionEvent actionEvent) {
        if(tbvMyInstallationlist.isVisible())
        {
            Project project = (Project) tbvMyInstallationlist.getFocusModel().getFocusedItem();
            try {
                openEditWindow(project);
            } catch (IOException e) {
                ExceptionHandler.displayError(new RuntimeException("Failed to open installation", e));
            }
        }
        else if (tbvInstallationlist.isVisible())
        {
            Project project = (Project)  tbvInstallationlist.getFocusModel().getFocusedItem();
            try {
                openEditWindow(project);
            } catch (IOException e) {
                ExceptionHandler.displayError(new RuntimeException("Failed to open installation", e));
            }
        }
    }

    private void openEditWindow(Project project) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        DocumentationViewController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        controller.setOpenedProject(project);
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("WUAV Documentation Documentation window");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
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
        getModelsHandler().getTechnicianModel().getAllProjects();
        getModelsHandler().getTechnicianModel().getAllMyProjects(getModelsHandler().getLoginModel().getUser());
        getModelsHandler().getTechnicianModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getTechnicianModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));

        //My Projects tableview
        tbvMyInstallationlist.setItems(getModelsHandler().getTechnicianModel().getMyProjectsObservableList());
        clmINSId1.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName1.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate1.setCellValueFactory(new PropertyValueFactory<>("projectDate"));

        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getTechnicianModel().getDeviceTypeObservableList());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void toggleViews(boolean installations, boolean myInstallations, boolean devices){
        if (installations == true){
            tbvInstallationlist.setVisible(true);
            txtViewName.setText("Installations:");
            btnShowInstallations.setDisable(true);
        }
        else {
            btnShowInstallations.setDisable(false);
            tbvInstallationlist.setVisible(false);
        }

        if (myInstallations == true){
            tbvMyInstallationlist.setVisible(true);
            txtViewName.setText("My Installations:");
            btnshowMyInstallations.setDisable(true);
        }
        else {
            btnshowMyInstallations.setDisable(false);
            tbvMyInstallationlist.setVisible(false);
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

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false,false, true);
    }
}
