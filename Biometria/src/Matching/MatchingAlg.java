package Matching;

import Tratamiento.Minucias;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by JuanP on 30/05/2016.
 */
public class MatchingAlg {
    private static MatchingAlg ourInstance;
    LinkedList<Minucias> minucias;
    private LinkedList<Triangulo> triangles;
    private byte[][] imageMatriz;

    private MatchingAlg() {
        triangles = new LinkedList<Triangulo>();
    }

    public static MatchingAlg getInstance() {
        if (ourInstance == null)
            ourInstance = new MatchingAlg();
        return ourInstance;
    }

    public void setMatrix(byte[][] imageMatriz) {
        this.imageMatriz = imageMatriz;
    }

    public LinkedList<Minucias> getMinucias() {
        return minucias;
    }

    public void setMinucias(LinkedList<Minucias> minucias) {
        this.minucias = minucias;
    }

    private void saveTriangle() {

        try {
            FileOutputStream fout = new FileOutputStream("triangulos.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(triangles);
            oos.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LinkedList<Triangulo> loadTriangle() {
        LinkedList<Triangulo> l;
        try {
            FileInputStream fin = new FileInputStream("triangulos.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            l = (LinkedList<Triangulo>) ois.readObject();
            ois.close();
            return l;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void compute() {
        generateTriangles();
    }

    private void generateTriangles() {
        boolean invalid2 = false;
        boolean invalid3 = false;

        Minucias M1,M2,M3;

        for (int i = 0; i < minucias.size() - 2; i++) {
            M1 = minucias.get(i);
            for (int j = i + 1; j < minucias.size() - 1 && !invalid2; j++) {
                M2 = minucias.get(j);
                invalid3 = isInvalid(new Arista(M1,M2).getLongitud());
                for (int k = j + 1; k < minucias.size() && !invalid3; k++) {
                    M3 = minucias.get(k);
                    invalid3 = isInvalid(new Arista(M2,M3).getLongitud()) || isInvalid(new Arista(M3,M1).getLongitud());

                    triangles.add(new Triangulo(M1,M2,M3));

                }
                if (invalid3)
                    invalid2 =true;

            }
            if (invalid2){
                invalid2 = false;
            }
            if (invalid3){
                invalid3=false;
            }
        }
    }

    private boolean isInvalid(double d){
        return d>=15;
    }

    public boolean compararHuellas(){
        LinkedList<Triangulo> trianGuard = loadTriangle();
        int triangulosPos =0;
        int triangulosTotal = 0;


        for (Triangulo triangle :
                trianGuard) {
            if ( isEqual(triangle)){
                triangulosPos++;
            }
            triangulosTotal++;
        }

        if (triangulosPos >= (triangulosTotal/3)){
            return true;
        }else
            return false;
    }

    private boolean isEqual(Triangulo t){


        for (Triangulo trian :
               triangles ) {
            if (compareAngulos(t, trian) && compareArea(t,trian)){
                return true;
            }
        }
        return false;
    }

    private boolean compareAngulos(Triangulo t,Triangulo trian){
        if (t.getAng1() == trian.getAng1()){
            if (t.getAng2()==t.getAng2()){
                return true;
            }else if(t.getAng2() == trian.getAng3()){
                return true;
            }else
                return false;
        }else if(t.getAng1()== trian.getAng2()){
            if (t.getAng2()==t.getAng1()){
                return true;
            }else if(t.getAng2() == trian.getAng3()){
                return true;
            }else
                return false;
        }else if(t.getAng1() == trian.getAng3()){
            if (t.getAng2()==t.getAng2()){
                return true;
            }else if(t.getAng2() == trian.getAng1()){
                return true;
            }else
                return false;
        }else
            return false;
    }

    private boolean compareArea(Triangulo t,Triangulo trian){
        double areaT = t.getArea();
        double areaTrian = trian.getArea();
        if (areaT == areaTrian){
            return true;
        }else if(Math.abs(areaT-areaTrian) <= (areaTrian*0.05)){ //caso deformación
            return true;
        }else
            return false;
    }

    public void test(){
        LinkedList<Triangulo> prob = triangles;
        loadTriangle();

        for (int i = 0; i < triangles.size() && (triangles.size() == prob.size()); i++) {
            System.out.println(triangles.get(i));
            System.out.println(prob.get(i));
        }
    }
}
