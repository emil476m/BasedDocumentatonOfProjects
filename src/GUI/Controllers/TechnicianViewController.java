package GUI.Controllers;

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

import java.io.IOException;
import java.time.LocalDate;

public class TechnicianViewController extends BaseController{
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
        toggleViews(true,false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up tableviews", e));
        }
    }

    public void handleShowMyInstallations(ActionEvent actionEvent) {
        toggleViews(false,true);
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true,false);
    }

    public void handleCreate(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

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
        stage.setTitle("WUAV Documentation Documentation window");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.show();
    }

    public void handleDelete(ActionEvent actionEvent) {
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
        getModelsHandler().getTechnicianModel().getAllProjects();
        getModelsHandler().getTechnicianModel().getAllMyProjects(getModelsHandler().getLoginModel().getUser());

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getTechnicianModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(new PropertyValueFactory<>("projectLocation"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));

        //My Projects tableview
        tbvMyInstallationlist.setItems(getModelsHandler().getTechnicianModel().getMyProjectsObservableList());
        clmINSId1.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName1.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress1.setCellValueFactory(new PropertyValueFactory<>("projectLocation"));
        clmINSDate1.setCellValueFactory(new PropertyValueFactory<>("projectDate"));
    }

    private void toggleViews(boolean installations, boolean myInstallations){
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
    }
}
