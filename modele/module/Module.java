package modele.module;

import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class Module {

    private String requete = null;
    private JSONObject json = null;

    public Module(String requete) {
        this.requete = requete;
        json = new JSONObject(requete);

        switch (json.get("idModule").toString()) {
            case "localisation":
                new ModuleLocalisation(json);
                break;

            case "public":
                break;

            default:
                System.out.println("idModule non reconnu : " + json.get("idModule").toString());
                break;
        }
    }

}
