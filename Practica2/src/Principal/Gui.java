package Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by JuanP on 04/03/2016.
 * <p>
 * Interfaz gráfica de la práctica 2 de BySS
 *
 * @author Juan Pedro Torres Muñoz
 * @version 2.1
 */
public class Gui extends JFrame {
    private JPanel RootPanel;
    private JTabbedPane tab;
    private JPasswordField contraText;
    private JPasswordField contraRepe;
    private JCheckBox mostrarCheckBox;
    private JButton cifrarButton;
    private JComboBox algoritmo;
    private JTextField rutaFicheroC;
    private JButton buscarButton;
    private JButton descifrarButton;
    private JTextField rutaDes;
    private JButton buscarDes;
    private JPasswordField contraDes;
    private JPanel TabPanel;
    private JPanel acerPanel;
    private JPanel ccPanel;
    private JPanel datosPanel;
    private JPanel titPanel;
    private JPanel desPanel;
    private JPanel cifPanel;


    public Gui() {

        super("Práctica 2");
        init();
        addListener();

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
        cifrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!rutaFicheroC.getText().equals("")) {
                    char[] contra = contraText.getPassword();
                    char[] contraR = contraRepe.getPassword();
                    String contraS1 = String.copyValueOf(contra);
                    String contraR1 = String.copyValueOf(contraR);
                    if (contraS1.equals(contraR1) && !contraS1.isEmpty()) {

                        JOptionPane.showMessageDialog(RootPanel, "El proceso de cifrado ha comenzado.", "Cifrando...", JOptionPane.INFORMATION_MESSAGE);
                        desactivarGUI();

                        int err;
                        PBE cif = new PBE();
                        err = cif.cifrar(contraS1, algoritmo.getSelectedItem().toString(), rutaFicheroC.getText());

                        msgErr(err);
                        activarGUI();
                    } else {
                        msgErr(6);
                    }
                } else {
                    msgErr(7);
                }

            }
        });
        mostrarCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char cMostrar = '•';
                if (mostrarCheckBox.isSelected()) {
                    contraText.setEchoChar((char) 0);
                } else {
                    contraText.setEchoChar(cMostrar);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int resp = fc.showOpenDialog(Gui.this);

                if (resp == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    rutaFicheroC.setText(f.getAbsolutePath());
                }
            }
        });


        buscarDes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int resp = fc.showOpenDialog(Gui.this);

                if (resp == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    rutaDes.setText(f.getAbsolutePath());
                }
            }
        });

        descifrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!rutaDes.getText().equals("")) {
                    char[] pass = contraDes.getPassword();
                    String passwd = String.copyValueOf(pass);

                    if(!passwd.equals("")) {
                        JOptionPane.showMessageDialog(RootPanel, "El proceso de descifrado ha comenzado.", "Descifrando...", JOptionPane.INFORMATION_MESSAGE);
                        desactivarGUI();

                        int err;
                        PBE cif = new PBE();
                        err = cif.descifrar(passwd, rutaDes.getText());

                        msgErr(err);
                        activarGUI();
                    }else{
                        msgErr(8);
                    }
                } else {
                    msgErr(7);
                }
            }
        });
    }

    private void msgErr(int err) {
        switch (err) {
            case 0:
                JOptionPane.showMessageDialog(RootPanel, "El proceso ha finalizado correctamente.", "Proceso finalizado.", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(RootPanel, "El fichero indicado no existe.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(RootPanel, "Clave invalida.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 4:
                JOptionPane.showMessageDialog(RootPanel, "Error al leer la cabecera.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 5:
                JOptionPane.showMessageDialog(RootPanel, "Error al escribir la cabecera.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 6:
                JOptionPane.showMessageDialog(RootPanel, "Las contraseñas son diferentes o estan vacías.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 7:
                JOptionPane.showMessageDialog(RootPanel, "Introduzca la ruta del fichero.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 8:
                JOptionPane.showMessageDialog(RootPanel, "Introduzca una contraseña.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            case 9:
                JOptionPane.showMessageDialog(RootPanel, "Contraseña incorrecta.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(RootPanel, "Ha ocurrido un error desconocido.", "Error.", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void desactivarGUI() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cifrarButton.setEnabled(false);
        tab.setEnabled(false);
        RootPanel.setEnabled(false);
        mostrarCheckBox.setEnabled(false);
        contraText.setEnabled(false);
        contraRepe.setEnabled(false);
        algoritmo.setEnabled(false);
        rutaFicheroC.setEnabled(false);
        buscarButton.setEnabled(false);
        contraDes.setEnabled(false);
        rutaDes.setEnabled(false);
        buscarDes.setEnabled(false);
        descifrarButton.setEnabled(false);
    }

    private void activarGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cifrarButton.setEnabled(true);
        tab.setEnabled(true);
        RootPanel.setEnabled(true);
        mostrarCheckBox.setEnabled(true);
        contraText.setEnabled(true);
        contraRepe.setEnabled(true);
        algoritmo.setEnabled(true);
        rutaFicheroC.setEnabled(true);
        buscarButton.setEnabled(true);
        contraDes.setEnabled(true);
        rutaDes.setEnabled(true);
        buscarDes.setEnabled(true);
        descifrarButton.setEnabled(true);
    }
}
