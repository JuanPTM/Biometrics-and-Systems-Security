package Matching;

import Tratamiento.Minucias;

import java.io.Serializable;

/**
 * Created by JuanP on 29/05/2016.
 * <p>
 * Clase contenedora que representa una arista.
 *
 * @author Juan Pedro Torres Muñoz.
 */
public class Arista implements Serializable {
    private Minucias origen;
    private Minucias destino;
    private int vectX;
    private int vectY;
    private double longitud;

    /**
     * Constructor parametrizado que inicializa la estructura Arista.
     *
     * @param origen  minucia de origen de la arista.
     * @param destino minucia de destino de la arista.
     */
    public Arista(Minucias origen, Minucias destino) {
        this.origen = origen;
        this.destino = destino;
        this.longitud = Math.sqrt(Math.pow(destino.getCoorX() - origen.getCoorX(), 2) + Math.pow(destino.getCoorY() - origen.getCoorY(), 2));
        vectX = destino.getCoorX() - origen.getCoorX();
        vectY = destino.getCoorY() - origen.getCoorY();
    }

    /**
     * Constructor parametrizado que inicializa la estructura Arista.
     *
     * @param origen   minucia de origen de la arista.
     * @param destino  minucia de destino de la arista.
     * @param longitud longitud de la arista.
     */
    public Arista(Minucias origen, Minucias destino, double longitud) {
        this.origen = origen;
        this.destino = destino;
        this.longitud = longitud;
    }

    /**
     * Método que consulta la minucia de origen.
     *
     * @return minucia de origen.
     */
    public Minucias getOrigen() {
        return origen;
    }

    /**
     * Método que consulta el vector de desplazamiento en X (coordenada X final - coordenada X inicial).
     *
     * @return valor de la componente x del desplazamiento.
     */
    public int getVectX() {
        return vectX;
    }

    /**
     * Método que consulta el vector de desplazamiento en Y (coordenada Y final - coordenada Y inicial).
     *
     * @return valor de la componente y del desplazamiento.
     */
    public int getVectY() {
        return vectY;
    }

    /**
     * Método que consulta destino de la arista.
     *
     * @return minucia destino de la arista.
     */
    public Minucias getDestino() {
        return destino;
    }

    /**
     * Método que consulta el valor de la longitud de la arista.
     *
     * @return valor de la longitud de la arista.
     */
    public double getLongitud() {
        return longitud;
    }

    /***
     * Sobreescritura del método toString() para poder serializar la clase.
     *
     * @return valores de la instancia.
     */
    public String toString() {
        return (origen.toString() + "\t" + destino.toString() + "\t" + vectX + "\t" + vectY + "\t" + longitud);
    }
}
