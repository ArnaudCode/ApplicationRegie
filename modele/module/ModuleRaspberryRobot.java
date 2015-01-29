package modele.module;

import java.net.Socket;
import modele.robot.ListeRobot;
import modele.robot.Robot;
import org.json.JSONObject;
import vue.Erreur;

/**
 *
 * @author Arnaud
 */
public class ModuleRaspberryRobot extends Module {

    private JSONObject json = null;
    private Socket socket = null;

    public ModuleRaspberryRobot(JSONObject json, Socket socket) {
        this.json = json;
        this.socket = socket;

        if (json.get("action").equals("init")) {
            Robot nouveauRobot = new Robot(0); //Automatiquement +1 dû au size()
            nouveauRobot.setSocket(socket);
            ListeRobot.getListe().add(0, nouveauRobot);
            System.out.println("Rasp ajoute");
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
