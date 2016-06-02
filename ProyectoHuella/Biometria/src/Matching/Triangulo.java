package Matching;

import Tratamiento.Minucias;

import java.io.Serializable;

/**
 * Created by JuanP on 29/05/2016.
 * <p>
 * Clase contenedora necesaria para realizar el matching de las huellas.
 *
 * @author Juan Pedro Torres Muñoz
 */
public class Triangulo implements Serializable {

    private Minucias M1;
    private Minucias M2;
    private Minucias M3;
    private int xCentro;
    private int yCentro;
    private double ang1;
    private double ang2;
    private double ang3;
    private double area;
    private Arista A1;
    /**
     * Arista entre M1 - M2
     */
    private Arista A2;
    /**
     * Arista entre M2 - M3
     */
    private Arista A3;

    /**
     * Arista entre M1 - M3
     */


    /***
     * Constructor de la clase triángulo, crea el triangulo y calcula todos los datos relativos.
     *
     * @param m1 Minucia1 para creación del triangulo.
     * @param m2 Minucia2 para creación del triangulo.
     * @param m3 Minucia3 para creación del triangulo.
     */
    public Triangulo(Minucias m1, Minucias m2, Minucias m3) {
        M1 = m1;
        M2 = m2;
        M3 = m3;
        calcularDatos();
    }

    /***
     * Método que consulta el valor de M1.
     *
     * @return devuelve la minucia M1.
     */
    public Minucias getM1() {
        return M1;
    }

    /***
     * Método que consulta el valor de M2.
     *
     * @return devuelve la minucia M2.
     */
    public Minucias getM2() {
        return M2;
    }

    /***
     * Método que consulta el valor de M3.
     *
     * @return devuelve la minucia M3.
     */
    public Minucias getM3() {
        return M3;
    }

    /***
     * Método que consulta el valor de la coordenada X del centro del triángulo.
     *
     * @return valor de la coordenada x del centro del triángulo.
     */
    public int getxCentro() {
        return xCentro;
    }

    /***
     * Método que consulta el valor de la coordenada y del centro del triángulo.
     *
     * @return valor de la coordenada y del centro del triángulo.
     */
    public int getyCentro() {
        return yCentro;
    }

    /***
     * Método que consulta el ángulo1 del triangulo.
     *
     * @return devuelve el angulo1
     */
    public double getAng1() {
        return ang1;
    }

    /***
     * Método que consulta el ángulo2 del triangulo.
     *
     * @return devuelve el angulo2
     */
    public double getAng2() {
        return ang2;
    }

    /***
     * Método que consulta el ángulo3 del triangulo.
     *
     * @return devuelve el angulo3
     */
    public double getAng3() {
        return ang3;
    }

    /***
     * Método que consulta el valor del área del triángulo.
     *
     * @return valor del area del triangulo.
     */
    public double getArea() {
        return area;
    }

    /***
     * Método que devuelve la Arista1(une M1 y M2) del triangulo.
     *
     * @return devuelve la Arista1
     */
    public Arista getA1() {
        return A1;
    }

    /***
     * Método que devuelve la Arista2(une M3 y M2) del triangulo.
     *
     * @return devuelve la Arista2
     */
    public Arista getA2() {
        return A2;
    }

    /***
     * Método que devuelve la Arista3(une M1 y M3) del triangulo.
     *
     * @return devuelve la Arista3
     */
    public Arista getA3() {
        return A3;
    }

    /***
     * Sobreescritura del metodo toString() para poder serializar el objeto.
     *
     * @return String con los valores del triángulo.
     */
    public String toString() {
        return (M1.toString() + "\t" + M2.toString() + "\t" + M3.toString() + "\t" + xCentro + "\t" + yCentro + "\t" + ang1 + "\t" + ang2 + "\t" + ang3 + "\t" + area + "\t" + A1.toString() + "\t" + A2.toString() + "\t" + A3.toString());
    }

    /***
     * Método que escribe con detalle los valores que contiene la clase Triangulo.Similar a toString()
     * @return String con los valores de la clase.
     */
    public String prettyprint(){
        return ("("+M1.getCoorX()+","+M1.getCoorY()+"),("+"("+M2.getCoorX()+","+M2.getCoorY()+"),("+"("+M3.getCoorX()+","+M3.getCoorY()+")"+" Angulos ("+ang1+","+ang2+","+ang3+") Area:"+area+"\nDistancias lados:"+A1.getLongitud()+" "+A2.getLongitud()+" "+A3.getLongitud());
    }

    private double obtenerAngulo(Arista a, Arista b, Arista c) {
        /**
         * Esto es asi por el teorema del coseno (o eso dice internet)
         */
        double dividendo = Math.pow(a.getLongitud(), 2) + Math.pow(b.getLongitud(), 2) - Math.pow(c.getLongitud(), 2);
        double divisor = 2 * a.getLongitud() * b.getLongitud();
        return Math.toDegrees(Math.acos(dividendo / divisor));
    }

    private void calcularDatos() {
        A1 = new Arista(M1, M2);
        A2 = new Arista(M2, M3);
        A3 = new Arista(M1, M3);
        calcularArea();
        xCentro = (M1.getCoorX() + M2.getCoorX() + M3.getCoorX()) / 3;
        yCentro = (M1.getCoorY() + M2.getCoorY() + M3.getCoorY()) / 3;
        calcularAngulos();
    }

    private void calcularArea() {
        /**
         * Formula sacada de internet
         */
        area = Math.abs(((M1.getCoorX() * (M2.getCoorY() - M3.getCoorY())) + (M2.getCoorX() * (M3.getCoorY() - M1.getCoorY())) + (M3.getCoorX() * (M1.getCoorY() - M2.getCoorY()))) / (2));

    }

    private void calcularAngulos() {
        ang1 = obtenerAngulo(A3, A1, A2);
        ang2 = obtenerAngulo(A1, A2, A3);
        ang3 = obtenerAngulo(A2, A3, A1);
    }

}
