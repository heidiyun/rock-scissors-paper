import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Client {
    private OutputStream os;
    private InputStream is;

    public Client() throws IOException {
        Socket socket = new Socket();

        InetSocketAddress endPoint =
                new InetSocketAddress("localhost", 5000);

        socket.connect(endPoint);

        os = socket.getOutputStream();
        is = socket.getInputStream();
    }

    public OutputStream getOs() {
        return os;
    }

    public InputStream getIs() {
        return is;
    }
}

public class Program {
    public static void main(String[] args) throws IOException {
        GameClient.main("GameClient");


    }
}
