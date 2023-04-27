package DAL.Interface;

import BE.CostumerType;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface ICostumerTypeDAO {
    List<CostumerType> getAllCostumerTypes() throws SQLException;
}
