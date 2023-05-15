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
                String costumerEmail = resultSet.getString("CostumerEmail");
                LocalDate projectDate = resultSet.getDate("ProjectDate").toLocalDate();
                String projectLocation = resultSet.getString("ProjectLocation");
                String projectDescription = resultSet.getString("ProjectDescription");
                int projectCreator = resultSet.getInt("ProjectCreator");
                String isDeleted = resultSet.getString("IsDeleted");
                int editedBy = resultSet.getInt("LastEditedBy");
                String canBeEditedByTech = resultSet.getString("CanBeEditedByTech");
                Timestamp lastEdited = resultSet.getTimestamp("LastEdited");
                int costumerType = resultSet.getInt("CostumerType");
                String address = resultSet.getString("ProjectAddress");
                String zipCode = resultSet.getString("ProjectZipCode");

                Project project = new Project(id, costumerName, costumerEmail, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType, address, zipCode);
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
                String costumerEmail = rs.getString("CostumerEmail");
                LocalDate projectDate = rs.getDate("ProjectDate").toLocalDate();
                String projectLocation = rs.getString("ProjectLocation");
                String projectDescription = rs.getString("ProjectDescription");
                int projectCreator = rs.getInt("ProjectCreator");
                String isDeleted = rs.getString("IsDeleted");
                int editedBy = rs.getInt("LastEditedBy");
                String canBeEditedByTech = rs.getString("CanBeEditedByTech");
                Timestamp lastEdited = rs.getTimestamp("LastEdited");
                int costumerType = rs.getInt("CostumerType");
                String address = rs.getString("ProjectAddress");
                String zipCode = rs.getString("ProjectZipCode");


                Project project = new Project(id, costumerName, costumerEmail, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType, address, zipCode);
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
        String sql = "UPDATE [Project] SET CostumerName = ?, CostumerEmail = ?, ProjectDate = ?, ProjectLocation = ?, ProjectDescription = ?, ProjectCreator = ?, IsDeleted = ?, LastEditedBy = ?, LastEdited = ?, CanBeEditedByTech = ?, CostumerType = ?, ProjectAddress = ?, ProjectZipCode = ? WHERE Id = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, project.getCostumerName());
            statement.setString(2, project.getCostumerEmail());
            statement.setDate(3, Date.valueOf(project.getProjectDate()));
            statement.setString(4, project.getProjectLocation());
            statement.setString(5, project.getProjectDescription());
            statement.setInt(6, project.getProjectCreatorId());
            statement.setString(7, String.valueOf(project.getProjectIsDeleted()));
            statement.setInt(8, project.getLastEditedBy());
            statement.setTimestamp(9, project.getLastEdited());
            statement.setString(10, String.valueOf(project.getCanBeEditedByTech()));
            statement.setInt(11, project.getCostumerType());
            statement.setString(12, project.getAddress());
            statement.setString(13,project.getZipCode());
            statement.setInt(14,project.getProjectId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to update or delete Project", e);
        }
    }

    @Override
    public Project createProject(Project project, List<Device> device) throws SQLException {
        String projectTableString = "INSERT INTO Project (CostumerName,CostumerEmail,ProjectDate,ProjectLocation,ProjectDescription,ProjectCreator,IsDeleted,LastEditedBy,LastEdited,CanBeEditedByTech,CostumerType,ProjectAddress,ProjectZipCode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        String checkUser = "SELECT UserType FROM [User] WHERE Id = ?;";
        String workingOnProjectString ="INSERT INTO WorkingOnProject (ProjectId,UserId) VALUES (?,?);";
        Project project1 = null;
        try (Connection connection = dbConnector.getConnection())
        {
            connection.setAutoCommit(false);
            PreparedStatement projectTable = connection.prepareStatement(projectTableString, Statement.RETURN_GENERATED_KEYS);
            projectTable.setString(1, project.getCostumerName());
            projectTable.setString(2,project.getCostumerEmail());
            projectTable.setDate(3, Date.valueOf(project.getProjectDate()));
            projectTable.setString(4, project.getProjectLocation());
            projectTable.setString(5, project.getProjectDescription());
            projectTable.setInt(6, project.getProjectCreatorId());
            projectTable.setString(7, String.valueOf(project.getProjectIsDeleted()));
            projectTable.setInt(8, project.getLastEditedBy());
            projectTable.setTimestamp(9, project.getLastEdited());
            projectTable.setString(10, String.valueOf(project.getCanBeEditedByTech()));
            projectTable.setInt(11, project.getCostumerType());
            projectTable.setString(12, project.getAddress());
            projectTable.setString(13,project.getZipCode());
            projectTable.executeUpdate();

            ResultSet rs = projectTable.getGeneratedKeys();
            while (rs.next())
            {
                int id = rs.getInt(1);
                project1 = new Project(id,project.getCostumerName(),project.getCostumerEmail(),project.getProjectDate(),project.getProjectLocation(),project.getProjectDescription(),project.getProjectCreatorId(),project.getProjectIsDeleted(),project.getLastEditedBy(),project.getCanBeEditedByTech(),project.getLastEdited(),project.getCostumerType(),project.getAddress(),project.getZipCode());
            }
            boolean addToWorkingOn = false;
            PreparedStatement userCheck = connection.prepareStatement(checkUser);
            userCheck.setInt(1, project.getProjectCreatorId());

            ResultSet userRS = userCheck.executeQuery();
            while (userRS.next()) {
                int userType = userRS.getInt("UserType");
                if (userType == 3)
                    addToWorkingOn = true;
                else
                    addToWorkingOn = false;
                System.out.println("This is userType: " +userType);
                System.out.println("This is boolean: " + addToWorkingOn);
            }

            if (addToWorkingOn) {
                PreparedStatement workingOn = connection.prepareStatement(workingOnProjectString);
                workingOn.setInt(1, project1.getProjectId());
                workingOn.setInt(2, project1.getProjectCreatorId());
                workingOn.executeUpdate();
            }
            connection.commit();
            if(!device.isEmpty())
            {
                createDeviceForProject(project1, device);
            }
            return project1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to create project in database", e);
        }
    }

    private void createDeviceForProject(Project project, List<Device> devices) throws SQLServerException {
        String deviceTable = "INSERT INTO Device (DeviceUsername, DevicePassword, DeviceType) VALUES(?,?,?);";
        String deviceForProject = "INSERT INTO DeviceForProject (DeviceId,ProjectId) VALUES(?,?);";
        List<Device> devicesForP = devices;
        List<Device> devicesToAdd = new ArrayList<>();
        try(Connection conn = dbConnector.getConnection())
        {
         PreparedStatement deviceTab = conn.prepareStatement(deviceTable, Statement.RETURN_GENERATED_KEYS);
         conn.setAutoCommit(false);
            for (Device d: devicesForP) {
                deviceTab.setString(1, d.getDeviceUserName());
                deviceTab.setString(2, d.getDevicePassWord());
                deviceTab.setInt(3, d.getDeviceTypeId());
                deviceTab.executeUpdate();
                ResultSet rs1 = deviceTab.getGeneratedKeys();
                while (rs1.next())
                {
                    int id = rs1.getInt(1);
                    Device device = new Device(id, d.getDeviceTypeId(), d.getDeviceUserName(),d.getDevicePassWord(),d.getDeviceTypeString());
                    devicesToAdd.add(device);
                }
            }
            PreparedStatement devsForProject = conn.prepareStatement(deviceForProject);
            for (Device dev: devicesToAdd)
            {
                devsForProject.setInt(1,dev.getDeviceId());
                devsForProject.setInt(2,project.getProjectId());
                devsForProject.addBatch();
            }
            devsForProject.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateProjectWithDevices(Project project, List<Device> newDevices) throws SQLException {
        String projectTable = "UPDATE [Project] SET CostumerName = ?, ProjectDate = ?, ProjectLocation = ?, ProjectDescription = ?, ProjectCreator = ?, IsDeleted = ?, LastEditedBy = ?, LastEdited = ?, CanBeEditedByTech = ?, CostumerType = ?, ProjectAddress = ?, ProjectZipCode = ? WHERE Id = ?;";
        String deviceTable = "INSERT INTO Device (DeviceUsername,DevicePassword,DeviceType) VALUES(?,?,?);";
        String deviceForProject = "INSERT INTO DeviceForProject (DeviceId,ProjectId) VALUSE (?,?)";
        try (Connection conn = dbConnector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(projectTable);
            conn.setAutoCommit(false);
            statement.setString(1, project.getCostumerName());
            statement.setDate(2, Date.valueOf(project.getProjectDate()));
            statement.setString(3, project.getProjectLocation());
            statement.setString(4, project.getProjectDescription());
            statement.setInt(5, project.getProjectCreatorId());
            statement.setString(6, String.valueOf(project.getProjectIsDeleted()));
            statement.setInt(7, project.getLastEditedBy());
            statement.setTimestamp(8, project.getLastEdited());
            statement.setString(9, String.valueOf(project.getCanBeEditedByTech()));
            statement.setInt(10, project.getCostumerType());
            statement.setString(11, project.getAddress());
            statement.setString(12, project.getZipCode());
            statement.setInt(13, project.getProjectId());
            //Run the specified SQL Statement
            statement.executeUpdate();
            conn.commit();
            if(!newDevices.isEmpty())
            {
                createDeviceForProject(project, newDevices);
            }
        } catch(SQLException e){
                throw new SQLException("failed to update project and devices",e);
        }
    }

    @Override
    public Project getProjectFromId(Project project) throws Exception {
        String sql = "SELECT * From Project WHERE Id = ?;";

        try (Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, project.getProjectId());

            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("Id");
                String costumerName = rs.getString("CostumerName");
                String costumerEmail = rs.getString("CostumerEmail");
                LocalDate projectDate = rs.getDate("ProjectDate").toLocalDate();
                String projectLocation = rs.getString("ProjectLocation");
                String projectDescription = rs.getString("ProjectDescription");
                int projectCreator = rs.getInt("ProjectCreator");
                String isDeleted = rs.getString("IsDeleted");
                int editedBy = rs.getInt("LastEditedBy");
                String canBeEditedByTech = rs.getString("CanBeEditedByTech");
                Timestamp lastEdited = rs.getTimestamp("LastEdited");
                int costumerType = rs.getInt("CostumerType");
                String address = rs.getString("ProjectAddress");
                String zipCode = rs.getString("ProjectZipCode");


                Project foundProject = new Project(id, costumerName, costumerEmail, projectDate, projectLocation, projectDescription, projectCreator, Boolean.valueOf(isDeleted),editedBy,Boolean.parseBoolean(canBeEditedByTech),lastEdited,costumerType, address, zipCode);
                return foundProject;
            }
            return null;
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to retrieve the installation");
        }
    }

    @Override
    public boolean lastProjectEditMatch(Project project) throws Exception {
        String sql = "SELECT * From Project WHERE Id = ?;";

        try (Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setInt(1, project.getProjectId());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Timestamp lastEdited = rs.getTimestamp("LastEdited");

                if (project.getLastEdited().equals(lastEdited)) {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to retrieve the installation");
        }
    }
}