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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;


public class ProjectManagerViewController extends BaseController{

    @FXML
    private TextField txfSearch;
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
    private Button btnLogout,btnOpen,btnCreate,btnDelete;

    @FXML
    private TableView<Project> tbvInstallationlist;
    @FXML
    private TableColumn<Project, Integer> clmINSId;
    @FXML
    private TableColumn<Project, String> clmCostumerName,clmINSAddress;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate;
    @FXML
    private Text txtViewName;
    private boolean searchName = true,  searchAddress = true,  searchDate = true;

    @Override
    public void setup() {
        toggleViews();
        setupButtonIcons();
        lblLoggedInAs.setText("Project Manager: " + getModelsHandler().getLoginModel().getUser().getName());
        try {
            setUpTableView();
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to setup tableviews", e));
        }
    }

    /**
     * sets the icons for the buttons
     */
    private void setupButtonIcons()
    {
        btnLogout.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-logout-80.png")));
        btnCreate.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-create-80.png")));
        btnDelete.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-empty-trash-80.png")));
        btnOpen.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-opened-folder-80.png")));
    }

    /**
     * Logs the current user out of the program and gives the option of logging in again.
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
            e.printStackTrace();
            ExceptionHandler.displayError(new Exception("Failed to logout", e));
        }
    }

    /**
     * Opens the documentation window to create a new project
     * @param actionEvent
     */
    public void handleCreate(ActionEvent actionEvent) {
        handleCreateProject();
    }

    /**
     * Opens the documentation window without a selected project.
     */
    public void handleCreateProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));


            Parent root = loader.load();
            Stage stage = new Stage();

            BaseController controller = loader.getController();

            controller.setModel(ModelsHandler.getInstance());

            controller.setup();

            stage.setScene(new Scene(root));
            stage.setTitle("WUAV Documentation Documentation window");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * deletes a selected element from a tableview
     * @param actionEvent
     */
    public void handleDelete(ActionEvent actionEvent) {
        try {
            getModelsHandler().getProjectManagerModel().deleteProject(tbvInstallationlist.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens a new window when the open button
     * @param actionEvent
     */
    public void handleOpen(ActionEvent actionEvent) {

        Project project = tbvInstallationlist.getFocusModel().getFocusedItem();
        try {
            openEditWindow(project);
        } catch (IOException e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to open installation", e));
        }
    }

    /**
     * opens the documentation view to edit an existing project.
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

    /**
     * Clears and resets some GUI elements.
     */
    private void toggleViews(){
        txfSearch.clear();

        tbvInstallationlist.setVisible(true);
        txtViewName.setText("Installations:");
    }

    /**
     * sets up the project tableview.
     * @throws Exception
     */
    private void setUpTableView() throws Exception {
        getModelsHandler().getProjectManagerModel().getAllProjects();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));
    }

    /**
     * Searches through the project tableview when a key is pressed.
     * @param keyEvent
     */
    public void searchOnButtonPress(KeyEvent keyEvent) {
        searchProject();
    }

    /**
     * Looks through the projects tableview and finds any that contains the search qury.
     */
    private void searchProject() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getProjectManagerModel().searchProject(search, searchName, searchAddress, searchDate);
        else if (search == null){
            getModelsHandler().getProjectManagerModel().clearSearch();
        }
    }

    /**
     * Sets the Search to only search through costumer names
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
     * Sets Date as the element that is searched on.
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
     * Sets the search to look ath the date
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
}
