package GUI.Controllers;

import BE.DeviceType;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.beans.Visibility;
import java.io.IOException;
import java.time.LocalDate;

public class CEOController extends BaseController{
    @FXML
    private TextField txfSearch;
    @FXML
    private BorderPane borderPaneCEO;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnShowDevices;
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
    private TableView<DeviceType> tbvDevicelist;
    @FXML
    private TableColumn<DeviceType, Integer> clmDeviceId;
    @FXML
    private TableColumn<DeviceType, String> clmDeviceName;
    @FXML
    private TableColumn<Project, LocalDate> clmINSDate;

    @Override
    public void setup() {
        toggleViews(true, false, false);
        try {
            setUpTableViews();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CEOController(){

    }

    private void toggleViews(boolean users, boolean projects, boolean devices){
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

        //Device tableview
        tbvDevicelist.setItems(getModelsHandler().getCeoModel().getDeviceTypeObservableList());
        clmDeviceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmDeviceName.setCellValueFactory(new PropertyValueFactory<>("type"));
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

    public void handleShowDevices(ActionEvent actionEvent) {
        toggleViews(false, false, true);
    }

    public void handleShowInstallations(ActionEvent actionEvent) {
        toggleViews(false, true, false);
    }

    public void handleShowUsers(ActionEvent actionEvent) {
        toggleViews(true, false, false);
    }

    public void handleCreate(ActionEvent actionEvent) {
        createSelectedItemType();
    }

    public void handleDelete(ActionEvent actionEvent) {
        try {
            deleteSelectedItemType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleOpen(ActionEvent actionEvent) {
        openSelectedItemType();
    }

    private void createDeviceType() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/createDeviceTypeView.fxml"));

        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ExceptionHandler.displayError(new Exception("Failed to open create DeviceType", e));
        }

        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("GUI/Images/WUAV.png"));

        createDeviceTypeController controller = loader.getController();
        controller.setModel(getModelsHandler());

        //checks if user wants to create a new user or edit existing user.
        controller.setup();

        stage.showAndWait();
    }

    private void openSelectedItemType(){

        if (tbvUserlist.isVisible()){
            openUserInfo();
        }
        else if (tbvDevicelist.isVisible()){

        }
        else if (tbvInstallationlist.isVisible()){
            openEditProjectWindow(tbvInstallationlist.getSelectionModel().getSelectedItem());
        }
    }

    private void createSelectedItemType(){

        if (tbvUserlist.isVisible()){
            tbvUserlist.getSelectionModel().clearSelection();
            openUserInfo();
        }
        else if (tbvDevicelist.isVisible()){
            createDeviceType();
        }
        else if (tbvInstallationlist.isVisible()){
            handleCreateProject();
        }
    }

    private void deleteSelectedItemType() throws Exception {
        if (tbvUserlist.isVisible()){
            getModelsHandler().getCeoModel().deleteUser(tbvUserlist.getSelectionModel().getSelectedItem());
        }
        else if (tbvDevicelist.isVisible()){
            getModelsHandler().getCeoModel().deleteDeviceType(tbvDevicelist.getSelectionModel().getSelectedItem());
        }
        else if (tbvInstallationlist.isVisible()){
            getModelsHandler().getCeoModel().deleteProject(tbvInstallationlist.getSelectionModel().getSelectedItem());
        }
    }

    private User getSelectedUser(){
        User user = tbvUserlist.getSelectionModel().getSelectedItem();
        if (user != null){
            return user;
        }
        else
            return null;
    }

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

    private void searchUser() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getCeoModel().searchUsers(search);
        else if (search == null){
            getModelsHandler().getCeoModel().clearSearch();
        }
    }

    private void searchProject() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getCeoModel().searchProject(search);
        else if (search == null){
            getModelsHandler().getCeoModel().clearSearch();
        }
    }

    private void searchDeviceType() {
        String search = txfSearch.getText().toLowerCase();

        if(search != null)
            getModelsHandler().getCeoModel().searchDeviceTypes(search);
        else if (search == null){
            getModelsHandler().getCeoModel().clearSearch();
        }
    }

    public void searchOnButtonPress(KeyEvent keyEvent) {
        selectSearch();
    }

    private void selectSearch(){
        if (tbvUserlist.isVisible()){
            searchUser();
        }
        else if (tbvDevicelist.isVisible()){
            searchDeviceType();
        }
        else if (tbvInstallationlist.isVisible()){
            searchProject();
        }
    }
}
