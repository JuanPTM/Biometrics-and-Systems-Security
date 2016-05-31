package Tratamiento;

/**
 * Created by juanp on 12/05/16.
 * <p>
 * Clase encargada del calculo del gradiente para la obtención de los ángulos de las minucias.
 *
 * @author Juan Pedro Torres Muñoz
 */
public class Gradiente {
    private static byte[][] image;
    private static int ventana = 5;

    /**
     * Constructor de la clase.
     *
     * @param imageRec imagen sobre la que se calculará el gradiente.
     */
    public Gradiente(byte[][] imageRec) {
        image = imageRec;
    }

    /***
     * Método que asigna una nueva imagen sobre la que trabajar.
     *
     * @param image1 nueva imagen.
     */
    public static void setImage(byte[][] image1) {
        image = image1;
    }

    /**
     * Método que asigna una nueva ventana para el calculo del gradiente.
     *
     * @param ventana1 Tamaño de la nueva ventana, es aconsejable que sea impar.
     */
    public static void setVentana(int ventana1) {
        ventana = ventana1;
    }

    /***
     * Método que calcula el gradiente en el eje X de un pixel dado.
     *
     * @param x coordenada x del pixel.
     * @param y coordenada y del pixel.
     * @return valor del gradiente en X.
     */
    private static int getGradienteX(int x, int y) {
        int z7 = image[x + 1][y - 1] + 1;
        int z8 = image[x + 1][y] + 1;
        int z9 = image[x + 1][y + 1] + 1;
        int z1 = image[x - 1][y - 1] + 1;
        int z2 = image[x - 1][y] + 1;
        int z3 = image[x - 1][y + 1] + 1;

        return (z7 + 2 * z8 + z9) - (z1 + 2 * z2 + z3);
    }

    /***
     * Método que calcula el gradiente en el eje Y de un pixel dado.
     *
     * @param x coordenada x del pixel.
     * @param y coordenada y del pixel.
     * @return valor del gradiente en Y.
     */
    private static int getGradienteY(int x, int y) {
        int z3 = image[x - 1][y + 1] + 1;
        int z6 = image[x][y + 1] + 1;
        int z9 = image[x + 1][y + 1] + 1;
        int z1 = image[x - 1][y - 1] + 1;
        int z4 = image[x][y - 1] + 1;
        int z7 = image[x + 1][y - 1] + 1;

        return (z3 + (2 * z6) + z9) - (z1 + (2 * z4) + z7);

    }

    /***
     * Método que calcula el dividendo para obtener el ángulo de las minucias.
     *
     * @param x coordenada x del pixel.
     * @param y coordenada y del pixel.
     * @return valor del dividendo para el cálculo del ángulo de las minucias.
     */
    public static int getDivi(int x, int y) {
        int suma = 0;

        for (int i = -(ventana / 2); i <= ventana / 2; i++) {
            for (int j = -(ventana / 2); j <= ventana / 2; j++) {
                int Gx = getGradienteX(x + i, y + j);
                int Gy = getGradienteY(x + i, y + j);
                if (Gx != 0 && Gy != 0)
                    suma += (2 * Gx) + Gy;
            }
        }

        return suma;
    }

    /***
     * Método que calcula el divisor para obtener el ángulo de las minucias.
     *
     * @param x coordenada x del pixel.
     * @param y coordenada y del pixel.
     * @return valor del divisor para el cálculo del ángulo de las minucias.
     */
    public static double getdivisor(int x, int y) {
        double suma = 0;

        for (int i = -(ventana / 2); i <= ventana / 2; i++) {
            for (int j = -(ventana / 2); j <= ventana / 2; j++) {
                int Gx = getGradienteX(x + i, y + j);
                int Gy = getGradienteY(x + i, y + j);
                if (Gx != 0 && Gy != 0)
                    suma = suma + ((Math.pow(Gx, 2)) - (Math.pow(Gy, 2)));
            }
        }

        return suma;
    }

}
