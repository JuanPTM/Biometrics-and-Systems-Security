package Principal;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.KeySpec;
import java.util.Scanner;

/**
 * Created by JuanP on 03/03/2016
 * 
 */
public class Principal {
    public static void main(String[] args) {
        Gui g = new Gui();
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
/**
        //TODO implementar primeros pasos con Scanner y sin cabecera.
        byte[] salt = new byte[] { 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae };
        Scanner in = new Scanner(System.in);
        System.out.println("Introduzca contraseña : ");
        String pass1 = in.next();
        System.out.println("Repita contraseña : ");
        String pass2 = in.next();


        if (pass1.equals(pass2)){
            System.out.println("Cifrando...");


            PBEKeySpec pbeKeySpec = new PBEKeySpec(pass1.toCharArray());
            PBEParameterSpec pPS = new PBEParameterSpec(salt,20);
            try {
                SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEwithMD5AndDES");
                SecretKey sKey = kf.generateSecret((KeySpec) pbeKeySpec);
                Cipher c = Cipher.getInstance("PBEwithMD5AndDES");
                c.init(Cipher.ENCRYPT_MODE,sKey,pPS);
                FileOutputStream fos = new FileOutputStream("candado.cif");
                FileInputStream fin = new FileInputStream("candado.png");
                CipherOutputStream cos = new CipherOutputStream(fos,c);
                byte[] buffer = new byte[512];
                int ite;


                ite = fin.read(buffer);
                do{
                    cos.write(buffer,0,ite);
                    ite = fin.read(buffer);
                }while (ite != -1);

                fin.close();
                cos.close();
                fos.close();
                FileInputStream fin1 = new FileInputStream("candado.cif");
                c.init(Cipher.DECRYPT_MODE,sKey,pPS);
                CipherInputStream cin = new CipherInputStream(fin1,c);
                fos = new FileOutputStream("candadin.png");

                ite = cin.read(buffer);
                do{
                    fos.write(buffer,0,ite);
                    ite = cin.read(buffer);
                }while (ite != -1);

                fos.close();
                fin.close();
                cin.close();
                System.out.println("Done");

            }catch (Exception e){
                e.printStackTrace();
            }



        }else {
            System.out.println("Las contraseñas no coinciden.");
        }
 */
    }
}
