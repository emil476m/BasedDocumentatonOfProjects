package BLL.Interfaces;

import BE.UserTypes.User;

import java.sql.SQLException;

public interface ILoginManager {

    User loginUser(String username, String Password) throws SQLException;
}
