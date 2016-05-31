package Tratamiento;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by juanp on 5/05/16.
 * <p>
 * Clase encargada de extraer las minucias, almacenarlas y mostrarlas.
 *
 * @author Juan Pedro Torres Muñoz
 */

public class ExtractorMinucias {
    private static ExtractorMinucias extmin = null;
    /**
     * Imagen inicial
     */
    private BufferedImage mypicture;
    /**
     * Almacena la imagen con colores de las minucias
     */
    private BufferedImage pictureFinal;
    /**
     * Matriz que guarda la imagen en blanco y negro
     */
    private byte[][] imageMatriz;
    /**
     * Matriz que guarda los puntos coloreados de las minucias
     */
    private byte[][] matrizFinal;

    private LinkedList<Minucias> ListaMinF = new LinkedList<>();
    private LinkedList<Minucias> ListaMinB = new LinkedList<>();

    /**
     * Método que obtiene la matriz con la que hemos trabajado.
     *
     * @return
     */
    public byte[][] getImageMatriz() {
        return imageMatriz;
    }

    /***
     * Metodo que permite la implementación del singleton.
     *
     * @return Instancia de la clase ExtractorMinucias.
     */
    public static ExtractorMinucias getInstance() {
        return extmin;
    }

    /***
     * Método que obtiene la instancia de la clase y en caso de que no este creada la crea.
     *
     * @param b BufferedImage con la que se va a inicializar la clase.
     * @return Instancia inicializada de la clase.
     */
    public static ExtractorMinucias getInstance(BufferedImage b) {
        extmin = new ExtractorMinucias(b);
        return extmin;
    }

    /***
     * Método que obtiene las minucias de la imagen, respetando un margen con los bordes.
     *
     * @param umbral ventana que respetará respecto a los bordes, en pixels.
     * @return Imagen con las minucias resaltadas, rojo bifurcacion, azul finales.
     */
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
                    ListaMinF.add(new Minucias(i, j, 'F', calculoAngulo(i, j)));

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
                    ListaMinB.add(new Minucias(i, j, 'B', calculoAngulo(i, j)));

                }
            }
        }
        //     }
        matrizFinal = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;

    }

    /***
     * Método que obtiene las minucias de la imagen, respetando un margen con los bordes.
     *
     * @param umbralLateral  ventana que respetará respecto a los bordes laterales, en pixels.
     * @param umbralInferior ventana que respetará respecto al borde inferior, en pixels.
     * @param umbralSuperior ventana que respetará respecto al borde superior, en pixels.
     * @return Imagen con las minucias resaltadas, rojo bifurcacion, azul finales.
     */
    public BufferedImage getMinucias(int umbralLateral, int umbralSuperior, int umbralInferior) {
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }
        ListaMinB.clear();
        ListaMinF.clear();

        for (int i = umbralSuperior; i < imageMatriz.length - 1 - umbralInferior; i++) {
            for (int j = umbralLateral; j < imageMatriz[i].length - 1 - umbralLateral; j++) {
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
                    ListaMinF.add(new Minucias(i, j, 'F', calculoAngulo(i, j)));

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
                    ListaMinB.add(new Minucias(i, j, 'B', calculoAngulo(i, j)));

                }
            }
        }
        matrizFinal = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;

    }

    /***
     * Método que obtiene la lista de minucias finales.
     *
     * @return LinkedList con las minucias Finales.
     */
    public LinkedList<Minucias> getListaMinF() {
        return ListaMinF;
    }

    /***
     * Método que obtiene la lista de minucias de bifurcación.
     *
     * @return LinkedList con las minucias de bifurcación.
     */
    public LinkedList<Minucias> getListaMinB() {
        return ListaMinB;
    }

    private double calculoAngulo(int x, int y) {
        Gradiente.setImage(imageMatriz);
        Gradiente.setVentana(3);
        double divid = Gradiente.getDivi(x, y);
        double divisor = Gradiente.getdivisor(x, y);

        return Math.toDegrees(Math.atan(divid / divisor) / 2);
    }

    private BufferedImage toBufferedImage() {
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), BufferedImage.TYPE_INT_RGB);

        int valor;
        int rgbfinal;
        int azul = 0xff0000ff;
        int red = 0xffff0000;
        int green = 0xff00ff00;

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
                    case 4:
                        newImage.setRGB(j, i, green);
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
}
