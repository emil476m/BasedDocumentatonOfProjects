package DAL.DB;

import BE.Project;
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
                String deletedIs = resultSet.getString("IsDeleted");

                boolean isDeleted;
                if (deletedIs.equals("false")) {
                    isDeleted = false;
                    Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, isDeleted);
                    projectList.add(project);
                }
                else
                    isDeleted = true;
            }
            return projectList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve Users", e);
        }
    }
}
