package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modele.Parametre;
import modele.applicationpublic.ListePublic;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class OngletControle extends JPanel implements Observateur {

    private String description = null;

    public OngletControle() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Réglage des paramètres du contrôle par le public";

        initialisation();

        ListePublic.ajouterObservateur(this);
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Contrôle");
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

        if (!ListePublic.getListe().isEmpty()) {
            String listePublic[] = new String[ListePublic.getListe().size()];

            for (int i = 0; i < ListePublic.getListe().size(); i++) {
                listePublic[i] = (i + 1) + " - " + ListePublic.getListe().get(i).getAdresseIP().toString().substring(1);
            }

            JList jlist = new JList(listePublic);
            JScrollPane jscollbar = new JScrollPane(jlist);
            jscollbar.setPreferredSize(new Dimension(200, 300));

            gbc.gridy++;
            this.add(jscollbar, gbc);
        }
    }

    @Override
    public void miseAJour() {
        this.removeAll();
        initialisation();
        this.updateUI();
    }

}
