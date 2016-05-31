package Tratamiento;

import java.io.Serializable;

/**
 * Created by juanp on 5/05/16.
 * <p>
 * Clase contenedora de la información para las minucias de la huella.
 *
 * @author Juan Pedro Torres Muñoz
 */
public class Minucias implements Serializable {
    private int coorX;
    private int coorY;
    private char tipo;
    private double modulo;
    private double angulo;

    /**
     * Constructor por defecto de la clase.
     */
    public Minucias() {
        coorY = 0;
        coorX = 0;
    }

    /***
     * Constructor parametrizado que asigna las coordenadas X e Y, además del tipo.
     *
     * @param X Coordenada X de la minucia.
     * @param Y Coordenada Y de la minucia.
     * @param C Tipo de la minucia B, para bifurcacion, F para final.
     */
    public Minucias(int X, int Y, char C) {
        coorX = X;
        coorY = Y;
        tipo = C;
        modulo = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));

    }

    /***
     * Constructor parametrizado que asigna las coordenadas X e Y, el tipo y el ángulo.
     *
     * @param X     Coordenada X de la minucia.
     * @param Y     Coordenada Y de la minucia.
     * @param C     Tipo de la minucia (B o F).
     * @param angle Ángulo de la minucia.
     */
    public Minucias(int X, int Y, char C, double angle) {
        coorX = X;
        coorY = Y;
        tipo = C;
        angulo = angle;
        modulo = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));

    }

    /***
     * Método que consulta el valor de la coordenada X.
     *
     * @return Coordenada X.
     */
    public int getCoorX() {
        return coorX;
    }

    /***
     * Método que cambia el valor de la coordenada X.
     *
     * @param coorX nuevo valor de la coordenada X.
     */
    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    /***
     * Método que consulta el valor de la coordenada Y.
     *
     * @return Coordenada Y.
     */
    public int getCoorY() {
        return coorY;
    }

    /**
     * Método que asigna un nuevo valor a la coordenada Y.
     *
     * @param coorY nuevo valor de la coordenada Y.
     */
    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    /***
     * Método que consulta el valor del tipo de minucia.
     *
     * @return B si la minucia es de bifurcación, F si es final.
     */
    public char getTipo() {
        return tipo;
    }

    /**
     * Método que asigna un nuevo valor al tipo.
     *
     * @param tipo nuevo valor para el tipo de minucia.
     */
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    /***
     * Método que consulta el valor del módulo de la minucia.
     *
     * @return valor del módulo.
     */
    public double getModulo() {
        return modulo;
    }

    /***
     * Método que asigna un nuevo módulo a la minucia.
     *
     * @param modulo nuevo valor del módulo.
     */
    public void setModulo(double modulo) {
        this.modulo = modulo;
    }

    /***
     * Método que consulta el valor del ángulo de la minucia.
     *
     * @return Valor del ángulo.
     */
    public double getAngulo() {
        return angulo;
    }

    /***
     * Método que asigna un nuevo valor al ángulo de la minucia.
     *
     * @param angulo nuevo valor del ángulo.
     */
    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    /***
     * Sobreescritura del método toString() para que se pueda serializar los objetos a un fichero.
     *
     * @return Valores del objeto.
     */
    public String toString() {
        //return (" Minucia de tipo "+tipo+" en las coordenadas ("+coorX+","+coorY+") con módulo "+ modulo +" y con un ángulo de "+angulo);
        return (coorX + "\t" + coorY + "\t" + tipo + "\t" + modulo + "\t" + angulo);
    }
}
