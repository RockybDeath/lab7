

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

public class Register {
    public static ConcurrentSkipListMap<String,User> Users=new ConcurrentSkipListMap<>();
    public static String login(User user,Mail clientmail){
//            String condition="\"Email\"="+"\'"+user.getEmail().trim()+"\'";
//            String nickcondition="\"Name\"="+"\'"+user.getName().trim()+"\'";
        try {
            if(!(Main.db.dbHas(1,user.getEmail())||Main.db.dbHas(2,user.getName()))) {
                    String passwrod = Register.getRandomPassword();
                    clientmail.send(passwrod, user.getEmail());
                    PreparedStatement preparedStatement=DbConnect.connection.prepareStatement("INSERT INTO \"Users\" VALUES(?,?,?)");
                    preparedStatement.setString(1,user.getName());
                    preparedStatement.setString(3,passwrod);
                    preparedStatement.setString(2,user.getEmail());
                    Main.db.change(preparedStatement,"set");
                    return "Registration was successful, you have sent a password to your email for authorization";
                }
            else return "This user or email already exists.";}
        catch (SQLException|NullPointerException e){
            e.printStackTrace();
            return "No interaction with the database";
        }
        catch (Exception e){
            return "I can not send to "+user.getEmail();
        }

    }
    public static String sign(User user){
        String passwordcondition="\"Password\"="+"\'"+user.getPassword().trim()+"\'";
        String nickcondition="\"Name\"="+"\'"+user.getName().trim()+"\'";
        try {
            if ((Main.db.dbHas(3, user.getPassword()) && Main.db.dbHas(2, user.getName()))) {
                if(Register.checkUser(user)) return "This user is already authorized.";
                else Users.put(user.getName(), user);
                return "Login was successful";
            } else return "Incorrect login or password";
        }catch (SQLException e){
            return "No interaction with the database";
        }
    }
    public static boolean checkUser(User user){
        if(user.getName()!=null){
            if(Register.Users.containsKey(user.getName())) return true;
        else return false;}
        else return false;
    }
    public static String getRandomPassword(){
        String superstring="?!#%&'()*+,-.0123456789:<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]{|}~;";
        char[] biba = new char[32];
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            biba[i] = superstring.charAt(random.nextInt(superstring.length()));
        }
        String md5Hex= DigestUtils.md5Hex(new String(biba));
        return md5Hex;
    }
}
