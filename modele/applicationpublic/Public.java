package modele.applicationpublic;

import java.net.SocketAddress;

/**
 *
 * @author Arnaud
 */
public class Public {

    private SocketAddress adresseIP = null;
    private boolean attente = true;

    public Public(SocketAddress adresseIP) {
        this.adresseIP = adresseIP;
    }

    /* Getter */
    public SocketAddress getAdresseIP() {
        return adresseIP;
    }

    public boolean isAttente() {
        return attente;
    }

    /*Setter */
    public void setAdresseIP(SocketAddress adresseIP) {
        this.adresseIP = adresseIP;
    }

    public void setAttente(boolean attente) {
        this.attente = attente;
    }

}
