package DAL.DB;

import BE.DeviceType;
import BE.UserTypes.*;
import DAL.DatabaseConnector;
import DAL.Interface.IDeviceDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                deviceTypeList.add(new DeviceType(id, type));
            }
            return deviceTypeList;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Failed to retrieve DeviceTypes", e);
        }
    }
}
