package Principal;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;

/**
 * Created by JuanP on 05/03/2016.
 *
 * Clase encargada del cifrado y descifrado.
 *
 */


public class PBE {
    private byte[] salt;
    private int numIte;

    public PBE(){
        salt = new byte[] { 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae };
        numIte = 20; // TODO: 05/03/2016 Revisar tras preguntarle lo de numIte y lo de salt
    }

    /***
     *
     * Constructor parametrizado de la clase PBE
     *
     * @param s Array de bytes para inicializar la semilla.
     * @param i Numero de iteraciones.
     */
    public PBE(byte[] s,int i){
        salt = s;
        numIte = i;
    }

    /***
     *
     * Metodo encargado de cifrar con las caracteristicas pedidas.
     *
     * @param pass Frase de paso con la que se cifrará el archivo.
     * @param algorithm Algoritmo utilizado para cifrar el archivo.
     * @param rutaArchivo Ruta del archivo a cifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida y 99 si es un error desconocido.
     */
    public int cifrar(String pass,String algorithm,String rutaArchivo){
        return _cifrar(pass,algorithm,rutaArchivo);
    }


    /***
     *
     * Metodo encargado de descifrar un archivo con los datos proporcionados.
     *
     * @param pass Frase de paso con la que se descifrará el archivo.
     * @param algorithm Algoritmo utilizado para descifrar el archivo.
     * @param rutaArchivoC Ruta del archivo a cifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida y 99 si es un error desconocido.
     */
    public int descifrar(String pass,String algorithm,String rutaArchivoC){
        return _descifrar(pass,algorithm,rutaArchivoC);
    }

    private int _cifrar(String pass,String algorithm,String rutaArchivo) {
        FileInputStream fin;
        FileOutputStream fos;
        CipherOutputStream cos;
        PBEKeySpec pbeKeySpec = new PBEKeySpec(pass.toCharArray());
        PBEParameterSpec pPS = new PBEParameterSpec(salt, numIte);

        try {
            SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
            SecretKey sKey = kf.generateSecret(pbeKeySpec);
            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.ENCRYPT_MODE, sKey, pPS);
            String rutaFinal = rutaArchivo.substring(0, rutaArchivo.length() - 3);
            rutaFinal = rutaFinal + "cif";
            fin = new FileInputStream(rutaArchivo);
            fos = new FileOutputStream(rutaFinal);
            cos = new CipherOutputStream(fos, c);
            byte[] buffer = new byte[1024]; // TODO: 05/03/2016 Revisar tras preguntarle el tamaño del buffer
            int ite;

            ite = fin.read(buffer);
            do {
                cos.write(buffer, 0, ite);
                ite = fin.read(buffer);
            } while (ite != -1);

            cos.flush();
            fos.flush();
            fin.close();
            cos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            return 2;
        }catch(InvalidKeyException e){
            return 3;
        }catch (Exception e){
            e.printStackTrace();
            return 99;
        }
        return 0;
    }

    private int _descifrar(String pass,String algorithm,String rutaArchivoC){
        byte[] buffer = new byte[1024]; // TODO: 05/03/2016 Revisar tras preguntarle el tamaño del buffer
        FileInputStream fin;
        FileOutputStream fos;
        PBEKeySpec pbeKeySpec = new PBEKeySpec(pass.toCharArray());
        PBEParameterSpec pPS = new PBEParameterSpec(salt,numIte);

        try {
            SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
            SecretKey sKey = kf.generateSecret(pbeKeySpec);
            fin = new FileInputStream(rutaArchivoC);
            Cipher c = Cipher.getInstance(algorithm);
            c.init(Cipher.DECRYPT_MODE, sKey, pPS);

            CipherInputStream cin = new CipherInputStream(fin, c);
            String rutaSalida = rutaArchivoC.substring(0,rutaArchivoC.length()-3);
            rutaSalida = rutaSalida +"cla";
            System.out.println(rutaSalida);
            fos = new FileOutputStream(rutaSalida);

            int ite;

            ite = cin.read(buffer);
            do {
                fos.write(buffer, 0, ite);
                ite = cin.read(buffer);
            } while (ite != -1);

            fos.flush();
            cin.close();
            fos.close();
            fin.close();

        }catch (FileNotFoundException e){
            return 2;
        }catch (InvalidKeyException e){
            return 3;
        }catch (Exception e){
            e.printStackTrace();
            return 99;
        }
        return 0;
    }
}
