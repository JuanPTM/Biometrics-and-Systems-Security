package principal;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by JuanP on 20/03/2016.
 *
 * @author Juan Pedro Torres Muñoz
 * @version 2.6
 */
public class Gui extends JFrame {
    private JTabbedPane TabPanel;
    private JPanel RootPanel;
    private JTextField ruta;
    private JButton buscarButton;
    private JPanel ccPanel;
    private JPanel infoPanel;
    private JPanel acerPanel;
    private JButton cifrarButton;
    private JButton descifrarButton;
    private JPanel rutaPanel;
    private JPanel labPanel;
    private JPanel buscPanel;
    private JPanel rutPanel;
    private JPanel descPanel;
    private JPanel cifPanel;
    private JButton firmarButton;
    private JButton verificarButton;
    private JPanel rutfPanel;
    private JTextField rutaFir;
    private JButton buscarfButton;
    private JPanel rutafPanel;
    private JPanel buscfPanel;
    private JPanel labfPanel;
    private JPanel veriPanel;
    private JPanel firPanel;
    private JButton mostrarClavesButton;
    private JPanel mostPanel;
    private JButton generarClavesButton;
    private JPanel genclPanel;
    private JComboBox algCif;
    private JComboBox algFirm;
    private JPanel optPanel;
    private CriptAsim asim;

    /***
     * Constructor por defecto de la Interfaz Gráfica.
     * Inicializa la interfaz y crea los listener.
     */
    public Gui() {
        super("Práctica 3");
        init();
        addListener();
        asim = CriptAsim.getInstancia();
        setVisible(true);
    }

    private void init() {
        setContentPane(RootPanel);
        pack();
        Dimension d = getSize();
        setMinimumSize(d);
        setLocation(400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon(this.getClass().getResource("/res/by-nc-sa.png"));
        JLabel label = new JLabel("", image, JLabel.CENTER);
        ccPanel.add(label);
    }

    private void addListener() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int resp = fc.showOpenDialog(Gui.this);

                if (resp == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    ruta.setText(f.getAbsolutePath());
                }
            }
        });

        buscarfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int resp = fc.showOpenDialog(Gui.this);
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);


                if (resp == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    rutaFir.setText(f.getAbsolutePath());
                }
            }
        });

        algCif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rellenoAlgFirm(algCif.getSelectedItem().toString());
            }
        });

        generarClavesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int err;
                desactivarGUI();
                err = asim.genClaves(algCif.getSelectedItem().toString());
                activarGUI();

                msgErr(err);
            }
        });

        mostrarClavesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (asim.getPku() != null) {
                    JOptionPane.showMessageDialog(RootPanel, "Clave Pública:\n" + asim.getPku().toString() + "\nClave Privada:\n" + asim.getPkr().toString(), "Clave Pública", JOptionPane.PLAIN_MESSAGE);
                } else {
                    msgErr(95);
                }
            }
        });

        firmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (asim.getPku() != null) {

                    int err;

                    if (!rutaFir.getText().equals("")) {
                        desactivarGUI();
                        err = asim.firmar(algFirm.getSelectedItem().toString(), rutaFir.getText());
                        activarGUI();
                    } else {
                        err = 7;
                    }
                    msgErr(err);
                } else {
                    msgErr(95);
                }

            }
        });

        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (asim.getPku() != null) {
                    int err;

                    if (!rutaFir.getText().equals("")) {
                        desactivarGUI();
                        err = asim.verificar(rutaFir.getText());
                        activarGUI();
                    } else {
                        err = 7;
                    }

                    msgErr(err);
                } else {
                    msgErr(95);
                }
            }
        });

        cifrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (asim.getPku() != null && asim.getPku().getAlgorithm().equals("RSA")) {
                    int err;

                    if (!ruta.getText().equals("")) {
                        desactivarGUI();
                        err = asim.cifrar(ruta.getText());
                        activarGUI();
                    } else {
                        err = 7;
                    }
                    msgErr(err);
                } else {
                    if (asim.getPku() == null)
                        msgErr(95);
                    else
                        msgErr(96);
                }
            }
        });

        descifrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (asim.getPku() != null && asim.getPkr().getAlgorithm().equals("RSA")) {
                    int err;

                    if (!ruta.getText().equals("")) {
                        desactivarGUI();
                        err = asim.descifrar(ruta.getText());
                        activarGUI();
                    } else {
                        err = 7;
                    }
                    msgErr(err);
                } else {
                    if (asim.getPku() == null)
                        msgErr(95);
                    else
                        msgErr(96);
                }

            }
        });

        TabPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateFirmAlg();
            }
        });

    }

    private void updateFirmAlg() {
        if (asim != null && asim.getPku() != null) {
            algFirm.removeAllItems();
            String alg = asim.getPku().getAlgorithm();
            if (alg.equals("RSA")) {
                algFirm.addItem("SHA1withRSA");
                algFirm.addItem("MD2withRSA");
                algFirm.addItem("MD5withRSA");
            }
            if (alg.equals("DSA")) {
                algFirm.addItem("SHA1withDSA");
            }
        }
    }

    private void rellenoAlgFirm(String algoritmo) {
        algFirm.removeAllItems();
        if (algoritmo.equals("RSA")) {
            algFirm.addItem("SHA1withRSA");
            algFirm.addItem("MD2withRSA");
            algFirm.addItem("MD5withRSA");
        }
        if (algoritmo.equals("DSA")) {
            algFirm.addItem("SHA1withDSA");
        }
    }

    private void msgErr(int err) {
        switch (err) {
            case 0:
                JOptionPane.showMessageDialog(RootPanel, "El proceso ha finalizado correctamente.", "Proceso finalizado.", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1:
                JOptionPane.showMessageDialog(RootPanel, "El algoritmo no existe.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(RootPanel, "El fichero no puede abrirse.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(RootPanel, "Las firma no es correcta.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(RootPanel, "La firma es correcta.", "Proceso finalizado.", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 5:
                JOptionPane.showMessageDialog(RootPanel, "Error inesperado durante el proceso.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 6:
                JOptionPane.showMessageDialog(RootPanel, "Error al leer la cabecera.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 7:
                JOptionPane.showMessageDialog(RootPanel, "Introduzca la ruta del fichero.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 8:
                JOptionPane.showMessageDialog(RootPanel, "El fichero no es valido.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 95:
                JOptionPane.showMessageDialog(RootPanel, "Por favor genere una llave primero.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 96:
                JOptionPane.showMessageDialog(RootPanel, "Su llave solo puede firmar,genere unas llaves RSA para cifrar.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 97:
                JOptionPane.showMessageDialog(RootPanel, "No se ha podido guardar el resultado.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 98:
                JOptionPane.showMessageDialog(RootPanel, "La llave no es válida o no tiene llaves.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(RootPanel, "Error inesperado.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void desactivarGUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cifrarButton.setEnabled(false);
        TabPanel.setEnabled(false);
        algCif.setEnabled(false);
        algFirm.setEnabled(false);
        ruta.setEnabled(false);
        firmarButton.setEnabled(false);
        verificarButton.setEnabled(false);
        rutaFir.setEnabled(false);
        buscarfButton.setEnabled(false);
        mostrarClavesButton.setEnabled(false);
        generarClavesButton.setEnabled(false);

    }

    private void activarGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cifrarButton.setEnabled(true);
        TabPanel.setEnabled(true);
        algCif.setEnabled(true);
        algFirm.setEnabled(true);
        ruta.setEnabled(true);
        firmarButton.setEnabled(true);
        verificarButton.setEnabled(true);
        rutaFir.setEnabled(true);
        buscarfButton.setEnabled(true);
        mostrarClavesButton.setEnabled(true);
        generarClavesButton.setEnabled(true);
    }
}
