package modele.robot;

import java.net.Socket;

/**
 *
 * @author Arnaud
 */
public class Robot {

    private int numero = 0;
    private double positionX = -1.0;
    private double positionY = -1.0;
    private Socket socket = null;
    private String adresseIpPublic = "";

    public Robot(int numero) {
        this.numero = numero;
    }

    /* Setter */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setAdresseIpPublic(String adresseIpPublic) {
        this.adresseIpPublic = adresseIpPublic;
    }

    /* Getter */
    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getNumero() {
        return numero;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getAdresseIpPublic() {
        return adresseIpPublic;
    }

}
