package modele.Serveur;

import java.io.IOException;
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

    private ServerSocket serveurSocket = null;
    private Thread thread = null;

    private ArrayList<Observateur> listeObservateur = new ArrayList<>();

    public static boolean etat = false; //False = OFF - True = ON
    public static InetAddress adresse = null;

    public Serveur() {
        try {
            serveurSocket = new ServerSocket(Parametre.SERVEUR_PORT);

            thread = new Thread(new AcceptationConnexion(serveurSocket));
            thread.start();

            adresse = InetAddress.getLocalHost();
            etat = true;
        } catch (IOException e) {
            System.err.println("Port " + serveurSocket.getLocalPort() + " deja utilise.");
        }
    }

    public void stop() {
        try {
            thread.interrupt();
            serveurSocket.close();

            etat = false;
        } catch (Exception e) {
            e.printStackTrace();
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
