package modele.applicationpublic;

import java.net.Socket;
import modele.robot.ListeRobot;
import modele.serveur.Emission;
import org.json.JSONObject;

/**
 *
 * @author Arnaud
 */
public class Public {

    private Socket socket = null;
    private boolean attente = true;
    private boolean controle = false;

    public Public(Socket socket) {
        this.socket = socket;
    }

    /* Getter */
    public String getAdresseIP() {
        return socket.getInetAddress().getHostAddress();
    }

    public boolean isAttente() {
        return attente;
    }

    public boolean isControle() {
        return controle;
    }

    /*Setter */
    public void setAttente(boolean attente) {
        this.attente = attente;
    }

    public void setControle(boolean controle) {
        this.controle = controle;
    }

    public boolean envoieSecondes() {
        JSONObject confirmation = new JSONObject();
        if (attente == false && controle == true) {
            //Recherche d'un robot a controller
            for (int i = 0; i < ListeRobot.getListe().size(); i++) {
                if (ListeRobot.getListe().get(i).getAdresseIpPublic().isEmpty()) {

                    ListeRobot.getListe().get(i).setAdresseIpPublic(this.getAdresseIP());

                    switch (ListeRobot.getListe().get(i).getNumero()) {
                        case 0:
                            confirmation.put("couleurRobot", "rouge");
                            break;
                        case 1:
                            confirmation.put("couleurRobot", "bleu");
                            break;
                        case 2:
                            confirmation.put("couleurRobot", "vert");
                            break;
                        default:
                            confirmation.put("couleurRobot", "blanc");
                            break;
                    }

                    confirmation.put("attente", false);
                    confirmation.put("secondeAttente", ListePublic.getNombreSecondeAttente());

                    confirmation.put("secondeControle", ListePublic.getNombreSecondeControle());
                    new Emission(socket, confirmation.toString());

                    return true;
                }
            }
        } else {
            confirmation.put("attente", true);
            new Emission(socket, confirmation.toString());
        }

        return false;
    }
}
