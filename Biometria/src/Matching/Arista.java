package Matching;

import Tratamiento.Minucias;

/**
 * Created by JuanP on 29/05/2016.
 */
public class Arista {
    private Minucias origen;
    private Minucias destino;
    private int vectX;
    private int vectY;
    private double longitud;

    public Arista(Minucias origen, Minucias destino) {
        this.origen = origen;
        this.destino = destino;
        this.longitud = Math.sqrt(Math.pow(destino.getCoorX() - origen.getCoorX(),2) + Math.pow(destino.getCoorY() - origen.getCoorY(),2));
        vectX = destino.getCoorX() - origen.getCoorX();
        vectY = destino.getCoorY() - origen.getCoorY();
    }

    public Arista(Minucias origen, Minucias destino, double longitud) {
        this.origen = origen;
        this.destino = destino;
        this.longitud = longitud;
    }

    public Minucias getOrigen() {
        return origen;
    }

    public int getVectX() {
        return vectX;
    }

    public void setVectX(int vectX) {
        this.vectX = vectX;
    }

    public int getVectY() {
        return vectY;
    }

    public void setVectY(int vectY) {
        this.vectY = vectY;
    }

    public void setOrigen(Minucias origen) {
        this.origen = origen;
    }

    public Minucias getDestino() {
        return destino;
    }

    public void setDestino(Minucias destino) {
        this.destino = destino;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
