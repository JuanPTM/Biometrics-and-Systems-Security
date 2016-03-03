package biometria;

import java.io.Console;
import java.util.Scanner;
import java.io.Console.*;

/**
 * Created by JuanP on 03/03/2016.
 *
 *
 */
public class principal {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String contraseña = new String();
        System.out.println("Introduce la contraseña : ");
        contraseña = in.nextLine();
      //  Console e = new Console(in);
       // contraseña = e.readPassword();


        Console cons;
        char[] passwd = new char[0];
        if ((cons = System.console()) != null &&
                (passwd = cons.readPassword("[%s]", "Password:")) != null) {
            java.util.Arrays.fill(passwd, ' ');
        }


        System.out.println(passwd);



    }
}
