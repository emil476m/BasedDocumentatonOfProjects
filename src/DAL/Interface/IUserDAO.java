package DAL.Interface;

import BE.UserTypes.User;

import java.util.List;

public interface IUserDAO {
    List<User> getAllUsers() throws Exception;
}
