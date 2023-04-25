package BE.UserTypes;

public class Technician extends User{
    public Technician(int userID, String passWord, String userName, String mail, String name, int userType) {
        super(userID, passWord, userName, mail, name, userType);
    }

    public Technician(String passWord, String userName, String mail, String name, int userType) {
        super(passWord, userName, mail, name, userType);
    }
}
