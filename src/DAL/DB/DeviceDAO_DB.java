package DAL.DB;

import BE.DeviceType;
import DAL.DatabaseConnector;
import DAL.Interface.IDeviceDAO;

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
                deviceTypeList.add(new DeviceType(id, type, Boolean.parseBoolean(isDeleted)));
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
        String sql = "INSERT INTO [DeviceType] (Type, IsDeleted) VALUES (?,?);";
        try(Connection connection = dbConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){


            statement.setString(1, deviceType.getType());
            statement.setString(2, String.valueOf(deviceType.getIsDeleted()));

            statement.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = statement.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            DeviceType deviceType1 = new DeviceType(id, deviceType.getType(), deviceType.getIsDeleted());

            return deviceType1;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to create DeviceType", e);
        }
    }

    @Override
    public void updateDeviceType(DeviceType deviceType) throws Exception {
        String sql = "UPDATE [DeviceType] SET Type = ?, IsDeleted = ? WHERE Id = ?;";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, deviceType.getType());
            statement.setString(2, String.valueOf(deviceType.getIsDeleted()));
            statement.setInt(3, deviceType.getId());
            //Run the specified SQL Statement
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to delete DeviceType", e);
        }
    }
}
