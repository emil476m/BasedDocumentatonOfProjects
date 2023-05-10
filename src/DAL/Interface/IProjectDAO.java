package DAL.Interface;

import BE.Device;
import BE.Project;
import BE.UserTypes.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IProjectDAO {
    List<Project> getAllProjects() throws Exception;

    List<Project> getMyProjects(User user) throws Exception;

    void updateProject(Project project) throws Exception;

    Project createProject(Project project, List<Device> devices) throws SQLException;

    void UpdateProjectWithDevices(Project project, List<Device> newDevices) throws SQLException;

    Project getProjectFromId(Project project) throws Exception;

    boolean lastProjectEditMatch(Project project) throws Exception;
}
