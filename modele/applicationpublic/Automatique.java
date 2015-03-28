package modele.applicationpublic;

import java.util.ArrayList;
import modele.robot.ListeRobot;
import vue.Observateur;

/**
 *
 * @author Aurélien
 */
public class Automatique extends Thread {

    private boolean fin = false;

    private static ArrayList<Observateur> listeObservateur = new ArrayList<>();

    public Automatique() {
    }

    @Override
    public void run() {
        System.out.println("Debut mode automatique");

        while (!fin) {

            if (ListePublic.getListe() != null && !ListePublic.getListe().isEmpty()) { //Si la liste n'est pas vide
                if (ListePublic.getListe().get(0).isAttente() && !ListePublic.getListe().get(0).isControle() && ListeRobot.getListe().get(0).getAdresseIpPublic().isEmpty()) { //Si le premier public de la liste ne controle pas déjà, alors on lui donne le controle
                    ListePublic.getListe().get(0).setAttente(!ListePublic.getListe().get(0).isAttente());
                    ListePublic.getListe().get(0).setControle(!ListePublic.getListe().get(0).isControle());
                    if (ListePublic.getListe().get(0).envoieSecondes()) {
                        ListePublic.getListe().add(ListePublic.getListe().remove(0));
                        notification();
                    } else {
                        ListePublic.getListe().get(0).setAttente(!ListePublic.getListe().get(0).isAttente());
                        ListePublic.getListe().get(0).setControle(!ListePublic.getListe().get(0).isControle());
                    }
                }
            }

            //Empecher au thread l'envoi seconde pendant 2 secondes
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void quitter() {
        fin = true;
        System.out.println("Fin mode automatique");
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
