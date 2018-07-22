/**
 * @author Shafikov Almir
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

final class serverLog {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private BufferedReader console = null;

  final   void start() {
        try {
        serverSocket = new ServerSocket(9001);
        } catch (IOException e) {
            System.out.println("Can't open port 9001");
            System.exit(1);
        }
        System.out.print("Server started. Waiting for a client.");
    }

  final   void catchClient() throws IOException {
        try {
            socket = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(1);
        }
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
        System.out.println("Wait for messages...");
    }

  final   void sendMessageServer(String msg)  {
       out.println("Server: " + msg);
    }
  final void sendMessageKlient(String misg) throws IOException {
        System.out.println("Klient: " + misg);
        if (misg.equalsIgnoreCase("END")) close();
    }
  final void close() throws IOException {
        out.close();

        in.close();
        socket.close();
        serverSocket.close();
        System.exit(1);
    }


   final void writeToConsole() throws IOException {
        while (true) {
            console = new BufferedReader(new InputStreamReader(System.in));
            String txt = console.readLine();
            sendMessageServer(txt);
        }
    }
    final public BufferedReader getIn() {
        return in;
    }

}
