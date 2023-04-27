package DAL.DB;

import BE.Project;
import BE.UserTypes.User;
import DAL.DatabaseConnector;
import DAL.Interface.IProjectDAO;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO_DB implements IProjectDAO {
    private DatabaseConnector dbConnector;

    public ProjectDAO_DB() throws IOException {
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
                int editedBy = resultSet.getInt("LastEditedBy");
                String canBeEditedByTech = resultSet.getString("CanBeEditedByTech");
                LocalDate lastEdited = resultSet.getDate("LastEdited").toLocalDate();
                int costumerType = resultSet.getInt("CostumerType");

                Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType);
                projectList.add(project);

            }
            return projectList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve all installations", e);
        }
    }

    @Override
    public List<Project> getMyProjects(User user) throws Exception {
        String sql = "SELECT * From WorkingOnProject \n" +
                "INNER JOIN Project on WorkingOnProject.ProjectId = Project.Id\n" +
                "WHERE WorkingOnProject.UserId=?;";

        try (Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            List<Project> projects = new ArrayList<>();

            statement.setInt(1,user.getUserID());

            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("Id");
                String costumerName = rs.getString("CostumerName");
                LocalDate projectDate = rs.getDate("ProjectDate").toLocalDate();
                String projectLocation = rs.getString("ProjectLocation");
                String projectDescription = rs.getString("ProjectDescription");
                int projectCreator = rs.getInt("ProjectCreator");
                String isDeleted = rs.getString("IsDeleted");
                int editedBy = rs.getInt("LastEditedBy");
                String canBeEditedByTech = rs.getString("CanBeEditedByTech");
                LocalDate lastEdited = rs.getDate("LastEdited").toLocalDate();
                int costumerType = rs.getInt("CostumerType");


                Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType);
                projects.add(project);
            }
            return projects;
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to retrive all your installations");
        }
    }

    @Override
    public void updateProject(Project project) throws Exception {
        String sql = "UPDATE [Project] SET CostumerName = ?, ProjectDate = ?, ProjectLocation = ?, ProjectDescription = ?, ProjectCreator = ?, IsDeleted = ?, LastEditedBy = ?, LastEdited = ?, CanBeEditedByTech = ?, CostumerType = ? WHERE Id = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, project.getCostumerName());
            statement.setDate(2, Date.valueOf(project.getProjectDate()));
            statement.setString(3, project.getProjectLocation());
            statement.setString(4, project.getProjectDescription());
            statement.setInt(5, project.getProjectCreatorId());
            statement.setString(6, String.valueOf(project.getProjectIsDeleted()));
            statement.setInt(7, project.getLastEditedBy());
            statement.setDate(8, Date.valueOf(project.getLastEdited()));
            statement.setString(9, String.valueOf(project.getCanBeEditedByTech()));
            statement.setInt(10, project.getLastEditedBy());
            statement.setInt(11, project.getProjectId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update or delete Project", e);
        }
    }

}
