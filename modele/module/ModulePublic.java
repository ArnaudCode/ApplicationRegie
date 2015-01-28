package modele.module;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.applicationpublic.ListePublic;
import modele.applicationpublic.Public;
import org.json.JSONObject;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public class ModulePublic extends Module {

    private JSONObject json = null;
    private Socket socket = null;
    private Public applicationpublic = null;

    public ModulePublic(JSONObject json, Socket socket) {
        this.json = json;
        this.socket = socket;

        /* Réception */
        boolean dejaPresent = false;
        for (Public p : ListePublic.getListe()) {
            if (p.getAdresseIP().equals(socket.getInetAddress().getHostAddress())) {
                dejaPresent = true;
            }
        }

        if (dejaPresent == false) {
            applicationpublic = new Public(socket);
            ListePublic.getListe().add(applicationpublic);

            /* Emision */
            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setAttente(true);
        } else {
            try {
                new Erreur("IP de l'application public déjà existante");
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ModulePublic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ListePublic.notification();
    }

    @Override
    public void stop() {
        ListePublic.getListe().remove(applicationpublic);
        ListePublic.notification();
    }

}
