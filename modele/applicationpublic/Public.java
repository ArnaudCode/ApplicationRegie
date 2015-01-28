package modele.applicationpublic;

import java.net.Socket;
import java.net.SocketAddress;
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
    public SocketAddress getAdresseIP() {
        return socket.getRemoteSocketAddress();
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
        }
        new Emission(socket, confirmation.toString());

        this.attente = attente;
    }

}
