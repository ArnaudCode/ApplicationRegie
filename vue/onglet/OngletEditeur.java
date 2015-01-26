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
public class OngletEditeur extends JPanel {

    public OngletEditeur() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        initialisation();
    }

    private void initialisation() {
        JLabel titre = new JLabel("Editeur");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel description = new JLabel("Editer les obstacles des scènes.");

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(description, gbc);
    }

}
