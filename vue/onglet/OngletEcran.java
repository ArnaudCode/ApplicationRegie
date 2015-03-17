package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Parametre;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Arnaud
 */
public class OngletEcran extends JPanel {

    private String description = null;

    public OngletEcran() {
        //this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Réglage des paramètres du contrôle par le public";

        initialisation();
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Ecran");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel texteDescription = new JLabel(description);

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(), 
                ecrangbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(texteDescription, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        
        this.add(new EcranPanel("http://192.168.1.5:12345", "Rasp 1"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(new EcranPanel("http://192.168.1.4:12345", "Rasp 1"), gbc);
        
    }
    
    public class EcranPanel extends JPanel
    {
        private String stream, title;
        private EmbeddedMediaPlayer player;
        
        private JButton start, stop;
        protected EcranPanel(String stream, String title)
        {
            super();
            this.stream = stream;
            this.title = title;
            initComponents();
            this.setVisible(true);
        }
        
        private void initComponents()
        {
            this.setLayout(new GridBagLayout());
            GridBagConstraints cst = new GridBagConstraints();
            cst.gridx = 0;
            cst.gridy = 0;
            
            this.add(new JLabel(title),cst);
            
            cst.gridy++;
            cst.fill = GridBagConstraints.BOTH;
            cst.gridwidth = 2;
            EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
            mediaPlayerComponent.setPreferredSize(new Dimension(480, 270));
            this.player = mediaPlayerComponent.getMediaPlayer();
            this.add(mediaPlayerComponent, cst);
            
            cst.gridwidth = 1;
            cst.fill = GridBagConstraints.NONE;
            cst.gridy++;
            start = new JButton("Lire");
            stop = new JButton("Arrêter");
            stop.setEnabled(false);
            start.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    play();
                    start.setEnabled(false);
                    stop.setEnabled(true);
                }
            });
            this.add(start,cst);
            
            
            stop.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    stop();
                    stop.setEnabled(false);
                    start.setEnabled(true);
                }
            });
            cst.gridx++;
            this.add(stop, cst);
            
            //JFrame frame = new JFrame();
            //frame.add(mediaPlayerComponent);
            //frame.setVisible(true);
        }
        
        protected void play()
        {
            player.playMedia(stream);
            this.revalidate();
        }
        
        protected void stop()
        {
            player.stop();
        }
    }

}
