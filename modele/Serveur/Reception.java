package modele.Serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Arnaud
 */
public class Reception implements Runnable {

    private Socket socket;

    public Reception(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String ligne;
            while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
                if ((ligne = in.readLine()) != null) {
                    System.out.println(ligne);
                }
            }
            socket.close();
            System.out.println("Fin reception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
