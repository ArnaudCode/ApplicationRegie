package modele.module;

import java.net.SocketAddress;
import modele.applicationpublic.ListePublic;
import modele.applicationpublic.Public;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class ModulePublic extends Module {

    private JSONObject json = null;
    private SocketAddress adresseIP = null;
    private Public applicationpublic = null;

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
            applicationpublic = new Public(adresseIP);
            ListePublic.getListe().add(applicationpublic);
        }

        ListePublic.notification();
    }

    @Override
    public void stop() {
        ListePublic.getListe().remove(applicationpublic);
        ListePublic.notification();
    }

}
