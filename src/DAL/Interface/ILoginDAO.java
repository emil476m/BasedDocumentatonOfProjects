package DAL.Interface;

import BE.UserTypes.User;

import java.sql.SQLException;

public interface ILoginDAO {

    User loginUser(String username, String password) throws SQLException;
}
