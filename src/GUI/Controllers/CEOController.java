package GUI.Controllers;

import BE.Project;
import BE.UserTypes.User;
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

public class CEOController extends BaseController{
    @FXML
    private Label lblLoggedInAs;
    @FXML
    private Text txtSearchText;
    @FXML
    private Button btnSearch;
    @FXML
    private MenuButton btnSearchChoice;
    @FXML
    private MenuItem miName;
    @FXML
    private MenuItem miAddress;
    @FXML
    private MenuItem miDate;
    @FXML
    private TextField txfSearch;
    @FXML
    private BorderPane borderPaneCEO;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnshowInstallations;
    @FXML
    private Button btnShowUsers;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnOpen;
    @FXML
    private Text txtViewName;
    @FXML
    private TableView<User> tbvUserlist;
    @FXML
    private TableColumn<User, String> clmUserId;
    @FXML
    private TableColumn<User, String> clmUserName;
    @FXML
    private TableColumn<User, String> clmUserClass;
    @FXML
    private TableView<Project> tbvInstallationlist;
    @FXML
    private TableColumn<Project, Integer> clmINSId;
    @FXML
    private TableColumn<Project, String> clmCostumerName;
    @FXML
    private TableColumn<Project, String> clmINSAddress;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate;

    private boolean searchName,  searchAddress,  searchDate;

    @Override
    public void setup() {
        toggleViews(true, false);
        buttonSetup();
        lblLoggedInAs.setText("CEO: " + getModelsHandler().getLoginModel().getUser().getName());
        try {
            setUpTableViews();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * sets the icons for the buttons
     */
    private void buttonSetup()
    {
        btnLogout.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-logout-80.png")));
        btnSearch.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-search-80.png")));
        btnCreate.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-create-80.png")));
        btnDelete.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-empty-trash-80.png")));
        btnOpen.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-opened-folder-80.png")));
        btnshowInstallations.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-stack-of-paper-80.png")));
        btnShowUsers.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-people-64.png")));
    }

    /**
     * toggles which tableview is shown to the user.
     * @param users
     * @param projects
     */
    private void toggleViews(boolean users, boolean projects){
        txfSearch.clear();
        if (users == true){
            tbvUserlist.setVisible(true);
            txtViewName.setText("Users:");
            btnShowUsers.setDisable(true);
        }
        else {
            btnShowUsers.setDisable(false);
            tbvUserlist.setVisible(false);
        }

        if (projects == true){
            tbvInstallationlist.setVisible(true);
            txtViewName.setText("Installations:");
            btnshowInstallations.setDisable(true);
            resetSearchToProject();
        }
        else {
            btnshowInstallations.setDisable(false);
            tbvInstallationlist.setVisible(false);
            resetSearchToUsers();
        }
    }

    /**
     * Sets up the different tableviews
     * @throws Exception
     */
    private void setUpTableViews() throws Exception {
        getModelsHandler().getCeoModel().getAllProjects();
        getModelsHandler().getCeoModel().getAllUsers();
        getModelsHandler().getCeoModel().getAllDeviceTypes();

        //Project tableview
        tbvInstallationlist.setItems(getModelsHandler().getCeoModel().getProjectsObservableList());
        clmINSId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        clmCostumerName.setCellValueFactory(new PropertyValueFactory<>("costumerName"));
        clmINSAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress() + " " + "(" + cellData.getValue().getZipCode() + ")"));
        clmINSDate.setCellValueFactory(new PropertyValueFactory<>("projectDate"));

        //User tableview
        tbvUserlist.setItems(getModelsHandler().getCeoModel().getUserObservableList());
        clmUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        clmUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmUserClass.setCellValueFactory(new PropertyValueFactory<>("userClass"));
    }

    /**
     * Logs out the current user and gives the option to login again
     * @param actionEvent
     */
    public void handleLogout(ActionEvent actionEvent) {
        try {
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
     * Shows the installation tableview.
     * @param actionEvent
     */
    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(false, true);
    }

    /**
     * Shows the user tableview.
     * @param actionEvent
     */
    public void handleShowUsers(ActionEvent actionEvent) {
        toggleViews(true, false);
    }

    /**
     * Opens the create window for the selected tableview be it user or installation
     * @param actionEvent
     */
    public void handleCreate(ActionEvent actionEvent) {
        createSelectedItemType();
    }

    /**
     * Deletes the selected item in whatever tableview that is visible.
     * @param actionEvent
     */
    public void handleDelete(ActionEvent actionEvent) {
        try {
            deleteSelectedItemType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens the selected item form the visible tableview
     * @param actionEvent
     */
    public void handleOpen(ActionEvent actionEvent) {
        openSelectedItemType();
    }

    /**
     * Checks what window there has to be opened.
     */
    private void openSelectedItemType(){

        if (tbvUserlist.isVisible()){
            openUserInfo();
        }
        else if (tbvInstallationlist.isVisible()){
            openEditProjectWindow(tbvInstallationlist.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Checks what create window there has to be opened.
     */
    private void createSelectedItemType(){

        if (tbvUserlist.isVisible()){
            tbvUserlist.getSelectionModel().clearSelection();
            openUserInfo();
        }
        else if (tbvInstallationlist.isVisible()){
            handleCreateProject();
        }
    }

    /**
     * Checks what item needs to be deleted and form which tableview.
     */
    private void deleteSelectedItemType() throws Exception {
        if (tbvUserlist.isVisible()){
            getModelsHandler().getCeoModel().deleteUser(tbvUserlist.getSelectionModel().getSelectedItem());
        }
        else if (tbvInstallationlist.isVisible()){
            getModelsHandler().getCeoModel().deleteProject(tbvInstallationlist.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Gets the selected user
     * @return the selected user
     */
    private User getSelectedUser(){
        User user = tbvUserlist.getSelectionModel().getSelectedItem();
        if (user != null){
            return user;
        }
        else
            return null;
    }

    /**
     * Opens the userInfo window.
     */
    private void openUserInfo(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/UserInfoView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ExceptionHandler.displayError(new Exception("Failed to open User Info", e));
        }

        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("GUI/Images/WUAV.png"));

        UserInfoController controller = loader.getController();
        controller.setModel(getModelsHandler());

        //checks if user wants to create a new user or edit existing user.
        controller.setCreateUser(getSelectedUser());
        controller.setup();

        stage.showAndWait();
    }

    /**
     * Opens the documentation window with the selected project.
     * @param project
     */
    private void openEditProjectWindow(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/DocumentationView.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();

            DocumentationViewController controller = loader.getController();

            controller.setModel(ModelsHandler.getInstance());

            controller.setOpenedProject(project);
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
     * Searches for a users matching the search query.
     */
    private void searchUser() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getCeoModel().searchUsers(search);
        else if (search == null){
            getModelsHandler().getCeoModel().clearSearch();
        }
    }

    /**
     * Searches for a project matching the query.
     * @param name
     * @param address
     * @param date
     */
    private void searchProject(boolean name, boolean address, boolean date) {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getCeoModel().searchProject(search, name, address, date);
        else if (search == null){
            getModelsHandler().getCeoModel().clearSearch();
        }
    }

    /**
     * Does the search action when a button is pressed.
     * @param keyEvent
     */
    public void searchOnButtonPress(KeyEvent keyEvent) {
        selectSearch();
    }

    /**
     * Checks which search method that has to be used.
     */
    private void selectSearch(){
        if (tbvUserlist.isVisible()){
            searchUser();
        }
        else if (tbvInstallationlist.isVisible()){
            searchProject(searchName, searchAddress, searchDate);
        }
    }

    /**
     * Searches on the costumer name in the installations tableview.
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
     * Searches for the address in the installations tableview.
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
     * Searches for the date in the installations tableview.
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
     * Resets the search for the installations tableview.
     */
    private void resetSearchToProject(){
        btnSearchChoice.setText("All");
        miName.setText("Name");
        miAddress.setText("Address");
        miDate.setText("Date");
        searchName = true;
        searchAddress = true;
        searchDate = true;

        btnSearchChoice.setVisible(true);
        miName.setVisible(true);
        miAddress.setVisible(true);
        miDate.setVisible(true);
        btnSearch.setVisible(false);
        txtSearchText.setVisible(true);
    }

    /**
     * Resets the search for the users tableView.
     */
    private void resetSearchToUsers(){
        btnSearchChoice.setVisible(false);
        miName.setVisible(false);
        miAddress.setVisible(false);
        miDate.setVisible(false);
        btnSearch.setVisible(true);
        txtSearchText.setVisible(false);
    }

    /**
     * Searches when the search button is pressed
     * @param actionEvent
     */
    public void handleSearch(ActionEvent actionEvent) {
        selectSearch();
    }

}