package GUI.Controllers;
import BE.CostumerType;
import BE.Project;
import BE.UserTypes.*;
import GUI.Models.ModelsHandler;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentationViewController extends BaseController{

    public ListView<User> lvTechniciansOnProject;
    public Button btnRemoveTechnician;
    @FXML
    private Button btnAssignTech,btnOpenPaint,btnSave,btnSend,btnSaveToDevice,btnAddImage,btnAddDevice,btnRemove,btnReturn;
    @FXML
    private TextField txtCostumerName,txtAddress,txtZipCode,txtCostumerType,txtLocation;
    @FXML
    private MenuButton menuTypes;
    @FXML
    private TextArea txtaComments;
    @FXML
    private DatePicker dpDatePicker;
    @FXML
    private ListView lvDevices;

    private List<CostumerType> costumerTypes;

    private List<TextField> textFields;

    private Project opnedProject;



    @Override
    public void setup() {
        checkAccesslevel();
        costumerTypes = new ArrayList<>();
        textFields = new ArrayList<>();
        addTextFields();
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
                setupListViews();
                setUpTechniciansRemoveAddAndView();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up window", e));
        }
    }

    private void setupListViews() {
        try {
            getModelsHandler().getDocumentationModel().getAllDevicesForProject(opnedProject);
            lvDevices.setItems(getModelsHandler().getDocumentationModel().getDevices());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void addTextFields()
    {
        textFields.add(txtCostumerName);
        textFields.add(txtAddress);
        textFields.add(txtZipCode);
        textFields.add(txtCostumerType);
        textFields.add(txtLocation);
    }

    private void checkAccesslevel()
    {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            btnAssignTech.setDisable(true);
            btnAssignTech.setVisible(false);
            txtCostumerType.setEditable(false);
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
            }
        } else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(SalesPerson.class.getSimpleName())){
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
        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName())||getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName())){
                txtCostumerType.setEditable(false);
                btnSend.setText("Send to Technician");
        }
    }


    private void generateMenuItems() throws Exception {
        getModelsHandler().getDocumentationModel().getAllCostumerTypes();
        costumerTypes = getModelsHandler().getDocumentationModel().getCostumerTypes();
        for (CostumerType c:costumerTypes)
        {
            menuTypes.getItems().add(createMenuItem(c));
        }
    }

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

    private Boolean checkTextField()
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

    public void handleReturn(ActionEvent actionEvent) {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(SalesPerson.class.getSimpleName()))
        {
            getModelsHandler().getDocumentationModel().getDevices().removeAll();
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
        }
        else
        {
        if(AlertOpener.confirm("Are you sure?","remember to save before you exit or your changes will be lost"))
        {
            Stage stage = (Stage) btnReturn.getScene().getWindow();
            stage.close();
        }
        }
    }

    public void handleSaveToDevice(ActionEvent actionEvent) {
    }

    public void handleSendToPMOrTech(ActionEvent actionEvent) {
        createNewProject();
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            sendToPMOrTech(false);
        }
        else if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()) || getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            sendToPMOrTech(true);
        }

    }

    private void sendToPMOrTech(boolean canEdit)
    {
        if(opnedProject != null)
        {
            Project project = opnedProject;
            Project oldProject = project;
            getModelsHandler().getTechnicianModel().getProjectsObservableList().remove(project);
            project.setCanBeEditedByTech(canEdit);
            if(getModelsHandler().getTechnicianModel().getMyProjectsObservableList().contains(oldProject))
            {
                getModelsHandler().getTechnicianModel().getMyProjectsObservableList().remove(oldProject);
                getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
            }

            try {
                getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);
                getModelsHandler().getDocumentationModel().sentToProjectManager(project);
            } catch (Exception e) {
                ExceptionHandler.displayError(new RuntimeException("Failed to send installation to project manager", e));
            }
        }
    }

    public void handleSave(ActionEvent actionEvent)
    {
        if(opnedProject != null)
        {
            saveProject();
        }
        else
        {
            createNewProject();
        }
    }

    private void saveProject()
    {
        try {
            String location = "";
            int costumerType = 0;
            String costumerName = txtCostumerName.getText();
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
            Project project = new Project(opnedProject.getProjectId(), costumerName , date, location, comment, opnedProject.getProjectCreatorId(), opnedProject.getProjectIsDeleted(), opnedProject.getLastEditedBy(), opnedProject.getCanBeEditedByTech(), LocalDate.now(), costumerType, address, zipCode);
            getModelsHandler().getDocumentationModel().saveproject(project, lvDevices.getItems());
            updateTableviews(project, opnedProject);
            AlertOpener.confirm("Has been saved", "Your changes have been saved.");
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to update project", e));
        }
    }

    private void updateTableviews(Project project, Project opnedProject)
    {
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(CEO.class.getSimpleName()))
        {
            getModelsHandler().getCeoModel().getProjectsObservableList().remove(opnedProject);
            getModelsHandler().getCeoModel().getProjectsObservableList().add(project);
        }
        else if (getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(Technician.class.getSimpleName()))
        {
            if(getModelsHandler().getTechnicianModel().getMyProjectsObservableList().contains(project))
            {
                getModelsHandler().getTechnicianModel().getMyProjectsObservableList().remove(opnedProject);
                getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
            }
            getModelsHandler().getTechnicianModel().getProjectsObservableList().remove(opnedProject);
            getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);
        }
        if(getModelsHandler().getLoginModel().getUser().getClass().getSimpleName().equals(ProjectManager.class.getSimpleName()))
        {
            getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().remove(opnedProject);
            getModelsHandler().getProjectManagerModel().getAllProjectsObservablelist().add(project);
        }
    }

    private void createNewProject()
    {
        if(checkTextField() && !txtaComments.getText().isEmpty())
        {
            String costumerName = txtCostumerName.getText();
            String location = "";
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
            Project project = new Project(costumerName, date, location, comment, creator, isDeleted, creator, true, date, costumerType, address, zipcode);
            try {
                getModelsHandler().getDocumentationModel().saveNewProject(project, lvDevices.getItems());
                getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);
                getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
                AlertOpener.confirm("Has been saved", "Your changes have been saved.");
            } catch (SQLException e) {
                e.printStackTrace();
                ExceptionHandler.displayError(new SQLException("Failed to save project in Database"));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void handleOpenPaint(ActionEvent actionEvent) throws IOException {
        Runtime.getRuntime().exec("mspaint.exe");
    }

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

    private void setTextFields()
    {
        Project project = opnedProject;
        txtCostumerType.setText(findCostumerTypeFromId(project.getCostumerType()));
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

    public void setOpenedProject(Project project)
    {
        opnedProject = project;
    }

    private void setUpTechniciansRemoveAddAndView(){
        try {
            if (opnedProject != null)
                getModelsHandler().getCeoModel().getUserOnCurrentProject().clear();
                getModelsHandler().getCeoModel().getUserOnCurrentProject().addAll(getModelsHandler().getCeoModel().getUsersWorkingOnProject(opnedProject));
        } catch (Exception e) {
            ExceptionHandler.displayError(new RuntimeException("Failed to setup technician List", e));
        }

        lvTechniciansOnProject.setItems(getModelsHandler().getCeoModel().getUserOnCurrentProject());
    }

    public void handleRemoveTechnician(ActionEvent actionEvent) {
        try {
            if (lvTechniciansOnProject.getSelectionModel().getSelectedItem() != null) {
                getModelsHandler().getCeoModel().deleteFromWorkingOnProject(lvTechniciansOnProject.getSelectionModel().getSelectedItem(), opnedProject);
            }
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to remove technician from List", e));
        }
    }

    public void handleAddDevice(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Views/AddDeviceView.fxml"));

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
        stage.setTitle("WUAV Documentation Add device");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/GUI/Images/WUAV.png"));
        stage.showAndWait();
    }
}
