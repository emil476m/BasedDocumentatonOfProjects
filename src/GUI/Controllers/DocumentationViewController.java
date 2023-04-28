package GUI.Controllers;
import BE.CostumerType;
import BE.Project;
import BE.UserTypes.CEO;
import BE.UserTypes.ProjectManager;
import BE.UserTypes.SalesPerson;
import BE.UserTypes.Technician;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentationViewController extends BaseController{

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

    private Project openedProject;

    @Override
    public void setup() {
        checkAccesslevel();
        costumerTypes = new ArrayList<>();
        textFields = new ArrayList<>();
        addTextFields();
        checkCostumertype();
        try{
        generateMenuItems();}
        catch (Exception e)
        {
            e.printStackTrace();
            ExceptionHandler.displayError(new RuntimeException("failed to set up window", e));
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
            if(txtCostumerType.getText().equals("Consumer"))
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

    public void handleSendToProjectManager(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
        if(openedProject == null)
        {
            if(checkTextField() && !txtaComments.getText().isEmpty() && txtLocation.isDisable())
            {
                String costumerName = txtCostumerName.getText();
                String location = "";
                LocalDate date = dpDatePicker.getValue();
                String address = txtAddress.getText();
                String zipcode = txtZipCode.getText();
                int creator = getModelsHandler().getLoginModel().getUser().getUserID();
                int costumerType = findCostumertype(txtCostumerType.getText());
                boolean isDeleted = false;
                String comment = txtaComments.getText();
            Project project = new Project(costumerName,date,location,comment,creator,isDeleted,creator,true,date,costumerType,address,zipcode);
                try {
                    getModelsHandler().getDocumentationModel().saveNewProject(project, lvDevices.getItems());
                    getModelsHandler().getTechnicianModel().getProjectsObservableList().add(project);
                    getModelsHandler().getTechnicianModel().getMyProjectsObservableList().add(project);
                } catch (SQLException e) {
                    e.printStackTrace();
                    ExceptionHandler.displayError(new SQLException("Failed to save project in Database"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void handleOpenPaint(ActionEvent actionEvent) throws IOException {
        Runtime.getRuntime().exec("mspaint.exe");
    }

    public void handleAssignTech(ActionEvent actionEvent) {
    }

    public void setOpenedProject(Project project)
    {
        openedProject = project;
    }

    private int findCostumertype(String type)
    {
        for (CostumerType c:costumerTypes)
        {
            if(c.getType().equals(type))
            {
                return c.getId();
            }
            else
            {
                return 0;
            }
        }
        return 0;
    }
}
