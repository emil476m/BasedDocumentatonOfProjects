package DAL.DB;

import BE.UserTypes.*;
import DAL.DatabaseConnector;
import DAL.Interface.IUserDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO_DB implements IUserDAO {
    private DatabaseConnector dbConnector;

    public UserDAO_DB() throws IOException {
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        String sql = "SELECT * FROM [User] WHERE IsDeleted = 'false';";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<User> userList = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String passWord = resultSet.getString("PassWord");
                String userName = resultSet.getString("UserName");
                String mail = resultSet.getString("Mail");
                String name = resultSet.getString("Name");
                int userTypes = resultSet.getInt("UserType");
                String isDeleted = resultSet.getString("IsDeleted");

                if (userTypes == 1){
                    userList.add(new CEO(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted)));
                } else if (userTypes == 2) {
                    userList.add(new ProjectManager(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted)));
                }
                else if (userTypes == 3){
                    userList.add(new Technician(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted)));
                }
                else if (userTypes == 4){
                    userList.add(new SalesPerson(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted)));
                }
                else
                    throw new Exception("Failed to find usertype");
            }
            return userList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve Users", e);
        }
    }
}
