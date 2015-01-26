package vue;

import vue.onglet.OngletCarte;
import vue.onglet.OngletControle;
import vue.onglet.OngletEcran;
import vue.onglet.OngletEditeur;
import vue.onglet.OngletRobot;
import vue.onglet.OngletServeur;
import vue.onglet.OngletSol;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import modele.Parametre;

/**
 *
 * @author Arnaud
 */
public class Fenetre extends JFrame {

    private JPanel panneau;

    public Fenetre() {
        super("Application Régie");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Parametre.LARGEUR, Parametre.HAUTEUR);

        initialisation();

        this.pack();
    }

    private void initialisation() {
        panneau = new JPanel();

        JTabbedPane barreOnglet = new JTabbedPane();

        /* Liste des onglets */
        OngletCarte ongletCarte = new OngletCarte();
        OngletControle ongletControle = new OngletControle();
        OngletEcran ongletEcran = new OngletEcran();
        OngletEditeur ongletEditeur = new OngletEditeur();
        OngletRobot ongletRobot = new OngletRobot();
        OngletServeur ongletServeur = new OngletServeur();
        OngletSol ongletSol = new OngletSol();

        barreOnglet.addTab("Serveur", ongletServeur);
        barreOnglet.addTab("Robots", ongletRobot);
        barreOnglet.addTab("Carte", ongletCarte);
        barreOnglet.addTab("Ecran", ongletEcran);
        barreOnglet.addTab("Sol", ongletSol);
        barreOnglet.addTab("Contrôle", ongletControle);
        barreOnglet.addTab("Editeur", ongletEditeur);

        panneau.add(barreOnglet);
        this.add(panneau);
    }
}
