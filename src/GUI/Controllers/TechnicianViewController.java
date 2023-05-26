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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class TechnicianViewController extends BaseController{
    @FXML
    private MenuButton btnSearchChoice;
    @FXML
    private MenuItem miName;
    @FXML
    private MenuItem miAddress;
    @FXML
    private MenuItem miDate;
    @FXML
    private Text txtSearchText;
    @FXML
    private Label lblLoggedInAs;
    @FXML
    private BorderPane borderPaneTechnician;
    @FXML
    private TextField txfSearch;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnOpen, btnScreenSize;
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

    private boolean searchName = true,  searchAddress = true,  searchDate = true;

    @Override
    public void setup() {
        toggleViews(false,true);
        setupButtonIcons();
        lblLoggedInAs.setText("Technician: " + getModelsHandler().getLoginModel().getUser().getName());
        try {
            setUpTableViews();
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("failed to set up tableviews", e));
        }
    }

    /**
     * sets  up the icons for the buttons.
     */
    private void setupButtonIcons()
    {
        btnLogout.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-logout-80.png")));
        btnOpen.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-opened-folder-80.png")));
        btnCreate.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-create-80.png")));
        btnShowInstallations.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-stack-of-paper-80.png")));
        btnshowMyInstallations.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-stack-of-paper-80.png")));
        btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-full-screen-80.png")));
    }

    public void handleShowMyInstallations(ActionEvent actionEvent) {
        toggleViews(false,true);
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(true,false);
    }


    /**
     * Opens the documentation window
     * @param actionEvent
     * @throws IOException
     */
    public void handleCreate(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        DocumentationViewController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to set the model", e));
        }
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("WUAV Documentation Documentation window");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Opens the Documentation view with the project that the user has selected.
     * @param actionEvent
     */
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

    /**
     * Opens the documentation view with all textfields filled out with the information of the project.
     * @param project
     * @throws IOException
     */
    private void openEditWindow(Project project) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        DocumentationViewController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to open edit window",e));
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

    /**
     * Logs the current user out and gives the chance to log in again as the same or a different user
     * @param actionEvent
     */
    public void handleLogout(ActionEvent actionEvent) {
        try{
            String title = "You are logging out!";
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
            ExceptionHandler.displayError(new Exception("Failed to logout", e));
        }
    }

    /**
     * Sets up the tableviews
     * @throws Exception
     */
    private void setUpTableViews() throws Exception {
        getModelsHandler().getTechnicianModel().getAllProjects();
        getModelsHandler().getTechnicianModel().getAllMyProjects(getModelsHandler().getLoginModel().getUser());

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
    }

    /**
     * Toggles which tableView is shown to the user
     * @param installations
     * @param myInstallations
     */
    private void toggleViews(boolean installations, boolean myInstallations){
        txfSearch.clear();
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

    /**
     * Searches the myInstallations tableview for the query
     */
    private void searchMyProject() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getTechnicianModel().searchMyProject(search, searchName, searchAddress, searchDate);
        else if (search == null){
            getModelsHandler().getTechnicianModel().clearSearch();
        }
    }

    /**
     * Searches the installations tableview for the query
     */
    private void searchProject() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getTechnicianModel().searchProject(search, searchName, searchAddress, searchDate);
        else if (search == null){
            getModelsHandler().getTechnicianModel().clearSearch();
        }
    }


    public void searchOnButtonPress(KeyEvent keyEvent) {
        selectSearch();
    }

    /**
     * Selects which search method to use
     */
    private void selectSearch(){
        if (tbvMyInstallationlist.isVisible()){
            searchMyProject();
        }
        else if (tbvInstallationlist.isVisible()){
            searchProject();
        }
    }

    /**
     * sets the search to look at the costumer name
     * @param actionEvent
     */
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

    /**
     * sets the search to look at the address
     * @param actionEvent
     */
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

    /**
     * sets the search to look at date
     * @param actionEvent
     */
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

    /**
     * Handles the resizing of the window when a button is pressed.
     * @param actionEvent
     */
    public void handleScreenSize(ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnScreenSize.getScene().getWindow();
        if(stage.isMaximized())
        {
            stage.setMaximized(false);
            btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-full-screen-80.png")));
        } else if (!stage.isMaximized())
        {
            stage.setMaximized(true);
            btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-normal-screen-80.png")));
        }
    }
}
