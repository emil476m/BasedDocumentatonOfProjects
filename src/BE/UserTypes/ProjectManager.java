package BE.UserTypes;

public class ProjectManager extends User{
    public ProjectManager(int userID, String passWord, String userName, String mail, String name, int userType) {
        super(userID, passWord, userName, mail, name, userType);
    }

    public ProjectManager(String passWord, String userName, String mail, String name, int userType) {
        super(passWord, userName, mail, name, userType);
    }
}
