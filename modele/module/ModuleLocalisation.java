package modele.module;

import java.net.Socket;
import modele.robot.ListeRobot;
import modele.robot.Robot;
import modele.serveur.Emission;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class ModuleLocalisation extends Module {

    private JSONObject json = null;
    private Socket socket = null;

    public ModuleLocalisation(JSONObject json, Socket socket) {
        this.json = json;

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
                Robot nouveauRobot = new Robot(robot.getInt("id"));
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
    }

    @Override
    public void stop() {
    }
}
