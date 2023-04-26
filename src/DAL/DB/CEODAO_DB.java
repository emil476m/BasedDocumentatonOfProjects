package DAL.DB;

import BE.Project;
import BE.UserTypes.*;
import DAL.DatabaseConnector;
import DAL.Interface.ICEODAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CEODAO_DB implements ICEODAO {

    private DatabaseConnector dbConnector;

    public CEODAO_DB() throws IOException {
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<Project> getAllProjects() throws Exception {
        String sql = "SELECT * FROM [Project] WHERE IsDeleted = 'false';";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Project> projectList = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String costumerName = resultSet.getString("CostumerName");
                LocalDate projectDate = resultSet.getDate("ProjectDate").toLocalDate();
                String projectLocation = resultSet.getString("ProjectLocation");
                String projectDescription = resultSet.getString("ProjectDescription");
                int projectCreator = resultSet.getInt("ProjectCreator");
                String isDeleted = resultSet.getString("IsDeleted");


                Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted));
                projectList.add(project);

            }
            return projectList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve Users", e);
        }
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
