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

    public Socket getScoket() {
        return socket;
    }

}
