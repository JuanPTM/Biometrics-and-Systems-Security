package Tratamiento;

/**
 * Created by juanp on 5/05/16.
 */
public class Minucias {
    private int coorX;
    private int coorY;
    private char tipo;
    private int modulo;
    private double angulo;

    // TODO: 9/05/16 Calculo angulos y módulo.

    public Minucias(){
        coorY = 0;
        coorX = 0;
    }

    public Minucias(int X, int Y, char C){
        coorX = X;
        coorY = Y;
        tipo = C;
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

    public int getModulo() {
        return modulo;
    }

    public void setModulo(int modulo) {
        this.modulo = modulo;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public String toString (){
        return (" Minucia de tipo "+tipo+" en las coordenadas ("+coorX+","+coorY+") con módulo "+ modulo +" y con un ángulo de "+angulo);
    }
}
