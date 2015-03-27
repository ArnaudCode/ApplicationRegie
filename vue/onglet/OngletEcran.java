package vue.onglet;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modele.Parametre;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Arnaud
 */
public class OngletEcran extends JPanel
{

    private String description = null;
    private FullScreenVideoFrame projFrame;

    public OngletEcran()
    {
        //this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Réglage des paramètres du contrôle par le public";

        initialisation();
    }

    /* Getter */
    public String getDescription()
    {
        return description;
    }

    private void initialisation()
    {
        projFrame = new FullScreenVideoFrame();
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

        this.add(new EcranPanel("http://192.168.1.5:12345", "Rasp 1", projFrame), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(new EcranPanel("http://192.168.1.4:12345", "Rasp 1", projFrame), gbc);

    }

    public class EcranPanel extends JPanel
    {

        private String stream, title;
        private EmbeddedMediaPlayer player;
        private FullScreenVideoFrame projFrame;

        private JButton start, stop, proj, stopProj;

        protected EcranPanel(String stream, String title, FullScreenVideoFrame frame)
        {
            super();
            this.stream = stream;
            this.title = title;
            this.projFrame = frame;
            initComponents();
            this.setVisible(true);
        }

        private void initComponents()
        {
            this.setLayout(new GridBagLayout());
            GridBagConstraints cst = new GridBagConstraints();
            cst.gridx = 0;
            cst.gridy = 0;

            this.add(new JLabel(title), cst);

            cst.gridy++;
            cst.fill = GridBagConstraints.BOTH;
            cst.gridwidth = 4;
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
            
            proj = new JButton("Projeter");
            proj.setEnabled(false);
            stopProj = new JButton("Arrêter projection");
            stopProj.setEnabled(false);
            
            start.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    play();
                    start.setEnabled(false);
                    stop.setEnabled(true);
                    proj.setEnabled(true);
                }
            });
            this.add(start, cst);

            stop.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    stop();
                    stopProj();
                    stop.setEnabled(false);
                    start.setEnabled(true);
                    proj.setEnabled(false);
                    stopProj.setEnabled(false);
                }
            });
            cst.gridx++;
            this.add(stop, cst);
            
            proj.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    stopProj.setEnabled(true);
                    proj();
                }
            });
            
            cst.gridx++;
            this.add(proj, cst);
            
            stopProj.addActionListener(new ActionListener()
            {

                @Override
                public void actionPerformed(ActionEvent e)
                {
                    stopProj();
                    stopProj.setEnabled(false);
                }
            });
            
            cst.gridx++;
            this.add(stopProj, cst);

        }
        
        /**
         * Lit le flux (affiché dans l'onglet).
         */
        private void play()
        {
            player.playMedia(stream);
            this.revalidate();
        }
        
        /**
         * Arrête la lecture du flux.
         */
        private void stop()
        {
            player.stop();
        }
        
        /**
         * Projette le stream.
         */
        private void proj()
        {
            this.projFrame.playStream(stream);
        }
        
        /**
         * Arrête la projection.
         */
        private void stopProj()
        {
            this.projFrame.stop();
        }
    }

    public class FullScreenVideoFrame extends JFrame
    {

        private EmbeddedMediaPlayer player;

        public FullScreenVideoFrame()
        {
            super();
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            if(gs.length != 1)
            {
                this.setLocation(gs[1].getDefaultConfiguration().getBounds().getLocation());
            }
            this.setVisible(true);
            EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
            this.player = mediaPlayerComponent.getMediaPlayer();
            this.player.setFullScreenStrategy(
                    new DefaultAdaptiveRuntimeFullScreenStrategy(this)
            );
            this.setContentPane(mediaPlayerComponent);
            this.player.setFullScreen(true);
        }
        
        public void playStream(String stream)
        {
            this.player.playMedia(stream);
        }
        
        public void stop()
        {
            this.player.stop();
        }
    }

}
