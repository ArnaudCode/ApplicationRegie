package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Parametre;

/**
 *
 * @author Arnaud
 */
public class OngletSol extends JPanel {

    private String description = null;

    public OngletSol() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Sélection du flux vidéo à projeter au sol";

        initialisation();
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Sol");
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
    }

}
