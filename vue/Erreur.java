package vue;

import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class Erreur {

    public Erreur(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

}
