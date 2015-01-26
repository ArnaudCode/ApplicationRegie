package applicationregie;

import javax.swing.UIManager;
import vue.Fenetre;

/**
 *
 * @author Arnaud
 */
public class ApplicationRegie {

    public static void main(String[] args) {

        /* Look and Feel */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Fenetre fenetre = new Fenetre();
        fenetre.show();
    }

}
