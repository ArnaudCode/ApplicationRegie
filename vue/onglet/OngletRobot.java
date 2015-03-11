package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import modele.Parametre;
import modele.module.ModuleLocalisation;

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
        valeurCalibrage.setPreferredSize(new Dimension(55, 25));
        gbc.gridx++;
        this.add(valeurCalibrage, gbc);
        
        JLabel uniteCalibrage = new JLabel(" centimètres");
        gbc.gridx++;
        this.add(uniteCalibrage, gbc);
        
        gbc.gridy++;
        gbc.gridx=0;
        this.add(new JLabel("Nombre de robots"),gbc);
        
        gbc.gridx++;
        JSpinner nbRobots = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
        this.add(nbRobots,gbc);

        JButton calibrage = new JButton("Calibrer");
        gbc.gridx = 0;
        gbc.gridy++;
        this.add(calibrage, gbc);

        calibrage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModuleLocalisation.demandeCalibrage((int) valeurCalibrage.getValue(),
                        (int)nbRobots.getValue());
            }
        });
    }

}
