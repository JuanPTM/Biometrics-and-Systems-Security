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
    private Triangulo bestTriangle;
    private byte[][] imageMatriz;

    private MatchingAlg() {
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

    private void saveTriangle(){

        try {
            FileOutputStream fout = new FileOutputStream("triangulo.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(bestTriangle);
            oos.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTriangle(){
        try {
            FileInputStream fin = new FileInputStream("triangulo.txt");
            ObjectInputStream ois = new ObjectInputStream(fin);
            bestTriangle = (Triangulo) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void compute(){
        generateTriangles();
        saveTriangle();
    }

    private void generateTriangles(){


        for (int i = 0; i < minucias.size()-2; i++) {
            for (int j = i+1; j < minucias.size()-1; j++) {
                for (int k = j+1; k < minucias.size(); k++) {

                }
            }
        }
    }
}
