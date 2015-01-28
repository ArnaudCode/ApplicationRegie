package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import modele.Parametre;

/**
 *
 * @author Arnaud
 */
public class OngletRobot extends JPanel {

    private String description = null;

    public OngletRobot() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Choix du mode de déplacement des robots";

        initialisation();
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Robots");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel texteDescription = new JLabel(description);

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(texteDescription, gbc);

        JLabel distanceCalibrage = new JLabel("Distance de calibrage : ");
        gbc.gridy++;
        this.add(distanceCalibrage, gbc);

        JSpinner valeurCalibrage = new JSpinner();
        valeurCalibrage.setValue(100);
        valeurCalibrage.setPreferredSize(new Dimension(50, 25));
        gbc.gridx++;
        this.add(valeurCalibrage, gbc);

        JLabel uniteCalibrage = new JLabel(" centimètres");
        gbc.gridx++;
        this.add(uniteCalibrage, gbc);

        JButton calbrage = new JButton("Calibrer");
        gbc.gridx = 0;
        gbc.gridy++;
        this.add(calbrage, gbc);
    }

}
