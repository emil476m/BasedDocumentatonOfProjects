package BLL.Managers;

import BE.UserTypes.User;
import BLL.Interfaces.ILoginManager;
import DAL.DB.LoginDAO_DB;
import DAL.Interface.ILoginDAO;

import java.io.IOException;
import java.sql.SQLException;

public class LoginManager implements ILoginManager {
    ILoginDAO loginDAO;

    public LoginManager() throws IOException {
        loginDAO = new LoginDAO_DB();
    }
    @Override
    public User loginUser(String username, String password) throws SQLException {
        return loginDAO.loginUser(username, password);
    }
}
