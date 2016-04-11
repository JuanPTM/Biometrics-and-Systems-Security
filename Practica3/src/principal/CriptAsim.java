package principal;

import header.Header;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by JuanP on 20/03/2016.
 *
 * Clase encargada del cifrado,descifrado,firma y verificación.
 *
 * @author Juan Pedro Torres Muñoz
 * @version 2.6
 *
 */
public class CriptAsim {
    private static CriptAsim asim = null;
    private PublicKey pku;
    private PrivateKey pkr;
    private Header cab;

    private CriptAsim() {
        cab = new Header();
        int err = loadKeyPair("RSA");
        if (err == 3){
            loadKeyPair("DSA");
        }
    }

    private int loadKeyPair(String algorithm){
        byte[] encodedPublicKey ;
        byte[] encodedPrivateKey ;

        try {
            // Read Public Key.
            File filePublicKey = new File("public.key");
            FileInputStream fis = new FileInputStream("public.key");
            encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Read Private Key.
            File filePrivateKey = new File("private.key");
            fis = new FileInputStream("private.key");
            encodedPrivateKey = new byte[(int) filePrivateKey.length()];
            fis.read(encodedPrivateKey);
            fis.close();
        } catch (IOException e) {
            return 1;
        }

        try {
            // Generate KeyPair.
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            pku = keyFactory.generatePublic(publicKeySpec);

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            pkr = keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException e) {
            return 2;
        } catch (InvalidKeySpecException e) {
            return 3;
        }
        return 0;
    }

    public void saveKeyPair(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        try {
            // Store Public Key.
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            FileOutputStream fos = new FileOutputStream("public.key");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();

            // Store Private Key.
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                    privateKey.getEncoded());
            fos = new FileOutputStream("private.key");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CriptAsim(PublicKey pku, PrivateKey pkr, Header cab) {
        this.pku = pku;
        this.pkr = pkr;
        this.cab = cab;
    }

    private int generarClaves(String algCif){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algCif);
            kpg.initialize(512);
            KeyPair keyPair = kpg.generateKeyPair();

            saveKeyPair(keyPair);

            pku = keyPair.getPublic();
            pkr = keyPair.getPrivate();

            System.out.println(pku.toString());
            return 0;
        } catch (NoSuchAlgorithmException e) {
            return 1;
        }
    }

    private int _firmar(String algorithm,String path) {
        byte[] buffer = new byte[1024];
        FileInputStream fis;
        FileOutputStream fos;
        String sPath = path.substring(0,path.length()-3)+"fir";

        try {
            fis = new FileInputStream(path);
            Signature dsa = Signature.getInstance(algorithm);
            dsa.initSign(pkr);


            int ite;
            ite = fis.read(buffer);
            do {
                dsa.update(buffer,0,ite);
                ite = fis.read(buffer);
            }while(ite > 0);

            byte[] sig = dsa.sign();

            fos = new FileOutputStream(sPath);
            cab = new Header(algorithm,sig);
            cab.save(fos);
            fis.close();
            fis = new FileInputStream(path);

            ite = fis.read(buffer);
            do{
               fos.write(buffer,0,ite);
               ite = fis.read(buffer);
            }while (ite > 0);

            fos.flush();
            fos.close();
            fis.close();

            return 0;

        } catch (FileNotFoundException e){
            return 2;
        } catch (NoSuchAlgorithmException e) {
            return 1;
        } catch (InvalidKeyException e) {
            return 98;
        } catch (SignatureException e) {
            return 5;
        } catch (IOException e) {
            return 97;
        }
    }

    private int _verificar(String path){
        byte[] buffer = new byte[1024];
        FileInputStream fis;
        FileOutputStream fos;

        if(!path.substring(path.length()-3,path.length()).equals("fir")){
            return 8;
        }

        try {

            fis = new FileInputStream(path);
            if(cab.load(fis)) {
                byte[] sig = cab.getSign();
                Signature dsa = Signature.getInstance(cab.getSigner());
                dsa.initVerify(pku);


                int ite;
                ite = fis.read(buffer);
                do {
                    dsa.update(buffer,0,ite);
                    ite = fis.read(buffer);
                } while (ite > 0);
                fis.close();

                if (dsa.verify(sig)) {

                    fis = new FileInputStream(path);
                    fos = new FileOutputStream(path.substring(0,path.length()-3)+"cla");
                    cab.load(fis);

                    ite = fis.read(buffer);
                    do {
                        fos.write(buffer,0,ite);
                        ite = fis.read(buffer);
                    } while (ite > 0);
                    fis.close();
                    fos.flush();
                    fos.close();

                    return 4;

                } else {
                    return 3;
                }
            }else{
                return 6;
            }
        } catch (IOException e) {
            return 2;
        } catch (NoSuchAlgorithmException e) {
            return 1;
        } catch (InvalidKeyException e) {
            return 98;
        } catch (SignatureException e) {
            return 5;
        }


    }

    private int _cifrar(String path){
        String oPath = path.substring(0,path.length()-3) + "cif";
        FileOutputStream foc;
        FileInputStream fis;
        byte[] out;

        try {
            fis = new FileInputStream(path);
            foc = new FileOutputStream(oPath);
            cab = new Header("RSA");
            cab.save(foc);

            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(c.ENCRYPT_MODE,pku);
            int blockSize = 53;
            byte[] buffer = new byte[blockSize];

            int ite;
            ite = fis.read(buffer);
            do{
                out = c.doFinal(buffer,0,ite);
                foc.write(out);
                ite = fis.read(buffer);
            }while (ite > 0);

            fis.close();
            foc.flush();
            foc.close();
            return 0;
        } catch (NoSuchAlgorithmException e) {
            return 1;
        } catch (NoSuchPaddingException e) {
            return 99;
        } catch (InvalidKeyException e) {
            return 98;
        } catch (IOException e) {
            return 97;
        } catch (IllegalBlockSizeException e) {
            return 99;
        } catch (BadPaddingException e) {
            return 99;
        }


    }

    private int _descifrar(String path){
        String oPath = path.substring(0,path.length()-3) + "cla";
        FileOutputStream foc;
        FileInputStream fis;
        byte[] out;

        if(!path.substring(path.length()-3,path.length()).equals("cif")){
            return 8;
        }

        try {
            fis = new FileInputStream(path);
            foc = new FileOutputStream(oPath);

            if (cab.load(fis)) {

                Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                c.init(c.DECRYPT_MODE, pkr);
                int blockSize = 64;
                byte[] buffer = new byte[blockSize];

                int ite;
                ite = fis.read(buffer);
                do {
                    out = c.doFinal(buffer, 0, ite);
                    foc.write(out);
                    ite = fis.read(buffer);
                } while (ite > 0);

                fis.close();
                foc.flush();
                foc.close();
                return 0;
            }
            return 6;
        } catch (NoSuchAlgorithmException e) {
            return 1;
        } catch (NoSuchPaddingException e) {
            return 98;
        } catch (InvalidKeyException e) {
            return 98;
        } catch (IOException e) {
            return 97;
        } catch (IllegalBlockSizeException e) {
            return 99;
        } catch (BadPaddingException e) {
            return 98;
        }

    }

    /***
     * Método encargado de firmar el fichero, dejando una cabecera con la firma y el contenido tras ella.
     *
     * @param algorithm algoritmo de firma seleccionado.
     * @param path ubicación del fichero a firmar.
     * @return Valor de control, 2 si no existe el fichero, 1 si no exite el algoritmo,98 si la clave no es valida,
     *          5 si se produce una excepcion en la firma,97 si no puede crear el fichero de salida
     *          y 0 si funciona correctamente.
     */
    public int firmar(String algorithm,String path){
        return _firmar(algorithm,path);
    }

    /***
     * Método que genera las claves y las almacena.
     *
     * @param algCif algoritmo para el que se quieren generar llaves(RSA o DSA).
     * @return Valor de control, 1 si no existe el algoritmo y 0 si no hay problemas.
     */
    public int genClaves(String algCif){
        return generarClaves(algCif);
    }

    /***
     * Método que nos permite verificar una firma.
     *
     * @param path Ubicación del fichero que contiene la firma.
     * @return Valor de control, 4 si la firma es correcta,3 si la firma no es correcta,2 si no se puedo escribir el fichero de salida,
     *          1 si no existe el algoritmo, 98 si la clave es invalida y 5 si se produce un error en la verificación,
     *          6 si la cabecera no corresponde a nuestro programa.
     */
    public int verificar(String path){
        return _verificar(path);
    }

    /***
     * Método que cifra un archivo dado en uno resultante con extensión ".cif".
     *
     * @param path Ubicación del fichero a cifrar
     * @return valor de control, 0 si se realizó correctamente, 1 si no existe el algoritmo, 97 si hubiera un error de i/o,
     *          98 si la clave no es valida, 99 si se produce un error en el cifrado.
     */
    public int cifrar(String path){
        return _cifrar(path);
    }

    /***
     * Método que descifra un archivo dado en uno resultante con extensión ".cla".
     *
     * @param path Ubicación del fichero a descifrar
     * @return valor de control, 0 si se realizó correctamente, 1 si no existe el algoritmo,6 si la cabecera
     *          no corresponde a nuestro programa,97 si hubiera un error de i/o,
     *          98 si la clave no es valida, 99 si se produce un error en el cifrado.
     */
    public int descifrar(String path){
        return _descifrar(path);
    }

    /***
     * Metodo que permite obtener la clave publica.
     *
     * @return Clave pública.
     */
    public PublicKey getPku() {
        return pku;
    }

    /***
     * Método que permite obtener la clave privada.
     *
     * @return Clave privada.
     */
    public PrivateKey getPkr() {
        return pkr;
    }

    /***
     * Método que nos permite la implementación de Singleton
     *
     * @return instancia de la clase CriptAsim, inicializada con las claves locales si las hay.
     */
    public static CriptAsim getInstancia(){
        if (asim == null){
            asim = new CriptAsim();
        }
        return asim;
    }

}
