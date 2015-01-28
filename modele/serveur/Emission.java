package modele.serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public class Emission {

    public Emission(Socket socket, String message) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Emission.class.getName()).log(Level.SEVERE, null, ex);
            new Erreur(ex.getMessage());
        }
    }

}
