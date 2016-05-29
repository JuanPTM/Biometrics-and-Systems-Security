package Matching;

import Tratamiento.Minucias;

/**
 * Created by JuanP on 29/05/2016.
 */
public class Triangulo {

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

    public Triangulo(Minucias m1, Minucias m2, Minucias m3) {
        M1 = m1;
        M2 = m2;
        M3 = m3;
        calcularDatos();
    }

    public Minucias getM1() {
        return M1;
    }

    public Minucias getM2() {
        return M2;
    }

    public Minucias getM3() {
        return M3;
    }

    public int getxCentro() {
        return xCentro;
    }

    public int getyCentro() {
        return yCentro;
    }

    public double getAng1() {
        return ang1;
    }

    public double getAng2() {
        return ang2;
    }

    public double getAng3() {
        return ang3;
    }

    public double getArea() {
        return area;
    }

    public Arista getA1() {
        return A1;
    }

    public Arista getA2() {
        return A2;
    }

    public Arista getA3() {
        return A3;
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

    private double obtenerAngulo(Arista a, Arista b, Arista c) {
        /**
         * Esto es asi por el teorema del coseno (o eso dice internet)
         */
        double dividendo = Math.pow(a.getLongitud(), 2) + Math.pow(b.getLongitud(), 2) - Math.pow(c.getLongitud(), 2);
        double divisor = 2 * a.getLongitud() * b.getLongitud();
        return Math.toDegrees(Math.acos(dividendo / divisor));
    }
}
