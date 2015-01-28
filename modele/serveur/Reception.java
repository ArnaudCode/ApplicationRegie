package modele.serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import modele.module.Module;

/**
 *
 * @author Arnaud
 */
public class Reception implements Runnable {

    private Socket socket = null;
    private Module module = null;

    public Reception(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String ligne = null;
            while (!Thread.currentThread().isInterrupted() && (ligne = in.readLine()) != null) {
                System.out.println(ligne);
                module = Module.DetectionModule(ligne, socket);
            }

            module.stop();
            socket.close();
            System.out.println("Fin de reception.");
        } catch (Exception e) {
            e.printStackTrace();
            module.stop();
        }
    }

}
