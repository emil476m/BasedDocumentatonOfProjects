package DAL.Interface;

import BE.Project;
import BE.UserTypes.User;

import java.util.List;

public interface ICEODAO {
    List<Project> getAllProjects() throws Exception;

    List<User> getAllUsers() throws Exception;
}
