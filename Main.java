import javafx.beans.binding.ObjectExpression;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Main {
    public static void main(String[] args) {
//        try {
//            Scanner klava=new Scanner(System.in);
//            SocketAddress socket=new InetSocketAddress(Inet4Address.getLocalHost(),2222);
//            SocketChannel privet=SocketChannel.open(socket);
//            ByteBuffer ll=ByteBuffer.wrap(klava.nextLine().getBytes(StandardCharsets.UTF_8));
//            privet.write(ll);
//            Thread.sleep(4000);
//        }
//        catch (UnknownHostException|SocketException e){
//            System.out.println("dwdw");
//        }
//        catch (InterruptedException e){
//            System.out.println("12");
//        }
//        catch (IOException e){
//            System.out.println("dwdwdwwww");
//        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("I'm die");
            } catch (Exception e) {
                System.err.println("Your work don't save");
            }
        }));
        String s = "";
//        ZonedDateTime dz=ZonedDateTime.now();
//        System.out.println(dz.toString());
        try {
            if (args.length > 1 | args.length == 0) {
                System.out.println("You must enter the name of a single file, the data is not loaded");
            } else {
                FileInputStream Try = new FileInputStream(args[0]);
                BufferedInputStream MyTree = new BufferedInputStream(Try);
                if (MyTree.available() > 0) {
                    byte[] b = new byte[MyTree.available()];
                    MyTree.read(b);
                    String XmlTree = new String(b);
                    TreeCol.set(FileOper.fromXml(XmlTree));
                } else System.out.println("Document is empty");
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no file for the specified path or no access rights to it.");
        } catch (IOException e) {
            System.out.println("File is empty or you are not authorized to access the file.");
        }
        System.out.print("Enter your command, please without bugs: ");
           try {
            while (true) {
                Scanner Input = new Scanner(System.in);
                s = Input.nextLine();
                s = s.trim();
                ParserJson MyString = new ParserJson(s);
                MyString.parser();
                if("connect".equals(MyString.getFirstValue())&&MyString.Checker()){
                    String host=MyString.getInput().substring(0, MyString.getInput().length() - 1);
                    try{
                    if(host!=null&&host.contains(":")) {
                            String[] ipport = host.split(":");
                            if (ipport.length == 2) {
                                SocketAddress socket1 = new InetSocketAddress(ipport[0].trim(), Integer.valueOf(ipport[1].trim()));
                                try (SocketChannel privet = SocketChannel.open(socket1)) {
                                    ClientCreate clientCreate = new ClientCreate(privet);
                                    clientCreate.CreateClient();
                                } catch (SocketException e) {
                                    System.out.println("Server is not available");
                                }
                            } else System.out.println("Wrong data");
                        } else System.out.println("Wrong data");
                    }catch (NullPointerException | StringIndexOutOfBoundsException | IllegalArgumentException e) {
                        System.out.println("Wrong data2");
                    }

                }
                else if("help".equals(s)){
                    TreeCol.help1();
                }
                else if("exit".equals(s)){
                    System.out.println("Exit from the program");
                    System.exit(0);
                }
                else System.out.println("Invalid data, enter help");
            }
            }
            catch (NullPointerException | StringIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("Wrong data1");
            }
//        catch (ClassNotFoundException e){
//            System.out.println("Непонятный объект с сервера");
//        }
            catch (NoSuchElementException e) {
                System.out.println("It was necessary for you (((");
            } catch (UnknownHostException e) {
                System.out.println("Wrong address");
            } catch (SocketException e) {
                System.out.println("Server is not available");
            } catch (IOException e) {
                System.out.println("Can not connect");
            }
            //System.out.println(Client_love_Server.read(privet));
//                ByteBuffer ff=ByteBuffer.allocate(65443);
//                privet.read(ff);
//                byte [] tea=ff.array();
//                System.out.println(new String(tea));
//                ByteArrayInputStream ggg=new ByteArrayInputStream(tea);
////                System.out.println((char)ggg.read());
//                    ObjectInputStream ggg1 = new ObjectInputStream(ggg);
//                    System.out.println(ggg1.readObject());
        }
    }
