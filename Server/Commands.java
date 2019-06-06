import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentSkipListMap;

public class Commands {
    public static String Activate(String command,ObjectInputStream in,TreeCol mytree,Mail clientmail,User user){
        try {
            byte [] examaple=new byte[5570000];
            if ("info".equals(command)) return mytree.info();
            else if ("show".equals(command)) {
                mytree.setL("");
                return mytree.show();
            }
            else if ("save".equals(command)) {
//                in.read(examaple);
                return mytree.save(user,mytree);
            }
            else if ("remove".equals(command)) {
                in.read(examaple);
                return mytree.remove(new String(examaple).trim(),user);
            }
            else if("import".equals(command)){
                mytree.addset((ConcurrentSkipListMap<String,Human>)in.readObject(),user);
                return "Data added";
            }
            else if("login".equals(command)||"sign".equals(command)){
//                in.read(examaple);
//                String username=(new String(examaple)).trim();
//                examaple=new byte[5570000];
//                in.read(examaple);
//                String emailORpassword=(new String(examaple)).trim();
                if ("login".equals(command))return Register.login(user,clientmail);
                else return Register.sign(user);
            }
            else if ("insert".equals(command)) return mytree.insert((Human)in.readObject(),user);
            else if ("add_if_max".equals(command)||"add_if_min".equals(command)) return mytree.add_if((Human)in.readObject(),command,user);
//            else if ("load".equals(command)) {
//                in.read(examaple);
//                return mytree.load(new String(examaple).trim());
//            }
//            else if("autosave".equals(command)) return mytree.autosave();
//            else if("autosave".equals(command)) return mytree.save(user,mytree);
            else if("disconnect".equals(command)){
                if(Register.checkUser(user)) {Register.Users.remove(user.getName()); return mytree.save(user,mytree)+". You have disconnected";}
                return "You have disconnected";
            }

        }
        catch(ClassNotFoundException e){
            System.out.println("There is no such class");
        }
        catch(IOException e){
            System.out.println("I can not pass");
        }
        return null;
    }
}
