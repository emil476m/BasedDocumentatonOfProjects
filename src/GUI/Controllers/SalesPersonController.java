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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SalesPersonController extends BaseController {
    public TextField txfSearch;
    public MenuButton btnSearchChoice;
    public MenuItem miName;
    public MenuItem miAddress;
    public MenuItem miDate;
    public Text txtSearchText;
    @FXML
    private BorderPane borderPaneTechnician;
    @FXML
    private Button btnLogout;
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
    private boolean searchName = true,  searchAddress = true,  searchDate = true;

    @Override
    public void setup() {
        toggleViews();
        logoutButtonSetup();
        try {
            setUpTableViews();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up tableviews", e));
        }
    }

    /**
     * sets the icon for the logout button
     */
    private void logoutButtonSetup()
    {
        btnLogout.setGraphic(new ImageView(new Image("/GUI/Images/icons8-logout-80.png")));
    }

    public void handleOpen(ActionEvent actionEvent) {
        try {
            openDocumentationWindow((Project) tbvInstallationlist.getFocusModel().getFocusedItem());
        } catch (IOException e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to open the documentation please try again", e));
        }
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
        getModelsHandler().getSalesPersonModel().getAllProjects();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getSalesPersonModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));
    }

    private void toggleViews(){
        txfSearch.clear();
        tbvInstallationlist.setVisible(true);
        txtViewName.setText("Installations:");
        btnOpen.setVisible(true);
    }



    public void searchOnButtonPress(KeyEvent keyEvent) {
        selectSearch();
    }

    private void searchProject() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getSalesPersonModel().searchProject(search, searchName, searchAddress, searchDate);
        else if (search == null){
            getModelsHandler().getSalesPersonModel().clearSearch();
        }
    }

    private void selectSearch(){
        if (tbvInstallationlist.isVisible()){
            searchProject();
        }
    }

    public void handleNameChoice(ActionEvent actionEvent) {
        if (miName.getText().equals("Name")){
            btnSearchChoice.setText("Name");
            miName.setText("All");
            miAddress.setText("Address");
            miDate.setText("Date");
            searchName = true;
            searchAddress = false;
            searchDate = false;
        } else if (miName.getText().equals("All")) {
            btnSearchChoice.setText("All");
            miName.setText("Name");
            miAddress.setText("Address");
            miDate.setText("Date");
            searchName = true;
            searchAddress = true;
            searchDate = true;
        }


    }

    public void handleAddressChoice(ActionEvent actionEvent) {
        if (miAddress.getText().equals("Address")){
            btnSearchChoice.setText("Address");
            miName.setText("Name");
            miAddress.setText("All");
            miDate.setText("Date");
            searchName = false;
            searchAddress = true;
            searchDate = false;
        } else if (miAddress.getText().equals("All")) {
            btnSearchChoice.setText("All");
            miName.setText("Name");
            miAddress.setText("Address");
            miDate.setText("Date");
            searchName = true;
            searchAddress = true;
            searchDate = true;
        }
    }

    public void handleDateChoice(ActionEvent actionEvent) {
        if (miDate.getText().equals("Date")){
            btnSearchChoice.setText("Date");
            miName.setText("Name");
            miAddress.setText("Address");
            miDate.setText("All");
            searchName = false;
            searchAddress = false;
            searchDate = true;
        } else if (miDate.getText().equals("All")) {
            btnSearchChoice.setText("All");
            miName.setText("Name");
            miAddress.setText("Address");
            miDate.setText("Date");
            searchName = true;
            searchAddress = true;
            searchDate = true;
        }
    }


    private void openDocumentationWindow(Project project) throws IOException {
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
        stage.show();
    }
}
