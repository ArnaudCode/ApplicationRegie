package modele.module;

import java.net.Socket;
import org.json.JSONObject;

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

        if (json.get("etat").equals("pret")) {

        }
    }

    @Override
    public void stop() {
    }

}
