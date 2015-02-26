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
    private Module module = null;

    public Reception(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String ligne = null;

            while (module == null) {
                //Première connection
                ligne = in.readLine();
                if(ligne == null)
                    continue;
                System.out.println(ligne);
                module = Module.DetectionModule(ligne, socket);
            }

            while (!Thread.currentThread().isInterrupted() && (ligne = in.readLine()) != null) {
                System.out.println(ligne);
                module.traitement(ligne);
            }

            if (module != null) {
                module.stop();
            }
            socket.close();
            System.out.println("Fin de reception.");
        } catch (Exception e) {
            new Erreur(e.getMessage());
            module.stop();
        }
    }

}
