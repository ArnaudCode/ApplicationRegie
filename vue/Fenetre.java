package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import modele.Parametre;
import vue.onglet.OngletCarte;
import vue.onglet.OngletControle;
import vue.onglet.OngletEcran;
import vue.onglet.OngletEditeur;
import vue.onglet.OngletRobot;
import vue.onglet.OngletServeur;
import vue.onglet.OngletSol;

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

        barreOnglet.addTab("Serveur", null, ongletServeur, ongletServeur.getDescription());
        barreOnglet.addTab("Robots", null, ongletRobot, ongletRobot.getDescription());
        barreOnglet.addTab("Carte", null, ongletCarte, ongletCarte.getDescription());
        barreOnglet.addTab("Ecran", null, ongletEcran, ongletEcran.getDescription());
        barreOnglet.addTab("Sol", null, ongletSol, ongletSol.getDescription());
        barreOnglet.addTab("Contrôle", null, ongletControle, ongletControle.getDescription());
        barreOnglet.addTab("Editeur", null, ongletEditeur, ongletEditeur.getDescription());

        panneau.add(barreOnglet);

        this.setContentPane(panneau);
    }
}
