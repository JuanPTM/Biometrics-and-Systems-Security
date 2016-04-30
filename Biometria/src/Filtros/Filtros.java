package Filtros;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by juanp on 20/04/16.
 */

public class Filtros {
    private static Filtros ourInstance = new Filtros();
    private BufferedImage mypicture;
    private BufferedImage pictureFinal;
    private byte[][] imageMatriz;
    private boolean isGrey = false;

    public BufferedImage getMypicture() {
        return mypicture;
    }

    public void setMypicture(BufferedImage mypicture) {
        this.mypicture = mypicture;
    }

    public BufferedImage getPictureFinal() {
        return pictureFinal;
    }

    public void setPictureFinal(BufferedImage pictureFinal) {
        this.pictureFinal = pictureFinal;
    }

    public static Filtros getInstance() {
        if (ourInstance == null)
            ourInstance = new Filtros();
        return ourInstance;
    }

    private Filtros() {
    }

    public BufferedImage getGrisImg() {
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), mypicture.getType());
        int rgb;
        int red;
        int green;
        int blue;
        int media;
        int rgbfinal;

        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                rgb = mypicture.getRGB(j, i);
                red = (rgb >> 16) & 0x000000FF;
                green = (rgb >> 8) & 0x000000FF;
                blue = (rgb) & 0x000000FF;

                media = (red + green + blue) / 3;
                rgbfinal = (media << 8) | media;
                rgbfinal = (rgbfinal << 8) | media;
                newImage.setRGB(j, i, rgbfinal);

                imageMatriz[i][j] = (byte) media;
            }
        }
        pictureFinal = newImage;
        isGrey = true;
        return newImage;

    }

    public boolean isGrey() {
        return isGrey;
    }

    public void loadImage(String path) {

        try {
            mypicture = ImageIO.read(new File(path));
            imageMatriz = new byte[mypicture.getHeight()][mypicture.getWidth()];
            isGrey = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoad() {
        if (mypicture != null)
            return true;
        else
            return false;
    }

    private int[] getHistogram() {
        int[] histogram = new int[256];
        for (int i : histogram
                ) {
            histogram[i] = 0;
        }
        int valueGrey;
        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                valueGrey = mypicture.getRGB(j, i) & 0xFF;
                histogram[valueGrey]++;
            }
        }

        return histogram;
    }

    public BufferedImage thresholdFilter(int umbral) {
        int valueGrey;
        int blanco = (255 << 8) | 255;
        blanco = (blanco << 8) | 255;
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), mypicture.getType());

        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                valueGrey = mypicture.getRGB(j, i) & 0xFF;
                if (valueGrey < umbral) {
                    newImage.setRGB(j, i, 0);
                    imageMatriz[i][j] = (byte) 0;
                } else {
                    newImage.setRGB(j, i, blanco);
                    imageMatriz[i][j] = (byte) 255;
                }
            }
        }
        pictureFinal = newImage;
        return newImage;
    }

    private float[] getLUT(int[] histogram) {
        float[] lut = new float[256];
        int sum = 0;
        int valor;
        float fscala = (float) (255.0 / (mypicture.getHeight() * mypicture.getWidth()));
        for (int i = 0; i < histogram.length; ++i) {
            sum += histogram[i];
            valor = (int) (sum * fscala);
            if (valor > 255) {
                lut[i] = 255;
            } else {
                lut[i] = valor;
            }
        }
        return lut;
    }

    public BufferedImage getEcualizar() {
        int[] histograma = getHistogram();
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }

        float[] LUT = getLUT(histograma);
        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                int valor = newMatrix[i][j] & 0xFF;

                int valorNuevo = (int) LUT[valor];
                newMatrix[i][j] = (byte) valorNuevo;
            }

        }
        imageMatriz = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;

    }

    private BufferedImage toBufferedImage() {
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), mypicture.getType());

        int media;
        int rgbfinal;
        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                media = (imageMatriz[i][j] & 0xFF);
                rgbfinal = (media << 8) | media;
                rgbfinal = (rgbfinal << 8) | media;
                newImage.setRGB(j, i, rgbfinal);

            }
        }
        return newImage;
    }

    public void dstToSrc() {
        mypicture = toBufferedImage();
    }

    public BufferedImage smoothing() {

        double[][] kernel = {{0.125, 0.125, 0.125}, {0.125, 0, 0.125}, {0.125, 0.125, 0.125}};

        for (int i = 1; i < imageMatriz.length - 1; i++) {
            for (int j = 1; j < imageMatriz[i].length - 1; j++) {
                float suma = 0;

                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        int valor = imageMatriz[i + k][j + l] & 0xFF;
                        suma = (float) (suma + (kernel[1 + k][1 + l] * valor));
                    }
                }
                imageMatriz[i][j] = (byte) suma;
            }
        }
        mypicture = toBufferedImage();
        return mypicture;
    }

    public BufferedImage binaryFilter() {
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }

        for (int i = 1; i < imageMatriz.length - 1; i++) {
            for (int j = 1; j < imageMatriz[i].length - 1; j++) {
                int b = (int) imageMatriz[i - 1][j] + 1;
                int d = (int) imageMatriz[i][j - 1] + 1;
                int p = (int) imageMatriz[i][j] + 1;
                int e = (int) imageMatriz[i][j + 1] + 1;
                int g = (int) (imageMatriz[i + 1][j]) + 1;

                int valor = p + (b * g * (d + e)) + (d * e * (b + g));
                if (valor != 0) {
                    newMatrix[i][j] = (byte) 0;
                } else
                    newMatrix[i][j] = (byte) 255;
            }
        }
        imageMatriz = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;
    }

    public BufferedImage binaryFilterRuido() {
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }

        for (int i = 1; i < imageMatriz.length - 1; i++) {
            for (int j = 1; j < imageMatriz[i].length - 1; j++) {
                int a = (int) imageMatriz[i - 1][j - 1] + 1;
                int b = (int) imageMatriz[i - 1][j] + 1;
                int c = (int) imageMatriz[i - 1][j + 1] + 1;
                int d = (int) imageMatriz[i][j - 1] + 1;
                int p = (int) imageMatriz[i][j] + 1;
                int e = (int) imageMatriz[i][j + 1] + 1;
                int f = (int) imageMatriz[i + 1][j - 1] + 1;
                int g = (int) imageMatriz[i + 1][j] + 1;
                int h = (int) imageMatriz[i + 1][j + 1] + 1;

                int valor = p * ((a + b + d) * (e + g + h) + (b + c + e) * (d + f + g));
                if (valor != 0) {
                    newMatrix[i][j] = (byte) 0;
                } else
                    newMatrix[i][j] = (byte) 255;
            }
        }
        imageMatriz = newMatrix;
        pictureFinal = toBufferedImage();
        return pictureFinal;
    }

    // TODO: 30/04/16 Preguntar por lo del filtro a usar

    public BufferedImage adelgazamiento() {
        adelgazar();

        pictureFinal = toBufferedImage();

        return pictureFinal;
    }

    private void adelgazar() {
        byte[][] newMatrix = new byte[imageMatriz.length][imageMatriz[1].length];
        for (int i = 0; i < imageMatriz.length; i++) {
            for (int j = 0; j < imageMatriz[i].length; j++) {
                newMatrix[i][j] = imageMatriz[i][j];
            }
        }
        boolean rep = true;
        while (rep) {
            rep = false;
            for (int i = 1; i <= imageMatriz.length - 2; i++) {
                for (int j = 1; j <= imageMatriz[i].length - 2; j++) {
                    int p1 = (int) imageMatriz[i - 1][j - 1] + 1;
                    int p2 = (int) imageMatriz[i - 1][j] + 1;
                    int p3 = (int) imageMatriz[i - 1][j + 1] + 1;
                    int p4 = (int) imageMatriz[i][j - 1] + 1;
                    int p5 = (int) imageMatriz[i][j];
                    int p6 = (int) imageMatriz[i][j + 1] + 1;
                    int p7 = (int) imageMatriz[i + 1][j - 1] + 1;
                    int p8 = (int) imageMatriz[i + 1][j] + 1;
                    int p9 = (int) imageMatriz[i + 1][j + 1] + 1;

                    if (p5 != -1 && (p1 == 0 && p2 == 0 && p3 == 0 && p7 == 1 && p8 == 1 && p9 == 1)) {
                        p5 = (byte) 255;
                        rep = true;
                    } // abajo

                    if (p5 != -1 && p1 == 0 && p4 == 0 && p7 == 0 && p3 == 1 && p6 == 1 && p9 == 1) {
                        p5 = (byte) 255;
                        rep = true;
                    } // derecha
                    if (p5 != -1 && p7 == 0 && p8 == 0 && p9 == 0 && p1 == 1 && p2 == 1 && p3 == 1) {
                        p5 = (byte) 255;
                        rep = true;
                    } // arriba
                    if (p5 != -1 && p3 == 0 && p6 == 0 && p9 == 0 && p1 == 1 && p4 == 1 && p7 == 1) {
                        p5 = (byte) 255;
                        rep = true;
                    } // izquierda


                    if (p5 != -1 && p1 == 0 && p2 == 0 && p4 == 0 && p6 == 1 && p8 == 1) { // abajo derecha
                        p5 = (byte) 255;
                        rep = true;
                    }
                    if (p5 != -1 && p2 == 0 && p3 == 0 && p6 == 0 && p4 == 1 && p8 == 1) { //abajo izquierda
                        p5 = (byte) 255;
                        rep = true;
                    }
                    if (p5 != -1 && p6 == 0 && p8 == 0 && p9 == 0 && p2 == 1 && p4 == 1) { //arriba izquierda
                        p5 = (byte) 255;
                        rep = true;
                    }
                    if (p5 != -1 && p4 == 0 && p7 == 0 && p8 == 0 && p2 == 1 && p6 == 1) { //arriba derecha
                        p5 = (byte) 255;
                        rep = true;
                    }


                    newMatrix[i][j] = (byte) p5;
                }
            }

            imageMatriz = newMatrix;
        }

    }
}
