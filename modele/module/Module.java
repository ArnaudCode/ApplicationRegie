package modele.module;

import java.net.SocketAddress;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class Module {

    private String requete = null;
    private JSONObject json = null;
    private SocketAddress adresseIP = null;

    public Module(String requete, SocketAddress adresseIP) {
        this.requete = requete;
        this.adresseIP = adresseIP;

        json = new JSONObject(requete);

        switch (json.get("idModule").toString()) {
            case "localisation":
                new ModuleLocalisation(json);
                break;

            case "public":
                new ModulePublic(json, adresseIP);
                break;

            default:
                System.out.println("idModule non reconnu : " + json.get("idModule").toString());
                break;
        }
    }

}
