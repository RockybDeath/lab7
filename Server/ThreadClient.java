import sun.reflect.generics.tree.Tree;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;

public class ThreadClient implements Runnable {
    private Socket client=null;
    private TreeCol mytree;
    public ThreadClient(Socket socket,TreeCol mytree){
        client=socket;
        this.mytree=mytree;
    }
    public void run(){
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            try {
//                System.out.println("dwdwdw");
//            } catch (Exception e) {
//                System.err.println("Данные работы программы не сохранены");
//            }
//        }));
        try(ObjectOutputStream out=new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in=new ObjectInputStream(client.getInputStream())){
            String what="";
            String answer="";
            Human human=new Human();
            Mail clientmail = new Mail("valeronace@gmail.com", "SashaIvan111");
            while(!client.isClosed()&&!"disconnect".equals(what)){
                if(in.available()>0){
                    int size = client.getReceiveBufferSize();
                    byte[] lol = new byte[size];
                    in.read(lol);
                    what=((new String(lol))).trim();
                    User user = (User) in.readObject();
                    System.out.println("Read from client message - " + what);
                    if("login".equals(what)||"sign".equals(what)||"disconnect".equals(what))
                    answer=Commands.Activate(what,in,mytree,clientmail,user);
                    else {
                        if(Register.checkUser(user)) answer=Commands.Activate(what,in,mytree,clientmail,user);
                        else answer="You are not authorized.";
                    }
                    if (answer==null) answer="STRONG";
                    else answer=";;;;;;"+answer;
                    Runnable r = new ThreadAnswer(answer, out);
                    Thread t = new Thread(r);
                    t.start();
                    t.interrupt();
                }
            }
            Thread.sleep(100);
            System.out.println("Disconnect");
            out.close();
            in.close();
            client.close();
            client=null;
        }
        catch(ClassNotFoundException e){
            System.out.println("Data does not match, abrupt stop of the server.");
        }
        catch (IOException e){
            System.out.println("I can not send or accept");
        }
        catch (InterruptedException e){
            System.out.println("Death");
        }
    }
}
