package GUI.Controllers;
import BE.UserTypes.*;
import GUI.Util.AlertOpener;
import GUI.Util.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInfoController extends BaseController {
    @FXML
    private MenuItem menuItemProjectManager;
    @FXML
    private MenuItem menuItemTechnician;
    @FXML
    private MenuItem menuItemSalesPerson;
    @FXML
    private MenuItem menuItemCEO;
    @FXML
    private Text txtUserId;
    @FXML
    private Menu mAccessLevel;
    @FXML
    private BorderPane borderPaneUserInfo;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnDeleteUser;
    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnConfirm;
    @FXML
    private TextField txtfUsername;
    @FXML
    private TextField txtfPassword;
    @FXML
    private TextField txtfMail;
    @FXML
    private TextField txtfName;
    @FXML
    private TextField txtfUserId;
    @FXML
    private TextField txtfAcceslevel;

    private User selectedUser;

    @Override
    public void setup() {
        setUpUserInfo();
        setupButtonIcons();
    }

    /**
     * sets CreateUser.
     * @param user
     */
    public void setCreateUser(User user){
        this.selectedUser = user;
    }

    /**
     * Closes the window.
     */
    public void handleExit() {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    /**
     * Check if you should edit.
     * @return
     */
    private boolean checkShouldEdit(){
        if (selectedUser != null)
            return true;
            else
                return false;
    }

    private void setUpUserInfo(){
        setEditAbleUser(false);
        if (!checkShouldEdit()){
            btnDeleteUser.setDisable(true);
            btnDeleteUser.setVisible(false);
            btnEditUser.setDisable(true);
            btnEditUser.setVisible(false);
            txtUserId.setVisible(false);
            txtfUserId.setVisible(false);
            setEditAbleUser(true);
        }
        else if (checkShouldEdit()) {
            setUserInfoInTxtf();
        }

    }

    /**
     * Sets the textfields to match the selected user.
     */
    private void setUserInfoInTxtf(){
        txtfMail.setText(selectedUser.getMail());
        txtfName.setText(selectedUser.getName());
        txtfPassword.setText(selectedUser.getPassWord());
        txtfUserId.setText(selectedUser.getUserID()+"");
        txtfUsername.setText(selectedUser.getUserName());
        txtfAcceslevel.setText(selectedUser.getClass().getSimpleName());
    }

    /**
     * Cehecks if you can edit a user or not.
     * @param ableToEdit
     */
    private void setEditAbleUser(boolean ableToEdit){
        if (ableToEdit){
            txtfAcceslevel.setEditable(false);
            txtfUserId.setEditable(false);

            mAccessLevel.setDisable(false);
            txtfUsername.setEditable(true);
            txtfPassword.setEditable(true);
            txtfName.setEditable(true);
            txtfMail.setEditable(true);

        } else if (!ableToEdit) {
            mAccessLevel.setDisable(true);
            txtfAcceslevel.setEditable(false);
            txtfUserId.setEditable(false);
            txtfUsername.setEditable(false);
            txtfPassword.setEditable(false);
            txtfName.setEditable(false);
            txtfMail.setEditable(false);
        }

    }

    /**
     * Deletes the selected user.
     * @param actionEvent
     */
    public void handleDeleteUser(ActionEvent actionEvent) {
        if (selectedUser != null){
            try {
                getModelsHandler().getCeoModel().deleteUser(selectedUser);
            } catch (Exception e) {
                ExceptionHandler.displayError(new Exception("Failed to delete user",e));
            }
        }
        handleExit();
    }

    /**
     * sets the EditAbleUser to true.
     * @param actionEvent
     */
    public void handleEditUser(ActionEvent actionEvent) {
        setEditAbleUser(true);
    }

    /**
     * Creates or updates a user.
     * @param actionEvent
     */
    public void handleConfirm(ActionEvent actionEvent) {
        if (checkTextFieldsNotNull()) {
            if (!checkShouldEdit()) {
                createUser();
            } else if (checkShouldEdit()) {
                editUser();
            }
        }
        else
            AlertOpener.validationError("Missing Input!");
    }

    /**
     * Creates a user.
     */
    private void createUser(){

        String passWord = txtfPassword.getText();
        String userName = txtfUsername.getText();
        String mail = txtfMail.getText();
        String name = txtfName.getText();

        User newUser = null;

        try {
            if (getModelsHandler().getCeoModel().checkUserName(userName)) {
                if (txtfAcceslevel.getText().equals(CEO.class.getSimpleName())) {
                    newUser = new CEO(passWord, userName, mail, name, 1);
                } else if (txtfAcceslevel.getText().equals(ProjectManager.class.getSimpleName())) {
                    newUser = new ProjectManager(passWord, userName, mail, name, 2);
                }
                else if (txtfAcceslevel.getText().equals(Technician.class.getSimpleName())) {
                    newUser = new Technician(passWord, userName, mail, name, 3);
                }
                else if (txtfAcceslevel.getText().equals(SalesPerson.class.getSimpleName())) {
                    newUser = new SalesPerson(passWord, userName, mail, name, 4);
                }
                if (newUser != null) {
                    getModelsHandler().getCeoModel().createUser(newUser);
                    handleExit();
                }
            }
            else {
                AlertOpener.validationError("Username is already taken!");
            }
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to create user please try again",e));
        }
    }

    /**
     * Updates a user.
     */
    private void editUser(){
        String passWord = txtfPassword.getText();
        String userName = txtfUsername.getText();
        String mail = txtfMail.getText();
        String name = txtfName.getText();
        int userId = Integer.parseInt(txtfUserId.getText());

        User user = null;

        try {
            if (txtfAcceslevel.getText().equals(CEO.class.getSimpleName())) {
                user = new CEO(userId, passWord, userName, mail, name, 1, false);
            } else if (txtfAcceslevel.getText().equals(ProjectManager.class.getSimpleName())) {
                user = new ProjectManager(userId, passWord, userName, mail, name, 2, false);
            }
            else if (txtfAcceslevel.getText().equals(Technician.class.getSimpleName())) {
                user = new Technician(userId, passWord, userName, mail, name, 3, false);
            }
            else if (txtfAcceslevel.getText().equals(SalesPerson.class.getSimpleName())) {
                user = new SalesPerson(userId, passWord, userName, mail, name, 4, false);
            }
            if (checkIfTwoUserAreSame(user)) {
                handleExit();
            }
            else {
                if (checkUsernameIsSame(user)){
                    if (user.getClass().getSimpleName().equals(selectedUser.getClass().getSimpleName())) {
                        getModelsHandler().getCeoModel().updateUser(user, selectedUser);
                        handleExit();
                    }else {
                        getModelsHandler().getCeoModel().updateUsernameOrClass(user, selectedUser);
                        handleExit();
                    }
                } else if (getModelsHandler().getCeoModel().checkUserName(user.getUserName())) {
                    if (user.getClass().getSimpleName().equals(selectedUser.getClass().getSimpleName())) {
                        getModelsHandler().getCeoModel().updateUser(user, selectedUser);
                        handleExit();
                    }
                    else {
                        getModelsHandler().getCeoModel().updateUsernameOrClass(user, selectedUser);
                        handleExit();
                    }
                }
                else
                    AlertOpener.validationError("username is already taken!");
            }
        } catch (Exception e) {
            ExceptionHandler.displayError(new Exception("Failed to edit user please try again"));
        }
    }

    /**
     * returns true if the two users are the same.
     * @param user1
     * @return
     */
    private boolean checkIfTwoUserAreSame(User user1){
        if (user1.getUserID() == selectedUser.getUserID()){
            if (user1.getClass().getSimpleName().equals(selectedUser.getClass().getSimpleName()))
                if (user1.getName().equals(selectedUser.getName()))
                    if (user1.getUserName().equals(selectedUser.getUserName()))
                        if (user1.getPassWord().equals(selectedUser.getPassWord()))
                            if (user1.getMail().equals(selectedUser.getMail()))
                                return true;
        }
        return false;
    }

    /**
     * Returns true if the 2 usernames are the same else returns false
     * @param
     * @return
     */
   private boolean checkUsernameIsSame(User user1){
        if (user1.getUserName().equals(selectedUser.getUserName())){
            return true;
        }
        else
            return false;
    }

    /**
     * Checks if the textfields are not null.
     * @return
     */
    private boolean checkTextFieldsNotNull(){
        if (checktxtfName() && checktxtfUserName() && checktxtfPassword()){
            if (checktxtfMail() && checktxtfAccessLevel())
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * Checks the name textfield.
     * @return
     */
    private boolean checktxtfName(){
        if (!txtfName.getText().isEmpty() && txtfName.getText() != null) return true;
        else
            return false;
    }

    /**
     * Checks the username textfield.
     * @return
     */
    private boolean checktxtfUserName(){
        if (!txtfUsername.getText().isEmpty() && txtfUsername.getText() != null)
            return true;
        else
            return false;
    }

    /**
     * Checks the password textfield.
     * @return
     */
    private boolean checktxtfPassword(){
        if (!txtfPassword.getText().isEmpty() && txtfPassword.getText() != null)
            return true;
        else
            return false;
    }

    /**
     * Checks the mail textfield.
     * @return
     */
    private boolean checktxtfMail(){
        if (!txtfMail.getText().isEmpty() && txtfMail.getText() != null)
            return true;
        else
            return false;
    }

    /**
     * Checks the accessLevel textfield.
     * @return
     */
    private boolean checktxtfAccessLevel(){
        if (!txtfAcceslevel.getText().isEmpty() && txtfAcceslevel.getText() != null)
            return true;
        else
            return false;
    }

    /**
     * Makes the window movable.
     * @param mouseEvent
     */
    @FXML
    private void dragScreen(MouseEvent mouseEvent){
        borderPaneUserInfo.setOnMousePressed(pressEvent -> {
            borderPaneUserInfo.setOnMouseDragged(dragEvent -> {
                borderPaneUserInfo.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                borderPaneUserInfo.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
    }

    /**
     * Sets AccessLevel textfield text.
     * @param actionEvent
     */
    public void handleMenuItemCEO(ActionEvent actionEvent) {
        txtfAcceslevel.setText(menuItemCEO.getText());
    }

    /**
     * Sets AccessLevel textfield text.
     * @param actionEvent
     */
    public void handleMenuItemProjectManager(ActionEvent actionEvent) {
        txtfAcceslevel.setText(menuItemProjectManager.getText());
    }

    /**
     * Sets AccessLevel textfield text.
     * @param actionEvent
     */
    public void handleMenuItemTechnician(ActionEvent actionEvent) {
        txtfAcceslevel.setText(menuItemTechnician.getText());
    }

    /**
     * Sets AccessLevel textfield text.
     * @param actionEvent
     */
    public void handleMenuItemSalesPerson(ActionEvent actionEvent) {
        txtfAcceslevel.setText(menuItemSalesPerson.getText());
    }

    /**
     * Sets up the button icons.
     */
    private void setupButtonIcons()
    {
        btnConfirm.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-checkmark-80.png")));
        btnDeleteUser.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-empty-trash-80.png")));
        btnExit.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-logout-80.png")));
        btnEditUser.setGraphic(new ImageView(new Image("/GUI/Images/ButtonIcons/icons8-edit-80.png")));
    }
}
