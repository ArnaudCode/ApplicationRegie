package modele.serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import modele.Observable;
import modele.Parametre;
import vue.Erreur;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class Serveur implements Observable {

    private ServerSocket serveurSocket = null;
    private Thread thread = null;

    private ArrayList<Observateur> listeObservateur = new ArrayList<>();

    private static boolean etat = false; //False = OFF - True = ON
    private static InetAddress adresse = null;

    public Serveur() {
        try {
            serveurSocket = new ServerSocket(Parametre.SERVEUR_PORT);

            thread = new Thread(new AcceptationConnexion(serveurSocket));
            thread.start();

            etat = true;
            adresse = InetAddress.getLocalHost();
        } catch (IOException e) {
            new Erreur("Port " + Parametre.SERVEUR_PORT + " déjà utilisé\nL'application est probablement déjà en cours d'éxécution");
        }
    }

    /* Getter */
    public static boolean getEtat() {
        return etat;
    }

    public static InetAddress getAdresse() {
        return adresse;
    }

    /* Fonction */
    public void stop() {
        try {
            thread.interrupt();
            serveurSocket.close();

            etat = false;
        } catch (Exception e) {
            e.printStackTrace();
            new Erreur(e.getMessage());
        }
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        listeObservateur.add(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        listeObservateur.remove(o);
    }

    @Override
    public void notification() {
        for (Observateur o : listeObservateur) {
            o.miseAJour();
        }
    }

}
