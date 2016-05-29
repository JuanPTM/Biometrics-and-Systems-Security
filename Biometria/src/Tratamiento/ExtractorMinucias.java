package Tratamiento;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by juanp on 5/05/16.
 */

public class ExtractorMinucias {
    private static ExtractorMinucias extmin = null;
    private BufferedImage mypicture; /** Imagen inicial */
    private BufferedImage pictureFinal; /** Almacena la imagen con colores de las minucias*/
    private byte[][] imageMatriz;  /** Matriz que guarda la imagen en blanco y negro */
    private byte[][] matrizFinal; /** Matriz que guarda los puntos coloreados de las minucias */
    private LinkedList<Minucias> ListaMinF = new LinkedList<>();
    private LinkedList<Minucias> ListaMinB = new LinkedList<>();

    private ExtractorMinucias(BufferedImage picture) {
        mypicture = picture;
        imageMatriz = new byte[picture.getHeight()][picture.getWidth()];
        int value;
        for (int i = 0; i < picture.getHeight(); i++) {
            for (int j = 0; j < picture.getWidth(); j++) {
                value = picture.getRGB(j, i) & 0xFF;
                imageMatriz[i][j] = (byte) value;
            }
        }
    }

    public static ExtractorMinucias getInstance(BufferedImage b) {
        if (extmin == null) {
            return new ExtractorMinucias(b);
        } else {
            return extmin;
        }
    }

    public BufferedImage getMinucias(int umbral) {
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }
        ListaMinB.clear();
        ListaMinF.clear();

        for (int i = umbral; i < imageMatriz.length - 1 - umbral; i++) {
            for (int j = umbral; j < imageMatriz[i].length - 1 - umbral; j++) {
                int p4 = (int) imageMatriz[i - 1][j - 1] + 1;
                int p3 = (int) imageMatriz[i - 1][j] + 1;
                int p2 = (int) imageMatriz[i - 1][j + 1] + 1;
                int p5 = (int) imageMatriz[i][j - 1] + 1;
                int p = (int) imageMatriz[i][j] + 1;
                int p1 = (int) imageMatriz[i][j + 1] + 1;
                int p6 = (int) imageMatriz[i + 1][j - 1] + 1;
                int p7 = (int) imageMatriz[i + 1][j] + 1;
                int p8 = (int) imageMatriz[i + 1][j + 1] + 1;

                int valor = Math.abs(p1 - p2) + Math.abs(p2 - p3) + Math.abs(p3 - p4) + Math.abs(p4 - p5) + Math.abs(p5 - p6) + Math.abs(p6 - p7) + Math.abs(p7 - p8) + Math.abs(p8 - p1);
                valor /= 2;

               // if (j >= (imageMatriz[i].length) && j <= ((imageMatriz[i].length))) {
                    if (valor == 1 && p == 1) { //minucia final

                        newMatrix[i - 1][j - 1] = (byte) 2;
                        newMatrix[i - 1][j] = (byte) 2;
                        newMatrix[i - 1][j + 1] = (byte) 2;
                        newMatrix[i][j - 1] = (byte) 2;
                        newMatrix[i][j + 1] = (byte) 2;
                        newMatrix[i + 1][j - 1] = (byte) 2;
                        newMatrix[i + 1][j] = (byte) 2;
                        newMatrix[i + 1][j + 1] = (byte) 2;
                        newMatrix[i][j] = (byte) 2;
                        ListaMinF.add(new Minucias(i, j, 'F',calculoAngulo(i,j)));

                    } else if (valor == 3 && p == 1) { // minucia bifurcacion

                        newMatrix[i][j] = (byte) 3;
                        newMatrix[i - 1][j - 1] = (byte) 3;
                        newMatrix[i - 1][j] = (byte) 3;
                        newMatrix[i - 1][j + 1] = (byte) 3;
                        newMatrix[i][j - 1] = (byte) 3;
                        newMatrix[i][j + 1] = (byte) 3;
                        newMatrix[i + 1][j - 1] = (byte) 3;
                        newMatrix[i + 1][j] = (byte) 3;
                        newMatrix[i + 1][j + 1] = (byte) 3;
                        ListaMinB.add(new Minucias(i, j, 'B',calculoAngulo(i,j)));

                    }
                }
            }
   //     }
        matrizFinal = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;

    }

    private BufferedImage toBufferedImage() {
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), BufferedImage.TYPE_INT_RGB);

        int valor;
        int rgbfinal;
        int azul = 0xff0000ff;
        int red = 0xffff0000;

        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                valor = (matrizFinal[i][j] & 0xFF);
                switch (valor) {
                    case 2:
                        newImage.setRGB(j, i, azul);
                        break;
                    case 3:
                        newImage.setRGB(j, i, red);
                        break;
                    default:
                        rgbfinal = (valor << 8) | valor;
                        rgbfinal = (rgbfinal << 8) | valor;
                        newImage.setRGB(j, i, rgbfinal);
                        break;
                }


            }
        }
        return newImage;
    }

    private double calculoAngulo(int x, int y){
        Gradiente.setImage(imageMatriz);
        Gradiente.setVentana(3);
        double divid = Gradiente.getDivi(x,y);
        double divisor = Gradiente.getdivisor(x,y);

        return Math.toDegrees(Math.atan(divid/divisor)/2);
    }
}
