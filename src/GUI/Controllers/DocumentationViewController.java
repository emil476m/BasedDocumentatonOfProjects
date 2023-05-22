package GUI.Controllers;
import BE.CostumerType;
import BE.Device;
import BE.Project;
import BE.UserTypes.*;
import GUI.Models.ModelsHandler;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.Metadata;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentationViewController extends BaseController{

    @FXML
    private ImageView imImageView;
    @FXML
    private ListView<User> lvTechniciansOnProject;
    @FXML
    private Button btnRemoveTechnician;
    @FXML
    private Button btnAssignTech,btnOpenPaint,btnSave,btnSend,btnSaveToDevice,btnAddImage,btnAddDevice,btnRemove,btnReturn, btnScreenSize;
    @FXML
    private TextField txtCostumerName,txtAddress,txtZipCode,txtCostumerType,txtLocation, txtCostumerEmail;
    @FXML
    private MenuButton menuTypes;
    @FXML
    private TextArea txtaComments;
    @FXML
    private DatePicker dpDatePicker;
    @FXML
    private ListView lvDevices, lvImages;
    @FXML
    private Text txtTech;

    private List<CostumerType> costumerTypes;

    private List<TextField> textFields;

    private Project opnedProject;

    private File file;

    private ObservableList<File> projectImages;

    private List<Integer> devicestoDelete = new ArrayList<>();

    private boolean lvImagesLastSelected = false;



    @Override
    public void setup() {
        try {
            checkAccesslevel();
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("failed to check access level" , e));
        }
        costumerTypes = new ArrayList<>();
        textFields = new ArrayList<>();
        projectImages = FXCollections.observableArrayList();
        getModelsHandler().getDocumentationModel().getImagesObservableList().clear();
        addTextFields();
        setupButtonIcons();
        try
        {
            generateMenuItems();

            if(txtCostumerType.getText() != null)
            {
                checkCostumertype();
            }

            if(opnedProject !=null)
            {
                setTextFields();
                getAllDropBoxFilesForProject();
            }
            setUpTechniciansRemoveAddAndView();
            setupListViews();
        }
        catch (Exception e)
        {
            ExceptionHandler.displayError(new RuntimeException("failed to set up window", e));
        }
    }

    /**
     * Sets up the listviews in the window.
     */
    private void setupListViews() {
        try {
            getModelsHandler().getDocumentationModel().getDevicesObservableList().clear();
            if(opnedProject != null)
            {
                getModelsHandler().getDocumentationModel().getAllDevicesForProject(opnedProject);
            }
            lvDevices.setItems(getModelsHandler().getDocumentationModel().getDevicesObservableList());
        } catch (Exception e) {
            ExceptionHandler.displayError(new Throwable("Failed to set up lists please try again",e));
        }
        projectImages.clear();
        projectImages.addAll(getModelsHandler().getDocumentationModel().getImagesObservableList());
        setUplvImagesToShowName();
        lvImages.setItems(projectImages);
    }

    /**
     * makes the lvImages listview show filenames instead of filepaths.
     */
    private void setUplvImagesToShowName(){
        lvImages.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            public ListCell<File> call(ListView param) {
                return new ListCell<File>(){
                    @Override
                    protected void updateItem(File item, boolean empty){
                        super.updateItem(item, empty);
                        setText(item == null || empty ? null : item.getName());
                    }
                };
            }
        });
    }

    /**
     * Adds all TextFields to an arraylist.
     */
    private void addTextFields()
    {
        textFields.add(txtCostumerName);
        textFields.add(txtAddress);
        textFields.add(txtZipCode);
        textFields.add(txtCostumerType);
        textFields.add(txtLocation);
        textFields.add(txtCostumerEmail);
    }

    /**
     * Checks the access level of the user that is currently signed in.
     * @throws Exception
     */
    private void checkAccesslevel() throws Exception {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            btnAssignTech.setDisable(true);
            btnAssignTech.setVisible(false);
            txtCostumerType.setEditable(false);
            lvTechniciansOnProject.setVisible(false);
            txtTech.setVisible(false);
            btnRemoveTechnician.setDisable(true);
            btnRemoveTechnician.setVisible(false);
            if(opnedProject != null)
            {
                if (!opnedProject.getCanBeEditedByTech()) {
                    btnAssignTech.setDisable(true);
                    btnAssignTech.setVisible(false);
                    txtAddress.setEditable(false);
                    txtaComments.setEditable(false);
                    txtCostumerName.setEditable(false);
                    txtLocation.setEditable(false);
                    txtZipCode.setEditable(false);
                    menuTypes.setDisable(true);
                    txtCostumerType.setEditable(false);
                    dpDatePicker.setEditable(false);
                    dpDatePicker.setDisable(true);
                    btnSave.setDisable(true);
                    btnAddDevice.setDisable(true);
                    btnSend.setDisable(true);
                    btnAddImage.setDisable(true);
                    btnRemove.setDisable(true);
                    btnOpenPaint.setDisable(true);
                }
                if(!checkIfUserCanEdit() && opnedProject != null)
                {
                    nonEditAccess();
                }
            }
        }

        else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(SalesPerson.class.getSimpleName())){
           nonEditAccess();
        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName())||getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName())){
                txtCostumerType.setEditable(false);
                btnSend.setText("Send to Technician");
                if(opnedProject != null)
                {
                    if(opnedProject.getCanBeEditedByTech())
                    {
                        btnSend.setDisable(true);
                    } else if (!opnedProject.getCanBeEditedByTech()) {
                        btnSend.setDisable(false);
                    }
                }
        }
    }

    /**
     * Makes it so the loggedIn user can not edit.
     */
    private void nonEditAccess()
    {
        btnAssignTech.setDisable(true);
        btnAssignTech.setVisible(false);
        txtAddress.setEditable(false);
        txtaComments.setEditable(false);
        txtCostumerName.setEditable(false);
        txtLocation.setEditable(false);
        txtZipCode.setEditable(false);
        menuTypes.setDisable(true);
        txtCostumerType.setEditable(false);
        dpDatePicker.setEditable(false);
        btnSave.setDisable(true);
        btnAddDevice.setDisable(true);
        btnSend.setDisable(true);
        btnAddImage.setDisable(true);
        btnRemove.setDisable(true);
        btnOpenPaint.setDisable(true);
        lvTechniciansOnProject.setVisible(false);
        txtTech.setVisible(false);
        btnRemoveTechnician.setDisable(true);
        btnRemoveTechnician.setVisible(false);
        dpDatePicker.setDisable(true);
        txtCostumerEmail.setEditable(false);
    }

    /**
     * Generates a Menus items for each costumerType
     * @throws Exception
     */
    private void generateMenuItems() throws Exception {
        getModelsHandler().getDocumentationModel().getAllCostumerTypes();
        costumerTypes = getModelsHandler().getDocumentationModel().getCostumerTypes();
        for (CostumerType c:costumerTypes)
        {
            menuTypes.getItems().add(createMenuItem(c));
        }
    }

    /**
     * Creates a MenuItem
     * @param costumerType
     * @return a menuItem with an onAction.
     */
    private MenuItem createMenuItem(CostumerType costumerType)
    {
       MenuItem menuItem = new MenuItem();
       menuItem.setText(costumerType.getType());
       menuItem.setOnAction(e ->
       {
           txtCostumerType.setText(menuItem.getText());
       });
       return menuItem;
    }

    /**
     * Checks if textFields are empty or disabled
     * @return true if they are not empty or if they are disabled and false if they are empty.
     */
    private Boolean checkTextFields()
    {
        for (TextField t: textFields)
        {
            if(t.getText().isEmpty())
            {
                return false;
            }
            else if(txtLocation.isDisable())
            {
                return true;
            }
            else
            {
                return true;
            }
        }
        return null;
    }

    /**
     * checks the costumerType textfield and adjusts the view according to what the costumer type needs to know.
     */
    private void checkCostumertype()
    {
        txtCostumerType.textProperty().addListener((observable, oldValue, newValue) -> {
            if(txtCostumerType.getText() == null)
            {
                txtLocation.setEditable(false);
            }
            else if(txtCostumerType.getText().equals("Consumer"))
            {
                txtLocation.setDisable(true);
                txtLocation.setVisible(false);
            }
            else
            {
                txtLocation.setDisable(false);
                txtLocation.setVisible(true);
            }
        });
    }

    /**
     * Closes the window.
     * @param actionEvent
     */
    public void handleReturn(ActionEvent actionEvent) {

        if(AlertOpener.confirm("Are you sure?","remember to save before you exit or your changes will be lost"))
        {
            List<String> localFilePaths = new ArrayList<>();
            for (File f: projectImages){
                localFilePaths.add(f.getAbsolutePath());
            }
            try {
                if (localFilePaths != null && !localFilePaths.isEmpty())
                    getModelsHandler().getDocumentationModel().deleteLocalFiles(localFilePaths);
            } catch (Exception e) {
                ExceptionHandler.displayError(new Throwable("failed to delete local files",e));
            }

            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
        }

    }

    /**
     * Saves the documentation to the current device as a pdf file.
     * @param actionEvent
     */
    public void handleSaveToDevice(ActionEvent actionEvent) {
        try
        {
            getModelsHandler().getDocumentationModel().createPdf(opnedProject,lvDevices.getItems(),lvImages.getItems());
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Documentation");
            fileChooser.setInitialFileName(opnedProject.getCostumerName());
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".pdf", "*.pdf"));
            File file = fileChooser.showSaveDialog(btnSaveToDevice.getScene().getWindow());
            Files.copy(getModelsHandler().getDocumentationModel().getPdf(), file.toPath());
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to save file to this device please try again", e));
        }
    }

    /**
     * makes it so a technician can either edit or not edit the openedProject
     * @param actionEvent
     */
    public void handleSendToPMOrTech(ActionEvent actionEvent) {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            if(AlertOpener.confirm("Are you sure?", "You are sending the documentation to the project managers and will not be able to edit after this action.")) {
                sendToPMOrTech(false);
            }

        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()) || getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            if(AlertOpener.confirm("Are you sure?", "You are sending the documentation back to the technician so they can edit in the documentation again.")) {
                sendToPMOrTech(true);
            }
        }
        btnSend.setDisable(true);
    }

    /**
     * makes it so a technician can either edit or not edit the openedProject
     * @param canEdit
     */
    private void sendToPMOrTech(boolean canEdit)
    {
        if(opnedProject != null)
        {
            try {
                Project project = opnedProject;
                project.setCanBeEditedByTech(canEdit);
                updateProjectList(project);
                getModelsHandler().getDocumentationModel().sentToProjectManager(project);
            }
            catch (Exception e) {
                ExceptionHandler.displayError(new Throwable("Failed to send installation to project manager", e));
            }
        }
    }

    /**
     * updates the project observable list when sending a project to a Project Manager or Technician
     * @param project
     */
    private void updateProjectList(Project project)
    {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            for (Project p: getModelsHandler().getTechnicianModel().getProjectsObservableList())
            {
                if(p.getProjectId() == project.getProjectId())
                {
                    getModelsHandler().getTechnicianModel().getProjectsObservableList().remove(p);
                    getModelsHandler().getTechnicianModel().getProjectsObservableList().add(p);
                }
            }
            for (Project p: getModelsHandler().getTechnicianModel().getMyProjectsObservableList())
            {
                if(p.getProjectId() == project.getProjectId())
                {
                    getModelsHandler().getTechnicianModel().getMyProjectsObservableList().remove(p);
                    getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(p);
                }
            }
        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
        {
            for (Project p: getModelsHandler().getCeoModel().getProjectsObservableList())
            {
                if(p.getProjectId() == project.getProjectId())
                {
                    getModelsHandler().getCeoModel().getProjectsObservableList().remove(p);
                    getModelsHandler().getCeoModel().getProjectsObservableList().add(p);
                }
            }
        }

        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            for (Project p: getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist())
            {
                if(p.getProjectId() == project.getProjectId())
                {
                    getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().remove(p);
                    getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().add(p);
                }
            }
        }
    }

    /**
     * Saves or creates a new project.
     * @param actionEvent
     */
    public void handleSave(ActionEvent actionEvent)
    {
        if(opnedProject == null)
        {
            createNewProject();

        }
        else if (opnedProject != null)
        {
            uploadImagesToBeSaved(opnedProject);
            if(!devicestoDelete.isEmpty())
            {
                try {
                    getModelsHandler().getDocumentationModel().deleteDevices(devicestoDelete);
                    devicestoDelete = new ArrayList<>();
                } catch (SQLException e) {
                    ExceptionHandler.displayError(new RuntimeException("Failed to delete devices",e));
                }
            }
            saveProject();
        }
    }

    /**
     * Saves the current changes to the openedProject:
     */
    private void saveProject()
    {
        try {
            String location = "";
            int costumerType = 0;
            String costumerName = txtCostumerName.getText();
            String costumerEmail = txtCostumerEmail.getText();
            String address = txtAddress.getText();
            if(findCostumertype(txtCostumerType.getText()) != opnedProject.getCostumerType())
            {
                costumerType = findCostumertype(txtCostumerType.getText());
            }
            else
            {
                costumerType = opnedProject.getCostumerType();
            }
            String zipCode = txtZipCode.getText();
            String comment = txtaComments.getText();
            if(txtLocation.isDisable())
            {
                location = "";
            }
            else
            {
                location = txtLocation.getText();
            }
            LocalDate date = dpDatePicker.getValue();

            if (getModelsHandler().getDocumentationModel().lastProjectEditMatch(opnedProject)){
                Project project = new Project(opnedProject.getProjectId(), costumerName, costumerEmail, date, location, comment, opnedProject.getProjectCreatorId(), opnedProject.getProjectIsDeleted(), opnedProject.getLastEditedBy(), opnedProject.getCanBeEditedByTech(), Timestamp.from(Instant.now()), costumerType, address, zipCode);
                getModelsHandler().getDocumentationModel().saveproject(project, lvDevices.getItems());
                updateTableviews(project);
                AlertOpener.confirm("Has been saved", "Your changes have been saved.");
            }
            else {
                if (AlertOpener.confirm("Project is not up to date!!", "Please copy your changes and press ok to reload the project")){
                    opnedProject = getModelsHandler().getDocumentationModel().getProjectFromId(opnedProject);
                    setup();
                }
            }

        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("failed to update project", e));
        }
    }

    /**
     * Updates the tableviews so the information matches if you change anything on a project that already exists.
     * @param project
     */
    private void updateTableviews(Project project)
    {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
        {
            for (Project p1 :getModelsHandler().getCeoModel().getProjectsObservableList()){
                if (p1.getProjectId() == project.getProjectId()){
                    getModelsHandler().getCeoModel().getProjectsObservableList().remove(p1);
                    getModelsHandler().getCeoModel().getProjectsObservableList().add(project);
                    break;
                }
            }
        }
        else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            for (Project p1 :getModelsHandler().getTechnicianModel().getProjectsObservableList()){
                if (p1.getProjectId() == project.getProjectId()){
                    getModelsHandler().getTechnicianModel().getProjectsObservableList().remove(p1);
                    getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);

                    for (Project p2 : getModelsHandler().getTechnicianModel().getMyProjectsObservableList()) {
                        if (p1.getProjectId() == p2.getProjectId()) {
                            getModelsHandler().getTechnicianModel().getMyProjectsObservableList().remove(p2);
                            getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
                            break;
                        }
                    }
                    break;
                }
            }
        }
       else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            for (Project p1 :getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist()){
                if (p1.getProjectId() == project.getProjectId()){
                    getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().remove(p1);
                    getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().add(project);
                    break;
                }
            }
        }
    }


    /**
     * updates the table views depending on what type of user is logged in
     * @param projectToBeMade
     * @param devices
     * @throws SQLException
     */
    private void updateTableViewsNewProject(Project projectToBeMade, ObservableList devices) throws Exception {
        Project project = getModelsHandler().getDocumentationModel().saveNewProject(projectToBeMade,devices);
        //Uploads images when project is saved
        uploadImagesToBeSaved(project);
        //Uploads technicians working on project
        getModelsHandler().getCeoModel().addUsersWorkingOnNewProject(getModelsHandler().getCeoModel().getUserOnCurrentProject(), project);

        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
        {
            getModelsHandler().getCeoModel().getProjectsObservableList().add(project);
        }
        else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
            getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);
        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().add(project);
        }
    }

    /**
     * Creates a new project.
     */
    private void createNewProject()
    {
        if(checkTextFields() && !txtaComments.getText().isEmpty() && dpDatePicker.getValue() != null)
        {
            String costumerName = txtCostumerName.getText();
            String location = "";
            String costumerEmail = txtCostumerEmail.getText();
            if(txtLocation.isDisable())
            {
                location = "";
            }
            else
            {
                location = txtLocation.getText();
            }
            LocalDate date = dpDatePicker.getValue();
            String address = txtAddress.getText();
            String zipcode = txtZipCode.getText();
            int creator = getModelsHandler().getLoginModel().getUser().getUserID();
            int costumerType = findCostumertype(txtCostumerType.getText());
            boolean isDeleted = false;
            String comment = txtaComments.getText();
            Project project = new Project(costumerName, costumerEmail, date, location, comment, creator, isDeleted, creator, true, Timestamp.from(Instant.now()), costumerType, address, zipcode);
            try {
                updateTableViewsNewProject(project, lvDevices.getItems());
                AlertOpener.confirm("Has been saved", "Your changes have been saved.");
            } catch (SQLException e) {
                ExceptionHandler.displayError(new SQLException("Failed to save project in Database"));
            } catch (Exception e) {
                ExceptionHandler.displayError(new RuntimeException("Failed to create new project"));
            }
        }
    }

    /**
     * Opens paint.
     * @param actionEvent
     * @throws IOException
     */
    public void handleOpenPaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/PaintAppView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ExceptionHandler.displayError(new Exception("Failed to open paint application", e));
        }

        Stage stage = new Stage();
        stage.setTitle("Paint");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("GUI/Images/WUAV.png"));

        BaseController controller = loader.getController();
        controller.setModel(getModelsHandler());

        controller.setup();

        stage.showAndWait();
    }

    /**
     * Opens assignTech window.
     * @param actionEvent
     */
    public void handleAssignTech(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AssignTechnicianView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            ExceptionHandler.displayError(new Exception("Failed to open Project technician List", e));
        }

        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("GUI/Images/WUAV.png"));

        AssignTechnicianController controller = loader.getController();
        controller.setModel(getModelsHandler());
        controller.setOpenedEvent(opnedProject);

        controller.setup();

        stage.showAndWait();
    }


    /**
     * finds the coustomerType form a string.
     * @param type
     * @return
     */
    private int findCostumertype(String type)
    {
        for (CostumerType c:costumerTypes)
        {
            if(c.getType().equals(type))
            {
                return c.getId();
            }
        }
        return 0;
    }

    /**
     * finds the costumer type form the id.
     * @param id
     * @return
     */
    private String findCostumerTypeFromId(int id)
    {
        for (CostumerType c: costumerTypes)
        {
            if(c.getId() == id)
            {
                return c.getType();
            }
        }
        return null;
    }

    /**
     * sets up the text fields.
     */
    private void setTextFields()
    {
        Project project = opnedProject;
        txtCostumerType.setText(findCostumerTypeFromId(project.getCostumerType()));
        txtCostumerEmail.setText(opnedProject.getCostumerEmail());
        txtCostumerName.setText(project.getCostumerName());
        if(!txtLocation.isDisable())
        {
            txtLocation.setText(project.getProjectLocation());
        }
        txtAddress.setText(project.getAddress());
        txtZipCode.setText(project.getZipCode());
        dpDatePicker.setValue(project.getProjectDate());
        txtaComments.setText(project.getProjectDescription());
    }

    /**
     * sets the OpenedProject.
     * @param project
     */
    public void setOpenedProject(Project project)
    {

        try {
            opnedProject = getModelsHandler().getDocumentationModel().getProjectFromId(project);
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("failed to set the opened project", e));
        }
    }

    /**
     * Sets up the TechniciansRemoveAddAndView.
     */
    private void setUpTechniciansRemoveAddAndView(){
        try {
            if (!getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName())){
                getModelsHandler().getCeoModel().getAllUsers();
            }
            getModelsHandler().getCeoModel().getUserOnCurrentProject().clear();
            if (opnedProject != null)

                getModelsHandler().getCeoModel().getUserOnCurrentProject().addAll(getModelsHandler().getCeoModel().getUsersWorkingOnProject(opnedProject));
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to setup technician List", e));
        }

        lvTechniciansOnProject.setItems(getModelsHandler().getCeoModel().getUserOnCurrentProject());
    }

    /**
     * checks if the current Logged-in user can edit.
     * @return
     * @throws Exception
     */
    private boolean checkIfUserCanEdit() throws Exception {
       List<Integer> userIds = getModelsHandler().getDocumentationModel().getUsersWorkingOnProject(opnedProject);
        for (Integer i: userIds)
        {
            if(i == getModelsHandler().getLoginModel().getUser().getUserID())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a technician form the project.
     * @param actionEvent
     */
    public void handleRemoveTechnician(ActionEvent actionEvent) {
        try {
            if (lvTechniciansOnProject.getSelectionModel().getSelectedItem() != null) {
                getModelsHandler().getCeoModel().deleteFromWorkingOnProject(lvTechniciansOnProject.getSelectionModel().getSelectedItem(), opnedProject);
            }
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to remove technician from List", e));
        }
    }

    /**
     * Opens add device window.
     * @param actionEvent
     * @throws IOException
     */
    public void handleAddDevice(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddDeviceView.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();

        BaseController controller = loader.getController();
        try {
            controller.setModel(ModelsHandler.getInstance());
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("failed to set model", e));
        }
        controller.setup();

        stage.setScene(new Scene(root));
        stage.setTitle("WUAV Documentation Add device");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.showAndWait();
    }

    /**
     * Opens a filechooser and takes the users input and creates a local file that is used to upload the image to dropbox
     * @param actionEvent
     */
    public void handleAddImage(ActionEvent actionEvent) {
        String filePath = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add your image");

        FileChooser.ExtensionFilter imageExtensions = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg");

        fileChooser.getExtensionFilters().add(imageExtensions);

        file = fileChooser.showOpenDialog((Stage) btnReturn.getScene().getWindow());

        if(file != null) {
            String fileUriString = file.toURI().toString();

            if ((fileUriString.endsWith(".png") || fileUriString.endsWith(".jpg"))){
                filePath = file.getAbsolutePath();
                try {
                    file = getModelsHandler().getDocumentationModel().createLocalFile(file);
                } catch (Exception e) {
                    ExceptionHandler.displayError(new Throwable("failed to create new locale files", e));
                }
                projectImages.add(file);
                getModelsHandler().getDocumentationModel().getImagesToBeSaved().add(file);
            }

        }
    }

    /**
     * Upload all newly added images to the dropbox folder of the project
     * @param project
     */
    private void uploadImagesToBeSaved(Project project){
        if (!getModelsHandler().getDocumentationModel().getImagesToBeSaved().isEmpty()) {
            for (File f : getModelsHandler().getDocumentationModel().getImagesToBeSaved()) {
                if (f.getPath() != null) {
                    try {
                        getModelsHandler().getDocumentationModel().uploadFilesFromDropBox(f.getPath(), "/" + project.getProjectId() + "/" + f.getName());
                    } catch (DbxException e) {
                        ExceptionHandler.displayError(new DbxException("Failed to upload files",e));
                    }
                }
            }
            getModelsHandler().getDocumentationModel().getImagesToBeSaved().clear();
        }
    }

    /**
     * gets dropbox metadata that is used to get the images from the project that is opened
     */
    private void getAllDropBoxFilesForProject(){
        try {
            for (Metadata m: getModelsHandler().getDocumentationModel().readAllFilesFromDropBox()){
                if (m.getName().equals(""+opnedProject.getProjectId())){
                    if (opnedProject.getProjectId() >= 1) {
                        getModelsHandler().getDocumentationModel().readFilesFromDropBox(opnedProject.getProjectId());
                        getModelsHandler().getDocumentationModel().downloadProjectFilesFromDropBox(opnedProject.getProjectId());
                    }
                }
            }
        } catch (DbxException e) {
            ExceptionHandler.displayError(new DbxException("Failed to get all files fro the project", e));
        }
    }

    /**
     * When button is clicked checks weather to remove a device or image and then calls the relevant method.
     * @param actionEvent
     */
    public void handleRemove(ActionEvent actionEvent) {
        try {
            if (!(lvImages.getSelectionModel().getSelectedItem() == null) && lvImagesLastSelected){
                removeImage();
            }
            else if(!(lvDevices.getSelectionModel().getSelectedItem() == null) && !lvImagesLastSelected){
                deleteDevice();
            }
        } catch (DbxException e) {
            ExceptionHandler.displayError(new DbxException("failed to delete items",e));
        }
    }

    /**
     * Removes the image that was clicked on from the dropbox, and changes the imageview to another default image "WUAV.png"
     * @throws DbxException
     */
    private void removeImage() throws DbxException {
        if (lvImages.getSelectionModel().getSelectedItem().getClass() == File.class){
            File file1 = projectImages.get(projectImages.indexOf(lvImages.getSelectionModel().getSelectedItem()));
            if (getModelsHandler().getDocumentationModel().getImagesToBeSaved().contains(lvImages.getSelectionModel().getSelectedItem()))
                getModelsHandler().getDocumentationModel().getImagesToBeSaved().remove(lvImages.getSelectionModel().getSelectedItem());
            else {
                getModelsHandler().getDocumentationModel().deleteFilesFromDropBox("/" + opnedProject.getProjectId() + "/" + file1.getName());
                projectImages.remove(lvImages.getSelectionModel().getSelectedItem());
                if (getModelsHandler().getDocumentationModel().getImagesObservableList().contains(lvImages.getSelectionModel().getSelectedItem()))
                    getModelsHandler().getDocumentationModel().getImagesObservableList().remove(lvImages.getSelectionModel().getSelectedItem());
            }
            if (imImageView.getImage().getUrl().equals(file1.getAbsolutePath())) {
                Image image = new Image("GUI/Images/WUAV.png");
                imImageView.setImage(image);
            }
        }
    }

    /**
     * Sets the imageview to the image that was clicked.
     * @param mouseEvent
     */
    public void handleImageClicked(MouseEvent mouseEvent) {
        lvImagesLastSelected = true;
        if (lvImages.getSelectionModel().getSelectedItem() != null){
            if (lvImages.getSelectionModel().getSelectedItem().getClass() == File.class){
                File file1 = projectImages.get(projectImages.indexOf(lvImages.getSelectionModel().getSelectedItem()));
                Image image = new Image(file1.getAbsolutePath());
                imImageView.setImage(image);
            }
        }
    }

    /**
     * Removes the selected device from the device listview and adds it to a list of devices to be deleted.
     */
    private void deleteDevice() {
        Device device = (Device) lvDevices.getSelectionModel().getSelectedItem();
        lvDevices.getSelectionModel().clearSelection();
        devicestoDelete.add(device.getDeviceId());
        getModelsHandler().getDocumentationModel().getDevicesObservableList().remove(device);
        System.out.println(devicestoDelete.toString());
    }

    public void handleDevicesClicked(MouseEvent mouseEvent) {
        lvImagesLastSelected = false;
    }


    /**
     * Sets up the icons for the buttons.
     */
    private void setupButtonIcons()
    {
        btnSave.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-save-80.png")));
        btnSaveToDevice.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-save-80.png")));
        btnAddImage.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-images-64.png")));
        btnSend.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-send-80.png")));
        btnOpenPaint.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-paint-brush-80.png")));
        btnRemove.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-empty-trash-80.png")));
        btnReturn.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-return-80.png")));
        btnRemoveTechnician.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-empty-trash-80.png")));
        btnAddDevice.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-add-80.png")));
        btnAssignTech.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-add-80.png")));
        btnScreenSize.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-full-screen-80.png")));
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