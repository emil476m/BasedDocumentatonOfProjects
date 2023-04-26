package GUI.Models;

import BE.UserTypes.User;
import BLL.Interfaces.ILoginManager;
import BLL.Managers.LoginManager;

import java.io.IOException;
import java.sql.SQLException;


public class LoginModel {
    private User user;
    private ILoginManager loginManager;
    public LoginModel() throws IOException {
        loginManager = new LoginManager();
    }

    public void loginAction(String username, String password) throws SQLException {
       user = loginManager.loginUser(username,password);
    }

    public User getUser() {
        return user;
    }
}
