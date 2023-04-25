package DAL.Interface;

import BE.User;

public interface ILoginDAO {

    User loginUser(String username, String password);
}
