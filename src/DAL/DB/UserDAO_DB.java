package DAL.DB;

import BE.UserTypes.*;
import DAL.DBUtil.BCrypt;
import DAL.DatabaseConnector;
import DAL.Interface.IUserDAO;

import java.io.IOException;
import java.sql.*;
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

    @Override
    public User createUser(User user) throws Exception{
        String sql = "INSERT INTO [User] (PassWord, UserName, Mail, Name, UserType, IsDeleted) VALUES (?,?,?,?,?,?);";
        String salt = BCrypt.gensalt(12);
        String hashPw = BCrypt.hashpw(user.getPassWord(),salt);
        try(Connection connection = dbConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){


            statement.setString(1, hashPw);
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getMail());
            statement.setString(4, user.getName());
            statement.setInt(5, user.getUserType());
            statement.setString(6, false+"");

            statement.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            User newUser = null;
            int userTypes = user.getUserType();
            if (userTypes == 1){
                newUser = new CEO(id, user.getPassWord(), user.getUserName(), user.getMail(), user.getName(), userTypes, user.getDeleted());
            } else if (userTypes == 2) {
                newUser = new ProjectManager(id, user.getPassWord(), user.getUserName(), user.getMail(), user.getName(), userTypes, user.getDeleted());
            }
            else if (userTypes == 3){
                newUser = new Technician(id, user.getPassWord(), user.getUserName(), user.getMail(), user.getName(), userTypes, user.getDeleted());
            }
            else if (userTypes == 4){
                newUser = new SalesPerson(id, user.getPassWord(), user.getUserName(), user.getMail(), user.getName(), userTypes, user.getDeleted());
            }
            else
                throw new Exception("Failed to find usertype");

            return newUser;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create User", e);
        }
    }

    @Override
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE [User] SET UserName = ?, PassWord = ?, Mail = ?, Name = ?, UserType = ?, IsDeleted = ? WHERE Id = ?;";
        String salt = BCrypt.gensalt(12);
        String hashPw = BCrypt.hashpw(user.getPassWord(),salt);
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, hashPw);
            statement.setString(3, user.getMail());
            statement.setString(4, user.getName());
            statement.setInt(5, user.getUserType());
            statement.setString(6, user.getDeleted().toString());
            statement.setInt(7, user.getUserID());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update or delete User", e);
        }
    }

    @Override
    public boolean checkUserName(String userName) throws Exception {
        String sql = "SELECT UserName FROM [User] WHERE UserName = ?;";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userName1 = resultSet.getString("UserName");
                if (userName1.equals(userName))
                    return false;
            }
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to check userName", e);
        }
    }

    @Override
    public User getUserFromId(int userId) throws Exception {
        String sql = "SELECT * FROM [User] WHERE Id = ?;";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String passWord = resultSet.getString("PassWord");
                String userName = resultSet.getString("UserName");
                String mail = resultSet.getString("Mail");
                String name = resultSet.getString("Name");
                int userTypes = resultSet.getInt("UserType");
                String isDeleted = resultSet.getString("IsDeleted");

                User newUser = null;
                if (userTypes == 1){
                    return newUser = new CEO(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted));
                } else if (userTypes == 2) {
                    return newUser = new ProjectManager(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted));
                }
                else if (userTypes == 3){
                    return newUser = new Technician(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted));
                }
                else if (userTypes == 4){
                   return newUser = new SalesPerson(id, passWord, userName, mail, name, userTypes, Boolean.valueOf(isDeleted));
                }
                else
                    throw new Exception("Failed to find usertype");
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to find user", e);
        }
    }
}
