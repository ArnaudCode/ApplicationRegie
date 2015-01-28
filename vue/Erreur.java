package vue;

import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class Erreur {

    public Erreur(String message) {
        System.out.println("\u001B[31mErreur : " + message + "\u001B[0m");
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

}
