package DAL.Interface;

import BE.UserTypes.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers() throws Exception;

    User createUser(User user) throws Exception;

    void updateUser(User user) throws Exception;

    boolean checkUserName(String userName) throws Exception;

    User getUserFromId(int userId) throws Exception;
}
