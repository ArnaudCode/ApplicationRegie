package modele.applicationpublic;

import java.net.Socket;
import java.net.SocketAddress;

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
        this.attente = attente;
    }

}
