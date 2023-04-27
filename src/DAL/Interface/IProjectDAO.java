package DAL.Interface;

import BE.Project;
import BE.UserTypes.User;

import java.util.List;

public interface IProjectDAO {
    List<Project> getAllProjects() throws Exception;

    List<Project> getMyProjects(User user) throws Exception;

    void updateProject(Project project) throws Exception;
}
