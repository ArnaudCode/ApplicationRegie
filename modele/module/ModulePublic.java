package modele.module;

import java.net.Socket;
import modele.applicationpublic.ListePublic;
import modele.applicationpublic.Public;
import modele.robot.ListeRobot;
import modele.serveur.Emission;
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

        if (json.get("action").equals("init")) {
            /* Réception */
            applicationpublic = new Public(socket);
            ListePublic.getListe().add(applicationpublic);

            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setAttente(true);
            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setControle(false);

            /* Emision */
            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).envoieSecondes();

            ListePublic.notification();
        } else {
            new Erreur("Première connexion inccorecte :\nPas de action: init");
        }
    }

    @Override
    public void traitement(String ligne) {
        JSONObject json = new JSONObject(ligne);

        try {
            JSONObject detail = json.getJSONObject("detail");

            String action = json.getString("action");
            String vitesse = String.valueOf(json.getInt("vitesse"));

            JSONObject emission = new JSONObject();
            emission.put("action", action);
            emission.put("vitesse", vitesse);

            new Emission(ListeRobot.getListe().get(0).getScoket(), emission.toString());
        } catch (Exception e) {
            try {
                String action = json.getString("action");
                if (action.equals("fin")) {
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setAttente(true);
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setControle(false);

                    /* Emision */
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).envoieSecondes();
                }
            } catch (Exception ee) {

            }
        }

    }

    @Override
    public void stop() {
        ListePublic.getListe().remove(applicationpublic);
        ListePublic.notification();
    }

}
