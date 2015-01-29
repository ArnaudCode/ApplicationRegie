package modele.applicationpublic;

import java.util.ArrayList;
import modele.Parametre;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class ListePublic {

    private static ArrayList<Public> liste = new ArrayList<Public>();
    private static int nombreSecondeAttente = Parametre.NOMBRE_SECONDE_ATTENTE;
    private static int nombreSecondeControle = Parametre.NOMBRE_SECONDE_CONTROLE;

    private static ArrayList<Observateur> listeObservateur = new ArrayList<>();

    private ListePublic() {
    }

    /* Getter */
    public static ArrayList<Public> getListe() {
        return liste;
    }

    public static int getNombreSecondeAttente() {
        return nombreSecondeAttente;
    }

    public static int getNombreSecondeControle() {
        return nombreSecondeControle;
    }

    /* Setter */
    public static void setListe(ArrayList<Public> liste) {
        ListePublic.liste = liste;
        notification();
    }

    public static void setNombreSecondeAttente(int nombreSecondeAttente) {
        ListePublic.nombreSecondeAttente = nombreSecondeAttente;
    }

    public static void setNombreSecondeControle(int nombreSecondeControle) {
        ListePublic.nombreSecondeControle = nombreSecondeControle;
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
