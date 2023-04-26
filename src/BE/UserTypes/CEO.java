package BE.UserTypes;

public class CEO extends User {
    public CEO(int userID, String passWord, String userName, String mail, String name, int userType, Boolean isDeleted) {
        super(userID, passWord, userName, mail, name, userType, isDeleted);
    }

    public CEO(String passWord, String userName, String mail, String name, int userType) {
        super(passWord, userName, mail, name, userType);
    }
}
