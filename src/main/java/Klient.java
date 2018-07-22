import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * @author Shafikov Almir
 */


 final class Klient {
    final private String SERVER_ADDRESS = "localhost";
    final private int SERVER_PORT = 9001;
    private Socket socket;
    private Scanner in, console;
    private PrintWriter out;

    protected Klient() {

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.hasNext()) {
                            String text = in.nextLine();
                            if (text.equalsIgnoreCase("End")) break;
                            System.out.println(text);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Good bye!");
                    System.exit(2);
                }
            }
        }).start();

        Messaging();
    }

    private final void Messaging() {
        String message;
        console = new Scanner(System.in);
        System.out.println("Enter text:");

        while (true) {
            message = console.next();
            out.println(message);
            out.flush();
            if (message.equalsIgnoreCase("END")) {
                try {
                    console.close();
                    out.close();
                    in.close();
                    socket.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}