package modele.module;

import java.net.Socket;
import java.util.ArrayList;
import modele.robot.ListeRobot;
import modele.robot.Robot;
import modele.serveur.Emission;
import org.json.JSONArray;
import org.json.JSONObject;
import vue.Erreur;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class ModuleLocalisation extends Module {

    private JSONObject json = null;
    private static Socket socket = null;
    public static boolean calibrageEffectue = false;
    public static boolean localisationStart = false;

    private static ArrayList<Observateur> listeObservateur = new ArrayList<>();

    public ModuleLocalisation(JSONObject json, Socket socket) {
        this.json = json;
        this.socket = socket;

        if (json.get("action").equals("init")) {
        } else {
            new Erreur("Première connexion inccorecte :\nPas de action: init");
        }

        notification();
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

                                calibrageEffectue = true;
                                notification();
                            } else {
                                String message = json.optString("message");

                                if (message != null) {
                                    new Erreur(message.toString());
                                } else {
                                    new Erreur("Problème localisation :\nNok sans message");
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
        JSONObject demande = new JSONObject();
        demande.put("action", "calibrage");
        demande.put("valeur", ((double) valeur) / 100.0); //En mètres
        demande.put("nbRobots", nbRobots);
        new Emission(socket, demande.toString());
    }

    public static void startLocalisation() {
        /* Demande a l'appli localisation de commencer la localisation */
        JSONObject start = new JSONObject();
        start.put("action", "start");
        new Emission(socket, start.toString());
        localisationStart = true;
        notification();
    }

    public static void stopLocalisation() {
        /* Demande a l'appli localisation d'arreter la localisation */
        JSONObject stop = new JSONObject();
        stop.put("action", "stop");
        try {
            new Emission(socket, stop.toString());
        } catch (Exception e) {
            new Erreur("Application Localisation déjà arrêtée");
        }
        localisationStart = false;
        notification();
    }

    public static boolean estConnecte() {
        /* Retourn true si l'application localisation a été lancée */
        if (socket == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void ajouterObservateur(Observateur o) {
        listeObservateur.add(o);
    }

    public static void supprimerObservateur(Observateur o) {
        listeObservateur.remove(o);
    }

    public static void notification() {
        for (Observateur o : listeObservateur) {
            o.miseAJour();
        }
    }
}
