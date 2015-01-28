package modele.module;

import java.net.Socket;
import org.json.JSONObject;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public abstract class Module {

    public static Module DetectionModule(String requete, Socket socket) {

        try {
            JSONObject json = new JSONObject(requete);

            switch (json.get("idModule").toString()) {
                case "localisation":
                    return new ModuleLocalisation(json);

                case "public":
                    return new ModulePublic(json, socket);

                default:
                    System.out.println("idModule non reconnu : " + json.get("idModule").toString());
                    break;
            }
        } catch (Exception e) {
            new Erreur("Le message reçu n'est pas au format JSON.");
        }

        return null;
    }

    public abstract void stop();
}
