package modele.module;

import java.net.SocketAddress;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public abstract class Module {

    public static Module DetectionModule(String requete, SocketAddress adresseIP) {
        JSONObject json = new JSONObject(requete);

        switch (json.get("idModule").toString()) {
            case "localisation":
                return new ModuleLocalisation(json);

            case "public":
                return new ModulePublic(json, adresseIP);

            default:
                System.out.println("idModule non reconnu : " + json.get("idModule").toString());
                break;
        }

        return null;
    }

    public abstract void stop();
}
