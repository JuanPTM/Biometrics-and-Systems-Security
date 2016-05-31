package Tratamiento;

import java.io.Serializable;

/**
 * Created by juanp on 5/05/16.
 */
public class Minucias implements Serializable {
    private int coorX;
    private int coorY;
    private char tipo;
    private double modulo;
    private double angulo;

    // TODO: 9/05/16 Calculo angulos.

    public Minucias(){
        coorY = 0;
        coorX = 0;
    }

    public Minucias(int X, int Y, char C){
        coorX = X;
        coorY = Y;
        tipo = C;
        modulo = Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));


    }

    public Minucias(int X, int Y, char C,double angle){
        coorX = X;
        coorY = Y;
        tipo = C;
        angulo = angle;
        modulo = Math.sqrt(Math.pow(X,2)+Math.pow(Y,2));

        System.out.println("El angulo es: "+angle);

    }

    public int getCoorX() {
        return coorX;
    }

    public void setCoorX(int coorX) {
        this.coorX = coorX;
    }

    public int getCoorY() {
        return coorY;
    }

    public void setCoorY(int coorY) {
        this.coorY = coorY;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public double getModulo() {
        return modulo;
    }

    public void setModulo(double modulo) {
        this.modulo = modulo;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public String toString (){
        //return (" Minucia de tipo "+tipo+" en las coordenadas ("+coorX+","+coorY+") con módulo "+ modulo +" y con un ángulo de "+angulo);
        return (coorX+"\t"+coorY+"\t"+tipo+"\t"+modulo+"\t"+angulo);
    }
}
