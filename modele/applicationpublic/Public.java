package modele.applicationpublic;

import java.net.Socket;
import modele.serveur.Emission;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class Public {

    private Socket socket = null;
    private boolean attente = true;

    public Public(Socket socket) {
        this.socket = socket;
    }

    /* Getter */
    public String getAdresseIP() {
        return socket.getInetAddress().getHostAddress();
    }

    public boolean isAttente() {
        return attente;
    }

    /*Setter */
    public void setAttente(boolean attente) {
        JSONObject confirmation = new JSONObject();
        if (attente == true) {
            confirmation.put("attente", true);
        } else {
            confirmation.put("attente", false);
            confirmation.put("secondeAttente", ListePublic.getNombreSecondeAttente());
        }
        new Emission(socket, confirmation.toString());

        this.attente = attente;
    }

}
