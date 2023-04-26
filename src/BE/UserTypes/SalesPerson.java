package BE.UserTypes;

public class SalesPerson extends User{
    public SalesPerson(int userID, String passWord, String userName, String mail, String name, int userType, Boolean isDeleted) {
        super(userID, passWord, userName, mail, name, userType, isDeleted);
    }

    public SalesPerson(String passWord, String userName, String mail, String name, int userType) {
        super(passWord, userName, mail, name, userType);
    }
}
