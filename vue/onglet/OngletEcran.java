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
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Arnaud
 */
public class OngletEcran extends JPanel {

    private static String[] IPs = {"http://192.168.1.5:8080/", "http://192.168.1.4:8080/"};
    private String description = null;
    private FullScreenVideoFrame projFrame;
    protected static String screenshotURL;

    public OngletEcran() {
        //this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Réglage des paramètres du contrôle par le public";
        new NativeDiscovery().discover();
        initialisation();
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        projFrame = new FullScreenVideoFrame();
        JLabel titre = new JLabel("Ecran");
        titre.setFont(titre.getFont().deriveFont(Font.BOLD));
        JLabel texteDescription = new JLabel(description);

        /* Placement des composants */
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(titre, gbc);

        gbc.gridy++;
        this.add(texteDescription, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        JButton stopProj = new JButton("Arrêter la projection");
        stopProj.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                projFrame.stop();
                screenshotURL = null;
            }
        });
        gbc.gridy++;
        this.add(stopProj, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        this.add(new EcranPanel(0, "Rasp 1", projFrame), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(new EcranPanel(1, "Rasp 1", projFrame), gbc);

    }

    public static BufferedImage screenshot() {
        BufferedImage ret = null;
        if (screenshotURL != null) {
            try {
                URL url = new URL(screenshotURL);
                ret = ImageIO.read(url);
            } catch (Exception ex) {
                ex.printStackTrace();
                ret = null;
            }
        }
        return ret;
    }

    public class EcranPanel extends JPanel {

        private String title;
        private int stream;
        private EmbeddedMediaPlayer player;
        private FullScreenVideoFrame projFrame;

        private JButton start, stop, proj;

        protected EcranPanel(int stream, String title, FullScreenVideoFrame frame) {
            super();
            this.stream = stream;
            this.title = title;
            this.projFrame = frame;
            initComponents();
            this.setVisible(true);
        }

        private void initComponents() {
            this.setLayout(new GridBagLayout());
            GridBagConstraints cst = new GridBagConstraints();
            cst.gridx = 0;
            cst.gridy = 0;

            this.add(new JLabel(title), cst);

            cst.gridy++;
            cst.fill = GridBagConstraints.BOTH;
            cst.gridwidth = 3;
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

            start.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    play();
                    start.setEnabled(false);
                    stop.setEnabled(true);
                }
            });
            this.add(start, cst);

            stop.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    stop();
                    stop.setEnabled(false);
                    start.setEnabled(true);
                }
            });
            cst.gridx++;
            this.add(stop, cst);

            proj.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    projFrame.playStream(IPs[stream] + "?action=stream");
                    screenshotURL = IPs[stream] + "?action=snapshot";
                }
            });

            cst.gridx++;
            this.add(proj, cst);
        }

        /**
         * Lit le flux (affiché dans l'onglet).
         */
        private void play() {
            player.stop();
            player.playMedia(IPs[0] + "?action=stream");
        }

        /**
         * Arrête la lecture du flux.
         */
        private void stop() {
            player.stop();
        }
    }

    public class FullScreenVideoFrame extends JFrame {

        private EmbeddedMediaPlayer player;

        public FullScreenVideoFrame() {
            super();
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();
            if (gs.length != 1) {
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

        public void playStream(String stream) {
            player.stop();
            this.player.playMedia(stream);
        }

        public void stop() {
            this.player.stop();
        }
    }

}
