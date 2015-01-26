package modele.Serveur;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Arnaud
 */
public class AcceptationConnexion implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    private Thread thread;

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
            System.out.println("Fin de connection.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
