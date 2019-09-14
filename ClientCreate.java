import com.sun.javafx.image.impl.ByteRgb;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientCreate {
    private boolean p=false;
    public static boolean alreadySign=false;
    public static boolean iwork=true;
    private SocketChannel privet;
    private ByteArrayOutputStream write1;
    private ObjectInputStream read;
    private ObjectOutputStream write;
    private ByteBuffer write2;
    public static String name="";
    public static String password="";
    public static String email="";
    public ClientCreate(SocketChannel privet){
        this.privet=privet;
    }
    public void CreateClient(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Client_love_Server.write("disconnect", write, write1, privet, write2);
                Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                Thread.sleep(100);
                p=true;
                iwork=false;
            } catch (Exception e) {
                if (p=false)System.err.println("The program data is not saved.");
            }
        }));
        try{
            Human human=new Human();
            iwork=true;
            alreadySign=false;
            System.out.println("You have successfully connected");
            Runnable rr=new ReadFromServer(privet);
            Thread t=new Thread(rr);
            t.start();
//           this.read=new ObjectInputStream(privet.socket().getInputStream());
//            this.write=new ObjectOutputStream(privet.socket().getOutputStream());
            write1=new ByteArrayOutputStream(6535160);
            write=new ObjectOutputStream(write1);
//            Selector ready=Selector.open();
//            privet.configureBlocking(false);
//            privet.register(ready, SelectionKey.OP_READ);
            String s="";
        while (!"disconnect".equals(s)) {
            Scanner Input = new Scanner(System.in);
            s = Input.nextLine();
            s = s.trim();
            ParserJson MyString = new ParserJson(s);
            MyString.parser();
            String command=MyString.getFirstValue();
//            login{red21:win.kudymov@gmail.com}
            if ("show".equals(command) || "info".equals(command)|| "help".equals(command)||"disconnect".equals(command)|| "save".equals(command) ){
                if ("help".equals(command)) TreeCol.help();
                else {Client_love_Server.write(command,write,write1,privet,write2);
                Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                Thread.sleep(100);}
            }
            else if ("import".equals(command)&&MyString.Checker()) {
                TreeCol.Import(MyString.getInput().substring(0, MyString.getInput().length() - 1));
                Client_love_Server.write(command,write,write1,privet,write2);
                Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                Client_love_Server.write(TreeCol.get(),write,write1,privet,write2);
//                System.out.println(Client_love_Server.read(privet));
            }
            else if("login".equals(command)&&MyString.Checker()){
                try {
                    String connect = MyString.getInput().substring(0, MyString.getInput().length() - 1);
                    String[] ipport = connect.split(":");
                    if (ipport.length == 2) {
                        name=ipport[0].trim();
                        email=ipport[1].trim();
//                       User user=new User(ipport[1],ipport[0],"");
                        Client_love_Server.write(command, write, write1, privet, write2);
                        Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
//                        Client_love_Server.write(ipport[0], write, write1, privet, write2);
//                        Client_love_Server.write(ipport[1], write, write1, privet, write2);
                    } else System.out.println("Wrong data");
                }
                catch (NullPointerException|IllegalArgumentException|StringIndexOutOfBoundsException e){
                    System.out.println("Wrong data");
                }}
                else if("sign".equals(command)&&MyString.Checker()){
                    if(alreadySign==true) System.out.println("You already authorized");
                    else{
                    try {
                        String connect = MyString.getInput().substring(0, MyString.getInput().length() - 1);
                        String[] ipport = connect.split(":");
                        if (ipport.length == 2) {
                            name=ipport[0].trim();
                            password=ipport[1].trim();
                            Client_love_Server.write(command, write, write1, privet, write2);
                            Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
//                            Client_love_Server.write(ipport[0], write, write1, privet, write2);
//                            Client_love_Server.write(ipport[1], write, write1, privet, write2);
                        } else System.out.println("Wrong data");
                    }
                    catch (NullPointerException|IllegalArgumentException|StringIndexOutOfBoundsException e){
                        System.out.println("Wrong data");
                    }}
                }
//            else if ("load".equals(command)) {
//                String key=MyString.getInput().substring(0, MyString.getInput().length() - 1);
//                Client_love_Server.write(command,write,write1,privet,write2);
//                Client_love_Server.write(key,write,write1,privet,write2);
////                System.out.println(Client_love_Server.read(privet));
//            }
            else if ("insert".equals(command)) {
                try {
                    MyString.element(MyString.StringKey(MyString.getInput()));
                    if (MyString.getHowmany() == 2 && MyString.getSecondValue() != null && MyString.getThirdValue() != null && MyString.getFourthValue() != null && MyString.getFifthValue() != null && MyString.getSixthValue() != null) {
                        human = new Human(MyString.getSecondValue(), MyString.getThirdValue(), new Location(MyString.getFourthValue(), MyString.getFifthValue()), MyString.getSixthValue());
                        Client_love_Server.write(command, write, write1, privet, write2);
                        Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                        Client_love_Server.write(human, write, write1, privet, write2);
//                        System.out.println(Client_love_Server.read(read));
                    } else System.out.println("Wrong data");
                }
                catch (NullPointerException|IllegalArgumentException|StringIndexOutOfBoundsException e){
                    System.out.println("Wrong data");
                }
            }
            else if ("remove".equals(command)){
                try {
                    MyString.StringKey(MyString.getInput());
                    if (MyString.getSecondValue() == null) System.out.println("Wrong data");
                    else {
                        Client_love_Server.write(command, write, write1, privet, write2);
                        Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                        Client_love_Server.write(MyString.getSecondValue(), write, write1, privet, write2);
//                        System.out.println(Client_love_Server.read(read));
                    }
                }catch (NullPointerException|IllegalArgumentException|StringIndexOutOfBoundsException e){
                    System.out.println("Wrong data");
                }
            }
            else if ("add_if_max".equals(command)||"add_if_min".equals(command)){
                try {
                    MyString.element("{" + MyString.getInput());
                    if (MyString.getSecondValue() != null && MyString.getThirdValue() != null && MyString.getFourthValue() != null && MyString.getFifthValue() != null && MyString.getSixthValue() != null) {
                        human = new Human(MyString.getSecondValue(), MyString.getThirdValue(), new Location(MyString.getFourthValue(), MyString.getFifthValue()), MyString.getSixthValue());
                        Client_love_Server.write(command, write, write1, privet, write2);
                        Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                        Client_love_Server.write(human, write, write1, privet, write2);
//                        System.out.println(Client_love_Server.read(read));
                    } else System.out.println("Wrong data");
                }catch (NullPointerException|IllegalArgumentException|StringIndexOutOfBoundsException e){
                    System.out.println("Wrong data");
                }
            }
            else System.out.println("Wrong data, enter help");
            Thread.sleep(100);
        }
//            read.close();
            privet.close();
    }
        catch (InterruptedException e){
            System.out.println("Client die");
        }
        catch (IOException e){
//            try {
//                privet.close();
//                read.close();
//            } catch (IOException ex) {
//                System.out.println("Незя закрыть");
//            }
            System.out.println("Can't connect");
        }
        catch (NoSuchElementException e) {
            try {
                Client_love_Server.write("disconnect", write, write1, privet, write2);
                Client_love_Server.write(new User(email,name,password),write,write1,privet,write2);
                Thread.sleep(100);
//                p=true;
//                iwork=false;
                p=true;
                iwork=false;
            }
            catch (InterruptedException e1){
                System.out.println("I'm die");
            }
            catch (IOException e1){
                System.out.println("Can't connect");
            }
        }
    }
}
