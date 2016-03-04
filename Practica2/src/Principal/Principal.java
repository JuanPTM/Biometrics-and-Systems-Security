package Principal;

import java.io.Console;
import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by JuanP on 03/03/2016
 */
public class Principal {
    public static void main(String[] args) {
        /**
         *
         * Bloque para leer por consola sin mostrar lo que escribimos, puede ser util más adelante.
         * Para ejecutar por consola -> desde fuera del paquete  "java nombrepaquete.Principal"
         *
         *
         */

        /*Console cons = System.console();
        char[] pass = cons.readPassword("%s","Introduzca contraseña : ");
        System.out.println(pass);*/

        //TODO implementar primeros pasos con Scanner y sin cabecera.

        Scanner in = new Scanner(System.in);
        System.out.println("Introduzca contraseña : ");
        String pass1 = in.next();
        System.out.println("Repita contraseña : ");
        String pass2 = in.next();

        if (pass1.equals(pass2)){
            System.out.println("Cifrando...");
            //TODO implementar el cifrado del fichero en args[] ó pedido el nombre
        }else {
            System.out.println("Las contraseñas no coinciden.");
        }


    }
}
