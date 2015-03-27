package modele.module;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import modele.applicationpublic.ListePublic;
import modele.applicationpublic.Public;
import modele.robot.ListeRobot;
import modele.serveur.Emission;
import org.json.JSONObject;
import vue.Erreur;
import vue.onglet.OngletEcran;

/**
 *
 * @author Arnaud
 */
public class ModulePublic extends Module {

    private JSONObject json = null;
    private Socket socket = null;
    private Public applicationpublic = null;

    public ModulePublic(JSONObject json, Socket socket) {
        this.json = json;
        this.socket = socket;

        if (json.get("action").equals("init")) {
            /* Réception */
            applicationpublic = new Public(socket);
            ListePublic.getListe().add(applicationpublic);

            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setAttente(true);
            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setControle(false);

            /* Emision */
            ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).envoieSecondes();

            ListePublic.notification();
        } else {
            new Erreur("Première connexion inccorecte :\nPas de action: init");
        }
    }

    @Override
    public void traitement(String ligne) {
        try {
            JSONObject json = new JSONObject(ligne);

            JSONObject detail = json.getJSONObject("detail");

            String action = detail.getString("action");
            switch(action)
            {
                case "fin":
                case "refuser":
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setAttente(true);
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).setControle(false);

                    libererRobot();

                    /* Emision */
                    ListePublic.getListe().get(ListePublic.getListe().indexOf(applicationpublic)).envoieSecondes();
                    break;
                case "photo":
                    BufferedImage img = OngletEcran.screenshot();
                    if(img == null)
                        break;
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
                    String path = "captures/"+format.format(new Date())+"/";
                    File folder = new File(path);
                    if(!folder.exists())
                        folder.mkdirs();
                    String[] files = folder.list();
                    try
                    {
                        File file = new File(path+files.length+".jpg");
                        ImageIO.write(img, "jpg", file);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    String vitesse = String.valueOf(detail.optInt("vitesse"));

                    JSONObject emission = new JSONObject();
                    emission.put("action", action);
                    emission.put("vitesse", vitesse);

                    new Emission(ListeRobot.getListe().get(0).getSocket(), emission.toString()); //Robot ecrit en dur !
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        ListePublic.getListe().remove(applicationpublic);
        libererRobot();
        ListePublic.notification();
    }

    private void libererRobot() {
        //Recherche du robot qui etait controlle
        for (int i = 0; i < ListeRobot.getListe().size(); i++) {
            if (ListeRobot.getListe().get(i).getAdresseIpPublic().equals(applicationpublic.getAdresseIP())) {

                JSONObject emission = new JSONObject();
                emission.put("action", "stop");
                emission.put("vitesse", "0");

                new Emission(ListeRobot.getListe().get(i).getSocket(), emission.toString());

                ListeRobot.getListe().get(i).setAdresseIpPublic("");
            }
        }
    }

}
