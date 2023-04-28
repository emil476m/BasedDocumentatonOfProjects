package DAL.DB;

import BE.Device;
import BE.Project;
import BE.UserTypes.User;
import DAL.DatabaseConnector;
import DAL.Interface.IProjectDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

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
                String address = resultSet.getString("ProjectAddress");
                String zipCode = resultSet.getString("ProjectZipCode");

                Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType, address, zipCode);
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
                String address = rs.getString("ProjectAddress");
                String zipCode = rs.getString("ProjectZipCode");


                Project project = new Project(id, costumerName, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType, address, zipCode);
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
        String sql = "UPDATE [Project] SET CostumerName = ?, ProjectDate = ?, ProjectLocation = ?, ProjectDescription = ?, ProjectCreator = ?, IsDeleted = ?, LastEditedBy = ?, LastEdited = ?, CanBeEditedByTech = ?, CostumerType = ?, ProjectAddress = ?, ProjectZipCode = ? WHERE Id = ?;";
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
            statement.setString(11, project.getAddress());
            statement.setString(12,project.getZipCode());
            statement.setInt(13,project.getProjectId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update or delete Project", e);
        }
    }

    @Override
    public Project createProject(Project project, List<Device> devices) throws SQLException {
        String projectTableString = "INSERT INTO Project (CostumerName,ProjectDate,ProjectLocation,ProjectDescription,ProjectCreator,IsDeleted,LastEditedBy,LastEdited,CanBeEditedByTech,CostumerType,ProjectAddress,ProjectZipCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
        String deviceTable = "INSERT INTO Device (DeviceUsername,DevicePassword,DeviceType) VALUES(?,?,?);";
        String deviceForProject = "INSERT INTO DeviceForProject (DeviceId,ProjectId)";
        String workingOnProjectString ="INSERT INTO WorkingOnProject (ProjectId,UserId) VALUES(?;?)";
        Project project1 = null;
        try (Connection connection = dbConnector.getConnection();)
        {
            connection.setAutoCommit(false);
            PreparedStatement projectTable = connection.prepareStatement(projectTableString, Statement.RETURN_GENERATED_KEYS);
            projectTable.setString(1, project.getCostumerName());
            projectTable.setDate(2, Date.valueOf(project.getProjectDate()));
            projectTable.setString(3, project.getProjectLocation());
            projectTable.setString(4, project.getProjectDescription());
            projectTable.setInt(5, project.getProjectCreatorId());
            projectTable.setString(6, String.valueOf(project.getProjectIsDeleted()));
            projectTable.setInt(7, project.getLastEditedBy());
            projectTable.setDate(8, Date.valueOf(project.getLastEdited()));
            projectTable.setString(9, String.valueOf(project.getCanBeEditedByTech()));
            projectTable.setInt(10, project.getLastEditedBy());
            projectTable.setString(11, project.getAddress());
            projectTable.setString(12,project.getZipCode());
            projectTable.executeUpdate();

            ResultSet rs = projectTable.getGeneratedKeys();
            while (rs.next())
            {
                int id = rs.getInt(1);
                project1 = new Project(id,project.getCostumerName(),project.getProjectDate(),project.getProjectLocation(),project.getProjectDescription(),project.getProjectCreatorId(),project.getProjectIsDeleted(),project.getLastEditedBy(),project.getCanBeEditedByTech(),project.getLastEdited(),project.getCostumerType(),project.getAddress(),project.getZipCode());
            }
            if(!devices.isEmpty()) {
                PreparedStatement deviceT = connection.prepareStatement(deviceTable);
                for (Device d : devices) {
                    deviceT.setString(1, d.getDeviceUserName());
                    deviceT.setString(2, d.getDevicePassWord());
                    deviceT.setInt(3, d.getDeviceTypeId());
                    deviceT.addBatch();
                }
                deviceT.executeBatch();
                ResultSet rs2 = deviceT.getResultSet();
                List<Device> deviceList = new ArrayList<>();
                for (Device d : devices) {
                    while (rs2.next()) {
                        int id = rs2.getInt("Id");
                        Device device = new Device(id, d.getDeviceTypeId(), d.getDeviceUserName(), d.getDevicePassWord());
                        deviceList.add(device);
                    }

                }

                PreparedStatement deviceForP = connection.prepareStatement(deviceForProject);
                for (Device dev : deviceList) {
                    deviceForP.setInt(1, dev.getDeviceId());
                    deviceForP.setInt(2, project1.getProjectId());
                    deviceForP.addBatch();
                }
                deviceForP.executeBatch();
            }
            PreparedStatement workingON = connection.prepareStatement(workingOnProjectString);
            workingON.setInt(1, project1.getProjectId());
            workingON.setInt(2, project1.getProjectCreatorId());
            workingON.executeUpdate();
            connection.commit();
            return project1;
        }
        catch (SQLException e) {
            throw new SQLException("Failed to create project in database", e);
        }
    }


}
