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
       String sql = "SELECT * FROM [User] WHERE UserName=?";
       String Usertype = "SELECT * FROM [UserType] WHERE Id=?";
       User user = null;
       User finalUser = null;
        try(Connection conn = dbConnector.getConnection(); PreparedStatement statement = conn.prepareStatement(sql))
        {
            statement.setString(1,username);
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
                if(isDeleted.equals("false"))
                {
                    user = new User(id, passWord, userName, eMail, name, userType, Boolean.valueOf(isDeleted));
                }
            }
            PreparedStatement userType = conn.prepareStatement(Usertype);
            userType.setInt(1,user.getUserType());
            userType.execute();

            ResultSet rs2 = userType.getResultSet();
            while (rs2.next())
            {
                if(rs2.getString("Type").equals(CEO.class.getSimpleName()))
                {
                    finalUser = new CEO(user.getUserID(),user.getPassWord(),user.getUserName(),user.getMail(),user.getName(),user.getUserType(),user.getDeleted());
                }
                else if(rs.getString("Type").equals(ProjectManager.class.getSimpleName()))
                {
                    finalUser = new ProjectManager(user.getUserID(),user.getPassWord(),user.getUserName(),user.getMail(),user.getName(),user.getUserType(),user.getDeleted());
                }
                else if(rs.getString("Type").equals(SalesPerson.class.getSimpleName()))
                {
                    finalUser = new SalesPerson(user.getUserID(),user.getPassWord(),user.getUserName(),user.getMail(),user.getName(),user.getUserType(),user.getDeleted());
                }
                else if(rs2.getString("Type").equals(Technician.class.getSimpleName()))
                {
                    finalUser = new Technician(user.getUserID(),user.getPassWord(),user.getUserName(),user.getMail(),user.getName(),user.getUserType(),user.getDeleted());
                }
            }

            if(BCrypt.checkpw(password,finalUser.getPassWord()))
            {
                return finalUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to find a user",e);
        }
        return null;
    }
}
