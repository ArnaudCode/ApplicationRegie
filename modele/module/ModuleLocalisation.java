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

        JSONArray robots = json.getJSONArray("robots");

        ListeRobot.getListe().clear();

        for (int i = 0; i < robots.length(); i++) {
            ListeRobot.getListe().add(new Robot(i));
            JSONArray coordonnee = robots.getJSONArray(i);

            ListeRobot.getListe().get(i).setPositionX(coordonnee.getDouble(0)); //Coordonnée x
            ListeRobot.getListe().get(i).setPositionY(coordonnee.getDouble(1)); //Coordonnée y
        }

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
