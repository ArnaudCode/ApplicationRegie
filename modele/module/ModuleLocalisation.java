package modele.module;

import java.net.Socket;
import modele.robot.ListeRobot;
import modele.robot.Robot;
import modele.serveur.Emission;
import org.json.JSONArray;
import org.json.JSONObject;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public class ModuleLocalisation extends Module {

    private JSONObject json = null;
    private Socket socket = null;

    public ModuleLocalisation(JSONObject json, Socket socket) {
        this.json = json;

        if (json.get("action").equals("init")) {
            JSONArray listeRobots = json.getJSONArray("robots");

            for (int i = 0; i < listeRobots.length(); i++) {
                JSONObject robot = listeRobots.getJSONObject(i);

                boolean existeDeja = false;
                for (Robot r : ListeRobot.getListe()) {
                    if (r.getNumero() == robot.getInt("id")) {
                        existeDeja = true;
                        r.setPositionX(robot.getDouble("x"));
                        r.setPositionY(robot.getDouble("y"));
                    }
                }

                if (existeDeja == false) {
                    Robot nouveauRobot = new Robot(robot.getInt("id")); //Pour les tests, ne doit pas être créé ici (uniquement lors de la reception du RaspberryRobot)
                    nouveauRobot.setPositionX(robot.getDouble("x"));
                    nouveauRobot.setPositionY(robot.getDouble("y"));
                    ListeRobot.getListe().add(nouveauRobot);
                }
            }

            /* Emmision d'une confirmation */
            JSONObject confirmation = new JSONObject();
            confirmation.put("action", "calibrage");
            confirmation.put("valeur", 25.3);

            new Emission(socket, confirmation.toString());

            ListeRobot.notification();
        } else {
            new Erreur("Première connexion inccorecte :\nPas de action: init");
        }
    }

    @Override
    public void traitement(String ligne) {
        JSONObject json = new JSONObject(ligne);

    }

    @Override
    public void stop() {
    }

}
