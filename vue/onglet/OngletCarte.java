package vue.onglet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Parametre;
import modele.robot.ListeRobot;
import modele.robot.Robot;
import vue.Observateur;

/**
 *
 * @author Arnaud
 */
public class OngletCarte extends JPanel implements Observateur {

    ArrayList<ArrayList<Position>> listePosition = new ArrayList<>(); //Liste positions par robot dans le temps

    private String description = null;

    public OngletCarte() {
        this.setPreferredSize(new Dimension(Parametre.LARGEUR, Parametre.HAUTEUR));

        description = "Suivi des déplacements des robots";

        initialisation();

        ListeRobot.ajouterObservateur(this);
    }

    /* Getter */
    public String getDescription() {
        return description;
    }

    private void initialisation() {
        JLabel titre = new JLabel("Carte");
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

        if (!ListeRobot.getListe().isEmpty()) {
            if (ListeRobot.getListe().size() > listePosition.size()) {
                for (int i = 0; i < ListeRobot.getListe().size(); i++) {
                    listePosition.add(new ArrayList<>());
                }
            }
            for (Robot robot : ListeRobot.getListe()) {
                JLabel texteRobot = new JLabel("Robot n° " + (robot.getNumero() + 1) + " : (" + robot.getPositionX() + " ; " + robot.getPositionY() + ")");
                gbc.gridy++;
                this.add(texteRobot, gbc);
                if (listePosition.get(robot.getNumero()).size() > 5) {
                    listePosition.get(robot.getNumero()).remove(0);
                }
                listePosition.get(robot.getNumero()).add(new Position((int) robot.getPositionX(), (int) robot.getPositionY()));
            }
        }
    }

    @Override
    public void miseAJour() {
        this.removeAll();
        initialisation();
        this.updateUI();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int rayon_centimetre = 250;
        int rayon_pixel = Parametre.HAUTEUR / 2 - 10;

        int square_centimetre = 25;
        int square_pixel = square_centimetre * rayon_pixel / rayon_centimetre;

        int x_cercle = Parametre.LARGEUR / 2 - rayon_pixel;
        int y_cercle = Parametre.HAUTEUR / 2 - rayon_pixel;

        g.drawOval(x_cercle, y_cercle, 2 * rayon_pixel, 2 * rayon_pixel);

        for (int x = x_cercle; x < x_cercle + 2 * rayon_pixel; x += square_pixel) {
            for (int y = y_cercle; y < y_cercle + 2 * rayon_pixel; y += square_pixel) {
                g.drawRect(x, y, square_pixel, square_pixel);
            }
        }

        if (!ListeRobot.getListe().isEmpty()) {
            for (Robot robot : ListeRobot.getListe()) {
                Position pos_precedent = null;
                for (Position position : listePosition.get(robot.getNumero())) {
                    if (pos_precedent != null) {
                        int x1 = x_cercle + ((int) pos_precedent.x * rayon_pixel / rayon_centimetre);
                        int y1 = y_cercle + ((int) pos_precedent.y * rayon_pixel / rayon_centimetre);
                        int x2 = x_cercle + ((int) position.x * rayon_pixel / rayon_centimetre);
                        int y2 = y_cercle + ((int) position.y * rayon_pixel / rayon_centimetre);

                        switch (robot.getNumero()) {
                            case 0:
                                g.setColor(Color.red);
                                break;
                            case 1:
                                g.setColor(Color.BLUE);
                                break;
                        }
                        dessiner_croix(g, x1, y1);
                        g.drawLine(x1, y1, x2, y2);
                    }
                    pos_precedent = position;
                }

                try {
                    BufferedImage img = null;

                    File file = new File("src\\img\\robot.png").getAbsoluteFile();
                    img = ImageIO.read(file);

                    int x_robot = x_cercle + ((int) robot.getPositionX() * rayon_pixel / rayon_centimetre) - img.getWidth() / 2;
                    int y_robot = y_cercle + ((int) robot.getPositionY() * rayon_pixel / rayon_centimetre) - img.getHeight() / 2;

                    switch (robot.getNumero()) {
                        case 0:
                            g.setColor(Color.red);
                            g.fillOval(x_robot + 1, y_robot, img.getWidth() - 4, img.getWidth() - 4);
                            break;
                        case 1:
                            g.setColor(Color.BLUE);
                            g.fillOval(x_robot + 1, y_robot, img.getWidth() - 4, img.getWidth() - 4);
                            break;
                    }

                    g.drawImage(img, x_robot, y_robot, this);
                } catch (Exception ex) {
                }
            }
        }
    }

    public void dessiner_croix(Graphics g, int x, int y) {
        g.drawLine(x - 5, y - 5, x + 5, y + 5);
        g.drawLine(x - 5, y + 5, x + 5, y - 5);
    }

    public class Position {

        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
