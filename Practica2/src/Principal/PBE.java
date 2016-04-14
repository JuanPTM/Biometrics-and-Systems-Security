package Principal;

import Header.Header;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by JuanP on 05/03/2016.
 * <p>
 * Clase encargada del cifrado y descifrado.
 *
 * @author Juan Pedro Torres Muñoz
 * @version 3
 */


public class PBE {
    private byte[] salt = new byte[8];
    private int numIte;
    private Header cab;


    public PBE() {
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        numIte = 50;
        cab = new Header();

    }

    /***
     * Constructor parametrizado de la clase PBE
     *
     * @param s Array de bytes para inicializar la semilla.
     * @param i Numero de iteraciones.
     * @param h Clase header encargada del trabajo con la cabecera.
     */
    public PBE(byte[] s, int i, Header h) {
        salt = s;
        numIte = i;
        cab = h;
    }

    /***
     * Metodo encargado de cifrar con las caracteristicas pedidas.
     *
     * @param pasS        Frase de paso con la que se cifrará el archivo.
     * @param passRep     Frase de paso para asegurarse de que es igual a la anterior y por tanto la deseada.
     * @param algorithm   Algoritmo utilizado para cifrar el archivo.
     * @param rutaArchivo Ruta del archivo a cifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida,
     * 5 en caso de no poder escribir la cabecera,7 si la la ruta esta vacía,6 si las contraseñas no son iguales o estan vacias,
     * 10 si el fichero no es valido y 99 si es un error desconocido.
     */
    public int cifrar(char[] pasS, char[] passRep, String algorithm, String rutaArchivo) {

        if (!rutaArchivo.equals("")) {
            String contraS1 = String.copyValueOf(pasS);
            String contraR1 = String.copyValueOf(passRep);
            if (rutaArchivo.substring(rutaArchivo.length() - 3, rutaArchivo.length()).equals("cif")) {
                return 10;
            }
            if (contraS1.equals(contraR1) && !contraS1.isEmpty()) {
                return _cifrar(contraS1, algorithm, rutaArchivo);
            } else {
                return 6;
            }
        } else {
            return 7;
        }
    }


    /***
     * Metodo encargado de descifrar un archivo con los datos proporcionados.
     *
     * @param pass         Frase de paso con la que se descifrará el archivo.
     * @param rutaArchivoC Ruta del archivo a descifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida,
     * 4 en caso de no reconocer la cabecera del archivo cifrado, 9 si encuentra problemas con el padding,10 si el fichero no es valido,
     * 7 si la ruta no es valida, 8 si la contraseña esta vacía y 99 si es un error desconocido.
     */
    public int descifrar(char[] pass, String rutaArchivoC) {

        if (!rutaArchivoC.equals("")) {
            String passwd = String.copyValueOf(pass);
            if (!rutaArchivoC.substring(rutaArchivoC.length() - 3, rutaArchivoC.length()).equals("cif")) {
                return 10;
            }
            if (!passwd.equals("")) {
                return _descifrar(passwd, rutaArchivoC);
            } else {
                return 8;
            }
        } else {
            return 7;
        }
    }

    private int _cifrar(String pass, String algorithm, String rutaArchivo) {
        FileInputStream fin;
        FileOutputStream fos;
        CipherOutputStream cos;

        try {
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            cab = new Header(algorithm, salt);

            String rutaFinal = rutaArchivo.substring(0, rutaArchivo.length() - 3);
            rutaFinal = rutaFinal + "cif";
            fin = new FileInputStream(rutaArchivo);
            fos = new FileOutputStream(rutaFinal);

            if (cab.save(fos)) {

                PBEKeySpec pbeKeySpec = new PBEKeySpec(pass.toCharArray());
                PBEParameterSpec pPS = new PBEParameterSpec(salt, numIte);
                SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
                SecretKey sKey = kf.generateSecret(pbeKeySpec);
                Cipher c = Cipher.getInstance(algorithm);
                c.init(Cipher.ENCRYPT_MODE, sKey, pPS);
                cos = new CipherOutputStream(fos, c);

                byte[] buffer = new byte[1024];
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
            } else {
                return 5;
            }

        } catch (FileNotFoundException e) {
            return 2;
        } catch (InvalidKeyException e) {
            return 3;
        } catch (Exception e) {
            e.printStackTrace();
            return 99;
        }
        return 0;
    }

    private int _descifrar(String pass, String rutaArchivoC) {
        byte[] buffer = new byte[1024];
        FileInputStream fin;
        FileOutputStream fos;
        String algorithm;

        try {
            fin = new FileInputStream(rutaArchivoC);
            if (cab.load(fin)) {

                /**
                 * Redundante en caso base - Necesario si cambia el salt al cifrar.
                 */
                salt = cab.getSalt();
                algorithm = cab.getAlgorithm();

                PBEKeySpec pbeKeySpec = new PBEKeySpec(pass.toCharArray());
                PBEParameterSpec pPS = new PBEParameterSpec(salt, numIte);
                SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
                SecretKey sKey = kf.generateSecret(pbeKeySpec);
                Cipher c = Cipher.getInstance(algorithm);
                c.init(Cipher.DECRYPT_MODE, sKey, pPS);


                CipherInputStream cin = new CipherInputStream(fin, c);
                String rutaSalida = rutaArchivoC.substring(0, rutaArchivoC.length() - 3);
                rutaSalida = rutaSalida + "cla";
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
            } else {
                return 4;
            }
        } catch (FileNotFoundException e) {
            return 2;
        } catch (InvalidKeyException e) {
            return 3;
        } catch (NoSuchAlgorithmException e) {
            return 99;
        } catch (InvalidKeySpecException e) {
            return 9;
        } catch (NoSuchPaddingException e) {
            return 9;
        } catch (InvalidAlgorithmParameterException e) {
            return 99;
        } catch (IOException e) {
            return 9;
        }

        return 0;
    }
}
