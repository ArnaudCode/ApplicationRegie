package modele.module;

import java.net.SocketAddress;
import modele.applicationpublic.ListePublic;
import modele.applicationpublic.Public;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class ModulePublic {

    private JSONObject json = null;
    private SocketAddress adresseIP = null;

    public ModulePublic(JSONObject json, SocketAddress adresseIP) {
        this.json = json;
        this.adresseIP = adresseIP;

        boolean dejaPresent = false;
        for (Public p : ListePublic.getListe()) {
            if (p.getAdresseIP() == adresseIP) {
                dejaPresent = true;
            }
        }

        if (dejaPresent == false) {
            ListePublic.getListe().add(new Public(adresseIP));
        }

        ListePublic.notification();
    }

}
