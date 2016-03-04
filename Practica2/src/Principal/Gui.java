package Principal;

import javax.swing.*;
import java.awt.*;

/**
 * Created by JuanP on 04/03/2016.
 */
public class Gui extends JFrame{
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
    private JComboBox algoritmoD;
    private JTextField rutaDes;
    private JButton buscarDes;
    private JPasswordField contraDes;
    private JPanel TabPanel;

    public Gui(){

        super("Práctica 2");
        setContentPane(RootPanel);
        pack();
        setLocation(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);




        setVisible(true);
    }



}
