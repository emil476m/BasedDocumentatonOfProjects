package BE;

public class User {
    private String name;
    private String mail;
    private String userName;
    private String passWord;
    private int userType;

    private int userID;

    public User(int userID, String passWord, String userName, String mail, String name, int userType){
        this.userID = userID;
        this.passWord = passWord;
        this.userName = userName;
        this.mail = mail;
        this.name = name;
        this.userType = userType;
    }
    public User(String passWord, String userName, String mail, String name, int userType){
        this.passWord = passWord;
        this.userName = userName;
        this.mail = mail;
        this.name = name;
        this.userType = userType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getUserName() {
        return userName;
    }
    public int getUserID() {
        return userID;
    }
    public String getPassWord(){
        return passWord;
    }

    public int getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return
                userID + "\t" + name + " " + "\t" + this.getClass().getSimpleName();
    }
}
