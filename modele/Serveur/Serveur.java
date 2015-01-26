package modele.Serveur;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import modele.Observable;
import modele.Parametre;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class Serveur implements Observable {

    private ServerSocket serveurSocket;
    private Thread thread;

    private ArrayList<Observateur> listeObservateur;

    public static boolean etat = false; //False = OFF - True = ON
    public static InetAddress adresse = null;

    public Serveur() {
        listeObservateur = new ArrayList<>();

        serveurSocket = null;
        thread = null;

        try {
            serveurSocket = new ServerSocket(Parametre.SERVEUR_PORT);

            thread = new Thread(new AcceptationConnexion(serveurSocket));
            thread.start();

            adresse = InetAddress.getLocalHost();
            etat = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        try {
            thread.interrupt();
            serveurSocket.close();

            etat = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        listeObservateur.add(o);
        this.notification();
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        listeObservateur.remove(o);
        this.notification();
    }

    @Override
    public void notification() {
        for (Observateur o : listeObservateur) {
            o.miseAJour();
        }
    }

}
