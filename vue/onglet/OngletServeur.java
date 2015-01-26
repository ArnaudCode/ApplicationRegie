package vue.onglet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Parametre;
import modele.Serveur.Serveur;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class OngletServeur extends JPanel implements Observateur {

    private Serveur serveur;

    public OngletServeur() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        this.serveur = null;

        initialisation();
    }

    private void initialisation() {
        JLabel titre = new JLabel("Serveur");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel description = new JLabel("Gestion des connexions entre les modules.");

        JButton bouton = null;
        JLabel statut = null;
        JLabel adresseIP = null;
        if (Serveur.etat == false) {
            bouton = new JButton("Lancer");
            statut = new JLabel(" OFF");
            statut.setForeground(Color.RED);
        } else {
            bouton = new JButton("Arrêter");
            statut = new JLabel(" ON");
            statut.setForeground(Color.GREEN);
        }

        if (Serveur.adresse == null) {
            adresseIP = new JLabel("XXX.XXX.XXX.XXX" + ":" + Parametre.SERVEUR_PORT);
        } else {
            adresseIP = new JLabel(Serveur.adresse.getHostAddress() + ":" + Parametre.SERVEUR_PORT);
        }

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(description, gbc);

        gbc.weighty = 2;
        gbc.gridy++;
        this.add(bouton, gbc);

        gbc.weighty = 1;
        gbc.gridx++;
        this.add(adresseIP, gbc);

        gbc.weighty = 1;
        gbc.gridx++;
        this.add(statut, gbc);

        /* Action Listener */
        OngletServeur ongletServeur = this;
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serveur == null) {
                    serveur = new Serveur();
                    serveur.ajouterObservateur(ongletServeur);
                } else {
                    serveur.stop();
                    serveur.notification();
                }
            }
        });
    }

    @Override
    public void miseAJour() {
        this.removeAll();
        initialisation();
        this.updateUI();
    }
}
