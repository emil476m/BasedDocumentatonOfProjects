package DAL.Interface;

import BE.UserTypes.User;

public interface ILoginDAO {

    User loginUser(String username, String password);
}
