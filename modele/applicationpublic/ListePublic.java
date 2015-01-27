package modele.applicationpublic;

import java.util.ArrayList;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class ListePublic {

    private static ArrayList<Public> liste = new ArrayList<Public>();

    private static ArrayList<Observateur> listeObservateur = new ArrayList<>();

    private ListePublic() {
    }

    public static void setListe(ArrayList<Public> liste) {
        ListePublic.liste = liste;
        notification();
    }

    public static ArrayList<Public> getListe() {
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
