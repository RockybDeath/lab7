import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client_love_Server {
//    private static ByteArrayOutputStream Second;
//    private static ObjectOutputStream First;
//    private static ByteBuffer Third;
//    private static ObjectInputStream rr;
//    private static ByteArrayInputStream tt;
//    private static ByteBuffer yyy=ByteBuffer.allocate(64000);
//    static {
//        try {
//            Second = new ByteArrayOutputStream(65536);
//            First = new ObjectOutputStream(Second);
//        }
//        catch (IOException e){
//            System.out.println("Нельзя подключиться");
//        }
//    }
    public static void write(Object a, ObjectOutputStream First,ByteArrayOutputStream Second,SocketChannel privet,ByteBuffer Third) throws IOException {
//        try {
           if (a instanceof java.lang.String) First.write(((String) a).getBytes(StandardCharsets.UTF_8)); else
            First.writeObject(a);
            First.flush();
            privet.write(Third.wrap(Second.toByteArray()));
            Second.reset();
//            if (a instanceof java.lang.String) out.write(((String)a).getBytes(StandardCharsets.UTF_8));
//            else out.writeObject(a);
//            out.flush();
//        } catch (IOException e) {
//            try {
//                First.reset();
//                Second.reset();
//            } catch (IOException ex) {
//                System.out.println("невозмонжо закрыть");
//            }
//            System.out.println("Невозможно отправить, сервер миртв");
//        }
    }
//    public static String read(SocketChannel privet) throws IOException{
    public static String read(SocketChannel privet)throws IOException{
//        return "dwdwd";
//        try {
        try {
            ByteBuffer input = ByteBuffer.allocate(2000000);
            privet.read(input);
            String ret = (new String(input.array())).substring(0,input.position());
            String [] God=ret.split(";;;;;;");
            if (God.length==2){ if (God[1].contains("This user is already authorized"))
            {ClientCreate.name=""; ClientCreate.password=""; ClientCreate.email=""; return "This person is already authorized.Ti kto malchik";}
            if (God[1].contains("Login was successful")) {ClientCreate.alreadySign=true; return God[1];}
            else return God[1];}
            else return "";
//            return ret.substring(4, input.position());
        }catch (StringIndexOutOfBoundsException e){
            return "Error on server";
        }}
//            input.rewind();
//            System.out.println(privet.read(input));
//            byte [] wow=input.array();
//            String s="";
//            for(int i=1;i<input.position();i++){
//                s=s+(char)wow[i];
//            }
//            ByteArrayInputStream meow=new ByteArrayInputStream(wow);
//        return  read.readUTF();
//        byte [] wow=new byte[meow.available()];
//            meow.read(wow,0,meow.available());
//            return new String(wow,StandardCharsets.UTF_8);
//            tt=new ByteArrayInputStream(yyy.array());
//            rr=new ObjectInputStream(tt);
//            rr.read(cccc);
//            tt.reset();
//            rr.reset();
//            return new String(cccc);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            System.out.println("Ошибка получения");
//        }
//        return read.readUTF();
    }

