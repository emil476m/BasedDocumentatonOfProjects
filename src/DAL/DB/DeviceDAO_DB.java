package DAL.DB;

import BE.Device;
import BE.DeviceType;
import BE.Project;
import DAL.DatabaseConnector;
import DAL.Interface.IDeviceDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO_DB implements IDeviceDAO {

    private DatabaseConnector dbConnector;

    public DeviceDAO_DB() throws IOException {
        dbConnector = new DatabaseConnector();
    }

    @Override
    public List<DeviceType> getAllDeviceTypes() throws Exception {
        String sql = "SELECT * FROM DeviceType WHERE IsDeleted = 'false';";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<DeviceType> deviceTypeList = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String type = resultSet.getString("Type");
                String isDeleted = resultSet.getString("IsDeleted");
                boolean isCustom = Boolean.valueOf(resultSet.getString("Custom"));
                deviceTypeList.add(new DeviceType(id, type, Boolean.parseBoolean(isDeleted), isCustom));
            }
            return deviceTypeList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve DeviceTypes", e);
        }
    }

    @Override
    public DeviceType createDeviceType(DeviceType deviceType) throws Exception{
        String sql = "INSERT INTO [DeviceType] (Type, IsDeleted, Custom) VALUES (?,?,?);";
        try(Connection connection = dbConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){


            statement.setString(1, deviceType.getType());
            statement.setString(2, String.valueOf(deviceType.getIsDeleted()));
            statement.setString(3, String.valueOf(deviceType.getIsCustom()));

            statement.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            DeviceType deviceType1 = new DeviceType(id, deviceType.getType(), deviceType.getIsDeleted(), deviceType.getIsCustom());

            return deviceType1;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create DeviceType", e);
        }
    }

    @Override
    public void updateDeviceType(DeviceType deviceType) throws Exception {
        String sql = "UPDATE [DeviceType] SET Type = ?, IsDeleted = ?, Custom = ? WHERE Id = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, deviceType.getType());
            statement.setString(2, String.valueOf(deviceType.getIsDeleted()));
            statement.setString(3, String.valueOf(deviceType.getIsCustom()));
            statement.setInt(4, deviceType.getId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete DeviceType", e);
        }
    }

    @Override
    public List<Device> getAllDevicesForProject(Project project) throws SQLException {
        String sql = "SELECT * FROM Device\n" +
                "INNER JOIN DeviceForProject ON Device.Id = DeviceForProject.DeviceId\n" +
                "INNER JOIN [DeviceType] ON Device.DeviceType = [DeviceType].id\n" +
                "WHERE ProjectId = ?;";
        List<Device> devices = new ArrayList<>();
        try(Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
         statement.setInt(1,project.getProjectId());
         statement.execute();

         ResultSet rs = statement.getResultSet();
         while (rs.next())
         {
             int id = rs.getInt("Id");
             String username = rs.getString("DeviceUsername");
             String password = rs.getString("DevicePassword");
             String deviceType = rs.getString("Type");
             int deviceTypeId = rs.getInt("DeviceType");
             Device device = new Device(id,deviceTypeId,username,password,deviceType);
             devices.add(device);
         }
         return devices;
        } catch (SQLException e) {
            throw new SQLException("Failed to get devices for project from the database", e);
        }
    }


}
