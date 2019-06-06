import java.io.Serializable;

public class User implements Serializable,Cloneable {
    private String email;
    private String name;
    private String password;
    public User(String email,String name, String password){
        this.email=email;
        this.name=name;
        this.password=password;
    }
    public User(){};
    public String getName() {
        return name;
    }
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
