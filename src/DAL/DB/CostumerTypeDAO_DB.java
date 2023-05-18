package DAL.DB;

import BE.CostumerType;
import DAL.DatabaseConnector;
import DAL.Interface.ICostumerTypeDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CostumerTypeDAO_DB implements ICostumerTypeDAO {
    DatabaseConnector dbConnector;

    public CostumerTypeDAO_DB() throws IOException {
        dbConnector = new DatabaseConnector();
    }

    /**
     * Retrieves all customerTypes from the database.
     * @return returns a list of found customerTypes.
     * @throws SQLException
     */
    @Override
    public List<CostumerType> getAllCostumerTypes() throws SQLException {
        List<CostumerType> costumerTypes = new ArrayList<>();
        String sql = "SELECT * FROM CostumerType";
        try (Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next())
            {
                int id = rs.getInt("Id");
                String type = rs.getString("Type");
                CostumerType costumerType = new CostumerType(id,type);
                costumerTypes.add(costumerType);
            }
            return costumerTypes;
        } catch (SQLException e) {
            throw new SQLException("Failed to get all CostumerTypes",e);
        }
    }
}
