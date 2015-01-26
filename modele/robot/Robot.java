package modele.robot;

/**
 *
 * @author Arnaud
 */
public class Robot {

    private int numero = 0;
    private double positionX = -1.0;
    private double positionY = -1.0;

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

}
