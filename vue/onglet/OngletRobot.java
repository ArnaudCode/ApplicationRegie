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
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class OngletRobot extends JPanel implements Observateur {

    private String description = null;

    public OngletRobot() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Choix du mode de déplacement des robots";

        initialisation();

        ModuleLocalisation.ajouterObservateur(this);
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

        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Nombre de robots : "), gbc);

        gbc.gridx++;
        JSpinner nbRobots = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
        this.add(nbRobots, gbc);

        JLabel nombreRobots = new JLabel("robots");
        gbc.gridx++;
        this.add(nombreRobots, gbc);

        JButton calibrage = new JButton("Calibrer");
        gbc.gridx = 1;
        gbc.gridy++;
        this.add(calibrage, gbc);

        JLabel appliLoca = new JLabel("(L'Application Localisation n'est pas connectée)");
        appliLoca.setFont(new Font(appliLoca.getFont().getName(), Font.ITALIC, appliLoca.getFont().getSize()));
        gbc.gridx = 0;
        this.add(appliLoca, gbc);

        JButton start = new JButton("Start");
        gbc.gridx = 0;
        gbc.gridy++;
        this.add(start, gbc);

        JButton stop = new JButton("Stop");
        gbc.gridx = 1;
        this.add(stop, gbc);

        /* Activation des boutons */
        if (ModuleLocalisation.estConnecte()) {
            calibrage.setEnabled(true);
            appliLoca.setVisible(false);

            if (ModuleLocalisation.calibrageEffectue == true && ModuleLocalisation.localisationStart == false) {
                start.setEnabled(true);
            } else {
                start.setEnabled(false);
            }

            if (ModuleLocalisation.localisationStart == true) {
                stop.setEnabled(true);
            } else {
                stop.setEnabled(false);
            }
        } else {
            appliLoca.setVisible(true);
            calibrage.setEnabled(false);
            start.setEnabled(false);
            stop.setEnabled(false);
        }

        /* Listener */
        calibrage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModuleLocalisation.demandeCalibrage((int) valeurCalibrage.getValue(), (int) nbRobots.getValue());
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModuleLocalisation.startLocalisation();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModuleLocalisation.stopLocalisation();
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
