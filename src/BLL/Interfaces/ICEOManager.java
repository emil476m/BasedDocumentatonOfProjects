package BLL.Interfaces;

import BE.DeviceType;
import BE.Project;
import BE.UserTypes.User;

import java.util.List;

public interface ICEOManager {
    List<Project> getAllProjects() throws Exception;

    List<User> getAllUsers() throws Exception;

    List<DeviceType> getAllDeviceTypes() throws Exception;

    User createUser(User user) throws Exception;

    void updateUser(User user) throws Exception;

    boolean checkUserName(String userName) throws Exception;

    User getUserFromId(int userId) throws Exception;
}
