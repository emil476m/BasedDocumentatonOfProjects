package BE.UserTypes;

public class User {
    private String name;
    private String mail;
    private String userName;
    private String passWord;
    private int userType;
    private Boolean isDeleted;

    private int userID;

    public User(int userID, String passWord, String userName, String mail, String name, int userType, Boolean isDeleted){
        this.userID = userID;
        this.passWord = passWord;
        this.userName = userName;
        this.mail = mail;
        this.name = name;
        this.userType = userType;
        this.isDeleted = isDeleted;
    }
    public User(String passWord, String userName, String mail, String name, int userType){
        this.passWord = passWord;
        this.userName = userName;
        this.mail = mail;
        this.name = name;
        this.userType = userType;
        this.isDeleted = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserClass(){
        return this.getClass().getSimpleName();
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return
                userID + "\t" + name + " " + "\t" + this.getClass().getSimpleName();
    }
}
