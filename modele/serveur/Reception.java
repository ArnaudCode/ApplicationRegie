package modele.serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import modele.module.Module;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public class Reception implements Runnable {

    private Socket socket = null;

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
                new Module(ligne);
            }

            socket.close();
            System.out.println("Fin de reception.");
        } catch (Exception e) {
            e.printStackTrace();
            new Erreur(e.getMessage());
        }
    }

}
