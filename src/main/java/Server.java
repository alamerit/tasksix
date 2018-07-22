/**
 * @author Shafikov Almir
 */

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        final serverLog log = new serverLog();
        log.start();
        log.catchClient();
        new Thread() {
         final public void run() {
                while (true) {
                    String text = null;
                    try {
                        text = log.getIn().readLine();
                    } catch (IOException e) {
                        try {
                            log.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (text != null) {
                        try {
                            log.sendMessageKlient(text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        new Thread() {
         final public void run() {
                try {
                    log.writeToConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

