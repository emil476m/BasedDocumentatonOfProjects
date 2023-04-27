package DAL.DB;

import BE.UserTypes.*;
import DAL.DBUtil.BCrypt;
import DAL.DatabaseConnector;
import DAL.Interface.ILoginDAO;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;

public class LoginDAO_DB implements ILoginDAO {

    private DatabaseConnector dbConnector;

   public LoginDAO_DB() throws IOException {
       dbConnector = new DatabaseConnector();
   }

    @Override
    public User loginUser(String username, String password) throws SQLException {
       String sql = "SELECT * FROM [User] INNER JOIN [UserType] ON [User].UserType = [UserType].ID WHERE UserName=? AND IsDeleted=?";
       User user = null;
        try(Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1,username);
            statement.setString(2,"false");
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next())
            {
                int id = rs.getInt("Id");
                String userName = rs.getString("UserName");
                String passWord = rs.getString("PassWord");
                String eMail = rs.getString("Mail");
                String name = rs.getString("Name");
                int userType = rs.getInt("UserType");
                String isDeleted = rs.getString("IsDeleted");
                String type = rs.getString("Type");
                if(type.equals(CEO.class.getSimpleName()))
                {
                    user = new CEO(id, passWord, userName, eMail, name, userType, Boolean.valueOf(isDeleted));
                }
                else if(type.equals(ProjectManager.class.getSimpleName()))
                {
                    user = new ProjectManager(id, passWord, userName, eMail, name, userType, Boolean.valueOf(isDeleted));
                }
                else if (type.equals(Technician.class.getSimpleName()))
                {
                    user = new Technician(id, passWord, userName, eMail, name, userType, Boolean.valueOf(isDeleted));
                }
                else if (type.equals(SalesPerson.class.getSimpleName()))
                {
                    user = new SalesPerson(id, passWord, userName, eMail, name, userType, Boolean.valueOf(isDeleted));
                }
            }

            if(user == null)
            {
                return null;
            }

            if(BCrypt.checkpw(password,user.getPassWord()))
            {
                return user;
            }
            else
            {
                return null;
            }

        } catch (SQLException e) {
            throw new SQLException("Failed to find a user",e);
        }
    }
}
