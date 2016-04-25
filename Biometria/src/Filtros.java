import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by juanp on 20/04/16.
 */
public class Filtros {
    private static Filtros ourInstance = new Filtros();
    BufferedImage mypicture;
    BufferedImage pictureFinal;

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
            }
        }
        pictureFinal = newImage;
        getHistogram();
        return newImage;

    }

    public void loadImage(String path) {

        try {
            mypicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pintar(BufferedImage im1, BufferedImage im2) {

        int rgb = im1.getRGB(500, 500);
        int red = (rgb >> 16) & 0x000000FF;
        int green = (rgb >> 8) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;


        System.out.println("El valor ROJO,VERDE,AZUL son: " + red + green + blue + "El valor medio es " + (im2.getRGB(500, 500) & 0x000000FF));
    }

    public boolean isLoad() {
        if (mypicture != null)
            return true;
        else
            return false;
    }

    public int[] getHistogram() {
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
        System.out.println(Arrays.toString(histogram));

        return histogram;
    }

    public BufferedImage thresholdFilter(int umbral){
        BufferedImage newImage = new BufferedImage(mypicture.getWidth(),
                mypicture.getHeight(), mypicture.getType());
        int blanco = (255 << 8) | 255;
        blanco = (blanco << 8) | 255;
        int valueGrey;

        for (int i = 0; i < mypicture.getHeight(); i++) {
            for (int j = 0; j < mypicture.getWidth(); j++) {
                valueGrey = mypicture.getRGB(j, i) & 0xFF;
                if ( valueGrey < umbral){
                    newImage.setRGB(j,i,0);
                }else{
                    newImage.setRGB(j,i,blanco);
                }
            }
        }

        pictureFinal = newImage;
        return newImage;
    }
}
