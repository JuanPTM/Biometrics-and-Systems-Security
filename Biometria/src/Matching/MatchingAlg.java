package Matching;

import Tratamiento.Minucias;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by JuanP on 30/05/2016.
 * <p>
 * Clase encargada de realizar el matching, guardar y cargar los datos.
 *
 * @author Juan Pedro Torres Muñoz.
 */
public class MatchingAlg {
    private static MatchingAlg ourInstance;
    LinkedList<Minucias> minucias;
    private LinkedList<Triangulo> triangles;
    private float prob = 0;


    /***
     * Método que permite utilizar el patron singleton.
     *
     * @return Instancia de la clase MatchingAlg.
     */
    public static MatchingAlg getInstance() {
        if (ourInstance == null)
            ourInstance = new MatchingAlg();
        return ourInstance;
    }

    /***
     * Método para consultar la lista de minucias sobre la que se esta trabajando.
     *
     * @return lista de minucias con las que se esta trabajando.
     */
    public LinkedList<Minucias> getMinucias() {
        return minucias;
    }

    /***
     * Método que asigna una lista de minucias a la clase.
     *
     * @param minucias Lista de minucias sobre la que se va a trabajar.
     */
    public void setMinucias(LinkedList<Minucias> minucias) {
        this.minucias = minucias;
    }

    /***
     * Método que genera todos los triangulos posibles y validos de la lista de minucias.
     */
    public void compute() {
        generateTriangles();
    }

    /***
     * Método que guarda en un fichero los triángulos contenidos en triangles.
     */
    public void guardarTriangulos() {
        saveTriangle();
    }

    /***
     * Método que devuelve la cantidad de triangulos guardados divididos de los que ha encontrado correctos.
     *
     * @return valor entre 0 y 1.
     */
    public float probAcierto() {
        return prob;
    }

    private void saveTriangle() {

        try {
            FileOutputStream fout = new FileOutputStream("Huella.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(triangles);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LinkedList<Triangulo> loadTriangle() throws IOException {
        LinkedList<Triangulo> l;
        try {
            FileInputStream fin = new FileInputStream("Huella.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            l = (LinkedList<Triangulo>) ois.readObject();
            ois.close();
            return l;
        } catch (IOException e) {
            throw new IOException("No hay huella en el sistema con la que comparar.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void generateTriangles() {
        boolean invalido = false;

        Minucias M1, M2, M3;
        triangles.clear();

        for (int i = 0; i < minucias.size() - 2; i++) {
            M1 = minucias.get(i);
            for (int j = i + 1; j < minucias.size() - 1; j++) {
                M2 = minucias.get(j);
                for (int k = j + 1; k < minucias.size(); k++) {
                    M3 = minucias.get(k);
                    invalido = isInvalid(new Arista(M2, M3).getLongitud()) || isInvalid(new Arista(M3, M1).getLongitud()) || isInvalid(new Arista(M1, M2).getLongitud());

                    if (!invalido)
                        triangles.add(new Triangulo(M1, M2, M3));
                    else
                        invalido = false;
                }
            }
        }
    }

    private boolean isInvalid(double d) {
        return d >= 40;
    }

    public boolean compararHuellas() {
        LinkedList<Triangulo> trianGuard = null;
        try {
            trianGuard = loadTriangle();
        } catch (IOException e) {
            prob = -1;
            return true;
        }
        int triangulosPos = 0;

        for (Triangulo triangle :
                trianGuard) {
            if (isEqual(triangle)) {
                triangulosPos++;
            }
        }


        prob = (float) triangulosPos / trianGuard.size();
        System.out.println(triangles.size() + " "+trianGuard.size());
        if (trianGuard.size() == 0 || triangles.size() > trianGuard.size()*1.5) //Evitar que de positivo por tener muchos triangulos y cuele alguno.
            prob = 0;
        if (prob >= 0.75) {
            return true;
        } else
            return false;
    }

    private boolean isEqual(Triangulo t) {

        if (!triangles.isEmpty())
            for (Triangulo trian :
                    triangles) {
                if (compareAngulos(t, trian) || compareArea(t, trian)) {
                    return true;
                }
            }
        return false;
    }

    private boolean compareAngulos(Triangulo t, Triangulo trian) {
        if (t.getAng1() == trian.getAng1()) {
            if (t.getAng2() == t.getAng2()) {
                return true;
            } else if (t.getAng2() == trian.getAng3()) {
                return true;
            } else
                return false;
        } else if (t.getAng1() == trian.getAng2()) {
            if (t.getAng2() == t.getAng1()) {
                return true;
            } else if (t.getAng2() == trian.getAng3()) {
                return true;
            } else
                return false;
        } else if (t.getAng1() == trian.getAng3()) {
            if (t.getAng2() == t.getAng2()) {
                return true;
            } else if (t.getAng2() == trian.getAng1()) {
                return true;
            } else
                return false;
        } else
            return false;
    }

    private boolean compareArea(Triangulo t, Triangulo trian) {
        double areaT = t.getArea();
        double areaTrian = trian.getArea();
        if (areaT == areaTrian) {
            return true;
        } else if (Math.abs(areaT - areaTrian) <= (areaTrian * 0.1)) { //caso deformación
            return true;
        } else
            return false;
    }

    private void pintar(LinkedList<Triangulo> l) {
        System.out.println(l.size());
        for (Triangulo trian :
                l) {
            System.out.println(trian.prettyprint());
        }
        System.out.println(triangles.size());
        for (Triangulo trian :
                triangles) {
            System.out.println(trian.prettyprint());
        }
    }

    private MatchingAlg() {
        triangles = new LinkedList<Triangulo>();
    }

    private void test() {
        LinkedList<Triangulo> prob = triangles;
        try {
            loadTriangle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < triangles.size() && (triangles.size() == prob.size()); i++) {
            System.out.println(triangles.get(i));
            System.out.println(prob.get(i));
        }
    }
}
