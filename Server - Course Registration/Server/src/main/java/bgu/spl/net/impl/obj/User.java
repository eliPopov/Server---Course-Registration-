package bgu.spl.net.impl.obj;


public class User {

    private String userName;
    private String password;
    private boolean isLogged;

    public User(){}

    public User(String userName) {
        this.userName = userName;
        this.isLogged = false;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isLogged = false;
    }

    public boolean isLogged() { return isLogged; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void Login(){ isLogged=true; }

    public void LogOut(){ isLogged=false; }
}
