package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

        JList jlist = null;
        if (!ListePublic.getListe().isEmpty()) {
            String listePublic[] = new String[ListePublic.getListe().size()];

            for (int i = 0; i < ListePublic.getListe().size(); i++) {
                listePublic[i] = (i + 1) + " - " + ListePublic.getListe().get(i).getAdresseIP().toString();
            }

            jlist = new JList(listePublic);
        } else {
            jlist = new JList();
        }
        JScrollPane jscollbar = new JScrollPane(jlist);
        jscollbar.setPreferredSize(new Dimension(200, 300));

        gbc.gridy++;
        this.add(jscollbar, gbc);

        JLabel secondeAttente = new JLabel("Secondes d'attente : ");
        gbc.gridx++;
        this.add(secondeAttente, gbc);

        JSpinner spinner = new JSpinner();
        spinner.setValue(Parametre.NOMBRE_SECONDE_ATTENTE);
        spinner.setPreferredSize(new Dimension(40, 25));
        gbc.gridx++;
        this.add(spinner, gbc);

        /* Listener */
        jlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList liste = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = liste.locationToIndex(evt.getPoint());

                    ListePublic.getListe().get(index).setAttente(!ListePublic.getListe().get(index).isAttente());
                }
            }
        });

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ListePublic.setNombreSecondeAttente((int) ((JSpinner) e.getSource()).getValue());
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
