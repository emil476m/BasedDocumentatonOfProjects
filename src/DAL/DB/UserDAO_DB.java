package DAL.DB;

import BE.Project;
import BE.UserTypes.*;
import DAL.DBUtil.BCrypt;
import DAL.DatabaseConnector;
import DAL.Interface.IUserDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAO_DB implements IUserDAO {
    private DatabaseConnector dbConnector;
    private static final String PROP_FILE = ".idea/Config/DataBase.Settings";

    public UserDAO_DB() throws IOException {
        dbConnector = new DatabaseConnector();
    }

    /**
     * Gets all users, that are not soft deleted.
     * @return returns a list of all users it found.
     * @throws Exception
     */
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

    /**
     * creates a new user in the database
     * @param user
     * @return returns a new user object with a userId.
     * @throws Exception
     */
    @Override
    public User createUser(User user) throws Exception{
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(new File(PROP_FILE)));

        String saltIteration = databaseProperties.getProperty("SaltIteration");


        String sql = "INSERT INTO [User] (PassWord, UserName, Mail, Name, UserType, IsDeleted) VALUES (?,?,?,?,?,?);";
        String salt = BCrypt.gensalt(Integer.parseInt(saltIteration));
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

    /**
     * updates a users data in the database, is also used to soft delete users.
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUser(User user) throws Exception {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(new File(PROP_FILE)));

        String saltIteration = databaseProperties.getProperty("SaltIteration");

        String sql = "UPDATE [User] SET UserName = ?, PassWord = ?, Mail = ?, Name = ?, UserType = ?, IsDeleted = ? WHERE Id = ?;";
        String salt = BCrypt.gensalt(Integer.parseInt(saltIteration));
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

    /**
     * checks if a username already exists in the database and returns false if it does, and true if it does not.
     * @param userName
     * @return returns false if username exist in database and true if it does not exist.
     * @throws Exception
     */
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

    /**
     * Uses a userId to find and return a specific user object.
     * @param userId
     * @return
     * @throws Exception
     */
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

    /**
     * Get all users(technicians) with relations to a specific project.
     * @param project
     * @return
     * @throws Exception
     */
    @Override
    public List<Integer> getUsersWorkingOnProject(Project project) throws Exception {
        String sql = "SELECT * FROM WorkingOnProject WHERE ProjectId = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            List<Integer> userIdList = new ArrayList<>();

            statement.setInt(1, project.getProjectId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("UserId");

                userIdList.add(id);
            }
            return userIdList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve User relations", e);
        }
    }

    /**
     * Creates relations between a list of users(Technicians) and a project.
     * @param userListToBeAdded
     * @param project
     * @throws Exception
     */
    @Override
    public void addUsersToWorkingOnProject(List<User> userListToBeAdded, Project project) throws Exception {
        String sql = "INSERT INTO [WorkingOnProject] (ProjectId, UserId) VALUES (?,?);";
        try(Connection connection = dbConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            for (User u: userListToBeAdded){
                statement.setInt(1, project.getProjectId());
                statement.setInt(2, u.getUserID());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create User to Project relation", e);
        }
    }

    /**
     * Deletes the relation between a User(technician) and a project in the database
     * @param user
     * @param project
     * @throws Exception
     */
    @Override
    public void deleteFromWorkingOnProject(User user, Project project) throws Exception {
        String sql = "DELETE FROM WorkingOnProject WHERE UserId = ? AND ProjectId = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserID());
            statement.setInt(2, project.getProjectId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to remove " + user.getClass().getSimpleName() + " from relation table", e);
        }
    }
}
