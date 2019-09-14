import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ReadFromServer implements Runnable{
//    private ObjectInputStream objectInputStream;
    private SocketChannel hello;
    private Selector ready=Selector.open();
    public ReadFromServer(SocketChannel hello) throws IOException {
            this.hello = hello;
        hello.configureBlocking(false);
            hello.register(ready,SelectionKey.OP_READ);
//            this.objectInputStream = new ObjectInputStream(hello.socket().getInputStream());
    }
    @Override
    public void run(){
        while(ClientCreate.iwork==true) {
            try {
                ready.select();
                Iterator keys=this.ready.selectedKeys().iterator();
                while(keys.hasNext()&&ClientCreate.iwork==true){
                    SelectionKey key=(SelectionKey) keys.next();
                    keys.remove();
                    if(key.isReadable()) {System.out.println(Client_love_Server.read(hello));}
                }
//                System.out.println(Client_love_Server.read(hello));
            } catch (IOException e) {
                ClientCreate.iwork=false;
            }
        }
    }
}
