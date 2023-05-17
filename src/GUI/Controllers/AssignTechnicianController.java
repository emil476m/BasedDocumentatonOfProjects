package GUI.Controllers;

import BE.Project;
import BE.UserTypes.Technician;
import BE.UserTypes.User;
import GUI.Util.ExceptionHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AssignTechnicianController extends BaseController{
    @FXML
    private TableView<User> tbvUserList;
    @FXML
    private TableColumn<User, Integer> tbcUserListId;
    @FXML
    private TableColumn<User, String> tbcUserListName;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnConfirm;
    @FXML
    private BorderPane borderPaneEventCoordinatorList;

    private Project openedProject;

    private ObservableList<User> allTechnicians;

    /**
     * Sets the openedProject.
     * @param openedProject
     */
    public void setOpenedEvent(Project openedProject) {
        this.openedProject = openedProject;
    }

    @Override
    public void setup() {
        getAndShowTechnicians();
        setupButtonIcons();
        multiSelect();
        dragScreen();
    }

    public AssignTechnicianController(){
        allTechnicians = FXCollections.observableArrayList();
    }

    /**
     * Gets and displays Technicians.
     */
    private void getAndShowTechnicians(){
        allTechnicians.addAll(getModelsHandler().getCeoModel().getUserOnCurrentProject());

        for (User u:getModelsHandler().getCeoModel().getAllUsersList()){
            if (u.getClass().getSimpleName() == Technician.class.getSimpleName()){
                if (!allTechnicians.contains(u))
                    allTechnicians.add(u);
                else
                    allTechnicians.remove(u);
            }
        }
        tbvUserList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbvUserList.setItems(allTechnicians);
        tbcUserListName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcUserListId.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Makes the window movable.
     */
    public void dragScreen() {
        borderPaneEventCoordinatorList.setOnMousePressed(pressEvent -> {
            borderPaneEventCoordinatorList.setOnMouseDragged(dragEvent -> {
                borderPaneEventCoordinatorList.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                borderPaneEventCoordinatorList.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }

    /**
     * Closes the window.
     */
    public void handleExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    /**
     * Adds the selected Technicians to the project.
     * @param actionEvent
     */
    public void handleConfirm(ActionEvent actionEvent) {
        try {
            List<User> usersToBeAdded = new ArrayList<>();
            for (User u : tbvUserList.getSelectionModel().getSelectedItems()) {
                usersToBeAdded.add(u);
            }
            getModelsHandler().getCeoModel().addUsersWorkingOnProject(usersToBeAdded, openedProject);
            handleExit();
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to do confirm action", e));
        }

    }

    /**
     * Makes the tableview able to multiselect without holding control,
     * by using node instances and mouse events.
     */
    private void multiSelect(){
        tbvUserList.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();

            // Gets the row that was clicked.
            while (node != null && node != tbvUserList && !(node instanceof TableRow)) {
                node = node.getParent();
            }

            // Don't use standard event, if node is a table row.
            if (node instanceof TableRow) {
                // Prevent further handling
                evt.consume();

                TableRow row = (TableRow) node;
                TableView tv = row.getTableView();

                // Focus the tableview
                tv.requestFocus();

                if (!row.isEmpty()) {
                    // Handle selection for non-empty nodes
                    int index = row.getIndex();

                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });
    }

    private void setupButtonIcons()
    {
        btnConfirm.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-checkmark-80.png")));
        btnExit.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-logout-80.png")));
    }
}
