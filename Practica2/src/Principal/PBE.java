package Principal;

import Header.Header;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by JuanP on 05/03/2016.
 *
 * Clase encargada del cifrado y descifrado.
 *
 * @version 2.1
 * @author Juan Pedro Torres Muñoz
 *
 */


public class PBE {
    private byte[] salt;
    private int numIte;
    private Header cab;


    public PBE(){
        SecureRandom random = new SecureRandom();
        salt = new byte[] { 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae };
        random.nextBytes(salt);
        numIte = 20; // TODO: 05/03/2016 Revisar tras preguntarle lo de numIte y lo de salt
        cab = new Header();
        
    }

    /***
     *
     * Constructor parametrizado de la clase PBE
     *
     * @param s Array de bytes para inicializar la semilla.
     * @param i Numero de iteraciones.
     */
    public PBE(byte[] s,int i,Header h){
        salt = s;
        numIte = i;
        cab = h;
    }

    /***
     *
     * Metodo encargado de cifrar con las caracteristicas pedidas.
     *
     * @param pass Frase de paso con la que se cifrará el archivo.
     * @param algorithm Algoritmo utilizado para cifrar el archivo.
     * @param rutaArchivo Ruta del archivo a cifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida,
     *         5 en caso de no poder escribir la cabecera y 99 si es un error desconocido.
     */
    public int cifrar(String pass,String algorithm,String rutaArchivo){
        return _cifrar(pass,algorithm,rutaArchivo);
    }


    /***
     *
     * Metodo encargado de descifrar un archivo con los datos proporcionados.
     *
     * @param pass Frase de paso con la que se descifrará el archivo.
     * @param rutaArchivoC Ruta del archivo a descifrar.
     * @return Retornará 0 en caso de que no sucedan errores, 2 si no existe el archivo indicado, 3 si la clave es invalida,
     *         4 en caso de no reconocer la cabecera del archivo cifrad y 99 si es un error desconocido.
     */
    public int descifrar(String pass,String rutaArchivoC){
        return _descifrar(pass,rutaArchivoC);
    }

    private int _cifrar(String pass,String algorithm,String rutaArchivo) {
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

            if(cab.save(fos)) {

                PBEKeySpec pbeKeySpec = new PBEKeySpec(pass.toCharArray());
                PBEParameterSpec pPS = new PBEParameterSpec(salt, numIte);
                SecretKeyFactory kf = SecretKeyFactory.getInstance(algorithm);
                SecretKey sKey = kf.generateSecret(pbeKeySpec);
                Cipher c = Cipher.getInstance(algorithm);
                c.init(Cipher.ENCRYPT_MODE, sKey, pPS);
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
            }else {
                return 5;
            }

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

    private int _descifrar(String pass,String rutaArchivoC){
        byte[] buffer = new byte[1024]; // TODO: 05/03/2016 Revisar tras preguntarle el tamaño del buffer
        FileInputStream fin;
        FileOutputStream fos;
        String algorithm;

        try {
            fin = new FileInputStream(rutaArchivoC);
            if(cab.load(fin)) {

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
            }else{
                return 4;
            }
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
