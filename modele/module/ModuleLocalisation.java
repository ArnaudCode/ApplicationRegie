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
    private static Socket socket = null;

    public ModuleLocalisation(JSONObject json, Socket socket) {
        this.json = json;
        this.socket = socket;

        if (json.get("action").equals("init")) {
        } else {
            new Erreur("Première connexion inccorecte :\nPas de action: init");
        }
    }

    @Override
    public void traitement(String ligne) {
        try {
            JSONObject json = new JSONObject(ligne);

            String action = json.optString("action", null);
            if (action != null) {
                switch (action) {
                    case "calibrage":
                        String statut = json.optString("statut");

                        if (statut != null) {
                            if (statut.equals("OK")) {
                                JSONObject confirmation = new JSONObject();

                                confirmation.put("action", "start");
                                new Emission(socket, confirmation.toString());
                            } else {
                                String message = json.optString("message");

                                if (message != null) {
                                    new Erreur(message.toString());
                                } else {
                                    new Erreur("Problème localisation :\nNok sans message.");
                                }
                            }
                        }
                        break;
                    default:
                        new Erreur("Probleme reception localisation");
                        break;
                }
            } else {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListeRobot.notification();
    }

    @Override
    public void stop() {
    }

    public static void demandeCalibrage(int valeur, int nbRobots) {
        /* Emmission d'une demande de calibrage avec la valeur */
        JSONObject confirmation = new JSONObject();
        confirmation.put("action", "calibrage");
        confirmation.put("valeur", ((double) valeur) / 100.0); //En mètres
        confirmation.put("nbRobots", nbRobots);
        new Emission(socket, confirmation.toString());
    }

}
