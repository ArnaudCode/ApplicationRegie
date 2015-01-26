package modele.Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Arnaud
 */
public class AcceptationConnexion implements Runnable {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private Thread thread = null;

    public AcceptationConnexion(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                socket = serverSocket.accept();
                System.out.println("Une nouvelle connection.");

                thread = new Thread(new Reception(socket));
                thread.start();
            }
            thread.interrupt();
        } catch (IOException e) {
            System.out.println("Fin de connexion.");
        }
    }
}
