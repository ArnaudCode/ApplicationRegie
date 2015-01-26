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
import modele.serveur2.Serveur;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class OngletServeur extends JPanel implements Observateur {

    private String description = null;
    private Serveur serveur;

    public OngletServeur() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Gestion des connexions entre les modules";
        this.serveur = null;

        initialisation();
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Serveur");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel texteDescription = new JLabel(description);

        JButton bouton = null;
        JLabel statut = null;
        JLabel adresseIP = null;
        if (Serveur.getEtat() == false) {
            bouton = new JButton("Lancer");
            statut = new JLabel(" OFF");
            statut.setFont(statut.getFont().deriveFont(Font.BOLD));
            statut.setForeground(Color.RED);
        } else {
            bouton = new JButton("Arrêter");
            statut = new JLabel(" ON");
            statut.setFont(statut.getFont().deriveFont(Font.BOLD));
            statut.setForeground(Color.GREEN);
        }

        if (Serveur.getAdresse() == null) {
            adresseIP = new JLabel("xxx.xxx.xxx.xxx" + ":" + Parametre.SERVEUR_PORT + " (x)");
        } else {
            adresseIP = new JLabel(Serveur.getAdresse().getHostAddress() + ":" + Parametre.SERVEUR_PORT + " (" + Serveur.getAdresse().getHostName() + ")");
        }

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(texteDescription, gbc);

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
                    serveur = null;
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
