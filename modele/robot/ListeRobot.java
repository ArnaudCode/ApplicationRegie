package modele.robot;

import java.util.ArrayList;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class ListeRobot {

    private static ArrayList<Robot> liste = new ArrayList<Robot>();

    private static ArrayList<Observateur> listeObservateur = new ArrayList<>();

    private ListeRobot() {
    }

    public static void setListe(ArrayList<Robot> liste) {
        ListeRobot.liste = liste;
        notification();
    }

    public static ArrayList<Robot> getListe() {
        return liste;
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
