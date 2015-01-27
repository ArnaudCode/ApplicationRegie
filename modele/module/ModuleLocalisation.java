package modele.module;

import modele.robot.ListeRobot;
import modele.robot.Robot;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class ModuleLocalisation extends Module {

    private JSONObject json = null;

    public ModuleLocalisation(JSONObject json) {
        this.json = json;

        JSONArray robots = json.getJSONArray("robots");

        ListeRobot.getListe().clear();

        for (int i = 0; i < robots.length(); i++) {
            ListeRobot.getListe().add(new Robot(i));
            JSONArray coordonnee = robots.getJSONArray(i);

            ListeRobot.getListe().get(i).setPositionX(coordonnee.getDouble(0)); //Coordonnée x
            ListeRobot.getListe().get(i).setPositionY(coordonnee.getDouble(1)); //Coordonnée y
        }

        ListeRobot.notification();
    }

    @Override
    public void stop() {
    }
}
