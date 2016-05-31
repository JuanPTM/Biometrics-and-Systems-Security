import Filtros.Filtros;
import Filtros.Thinner;
import Matching.MatchingAlg;
import Tratamiento.ExtractorMinucias;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by juanp on 16/04/16.
 * <p>
 * Interfaz gráfica del proyecto.
 *
 * @author Juan Pedro Torres Muñoz.
 */
public class Gui extends JFrame {
    private JPanel RootPanel;
    private JButton cargarImagenButton;
    private JPanel imagenFinPanel;
    private JPanel imagenFuentePanel;
    private JPanel BotPanel;
    private JPanel izqPanel;
    private JPanel derPanel;
    private JPanel topPanel;
    private JButton igualButton;
    private JButton destinoAFuenteButton;
    private JButton gris;
    private JButton binarizacionButton;
    private JButton ecualizarButton;
    private JButton filtroBinarioButton;
    private JPanel botonesPanel;
    private JPanel infoPanel;
    private JButton filtroBinario2Button;
    private JButton adelgazamientoButton;
    private JButton obtenerMinuciasButton;
    private JButton pruebaMatching1Button;
    private JButton guardarMinuciasButton;

    /***
     * Método que crea e inicializa la interfaz grafica.
     */
    public Gui() {
        super("Práctica Biometría");
        init();
        addListener();

        setVisible(true);
    }

    private void init() {
        setContentPane(RootPanel);
        pack();
        Dimension d = getSize();
        setMinimumSize(d);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void addListener() {
        cargarImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int resp = fc.showOpenDialog(Gui.this);

                if (resp == JFileChooser.APPROVE_OPTION) {
                    imagenFuentePanel.removeAll();
                    File f = fc.getSelectedFile();
                    Filtros.getInstance().loadImage(f.getAbsolutePath());
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getMypicture()));

                    JLabel picLabel2 = new JLabel(new ImageIcon(Filtros.getInstance().getGrisImg()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel2);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();

                    imagenFuentePanel.add(picLabel);
                    imagenFuentePanel.revalidate();
                    imagenFuentePanel.repaint();

                }
            }
        });

/*        gris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getGrisImg()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }

            }
        });

        igualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getMypicture()));
                    imagenFinPanel.removeAll();
                    Filtros.getInstance().setPictureFinal(Filtros.getInstance().getMypicture());
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });*/

        destinoAFuenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    Filtros.getInstance().dstToSrc();
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getPictureFinal()));
                    imagenFuentePanel.removeAll();
                    Filtros.getInstance().setMypicture(Filtros.getInstance().getPictureFinal());

                    imagenFuentePanel.add(picLabel);
                    imagenFuentePanel.revalidate();
                    imagenFuentePanel.repaint();
                }

            }
        });

        binarizacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    String s = JOptionPane.showInputDialog(rootPane, "Introduzca el valor del umbral", "Introduzca un valor");

                    try {
                        int umbral = Integer.parseInt(s);
                        if (umbral >= 0 && umbral <= 255) {
                            JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().thresholdFilter(umbral)));

                            imagenFinPanel.removeAll();
                            imagenFinPanel.add(picLabel);
                            imagenFinPanel.revalidate();
                            imagenFinPanel.repaint();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "El valor debe estar entre 0 y 255", "Error al intentar umbralizar", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(rootPane, "El valor debe estar entre 0 y 255", "Error al intentar umbralizar", JOptionPane.ERROR_MESSAGE);
                    } catch (HeadlessException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        ecualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getEcualizar()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });

        filtroBinarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().binaryFilter()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });

        filtroBinario2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().binaryFilterRuido()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });

        adelgazamientoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    Thinner n = new Thinner(Filtros.getInstance().getMypicture());
                    Filtros.getInstance().setPictureFinal(n.getResult());
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getPictureFinal()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });

        obtenerMinuciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    try {
                        String s = JOptionPane.showInputDialog(rootPane, "Introduzca el tamaño de la ventana lateral en pixeles", "Introduzca un valor");
                        int umbralLateral = Integer.parseInt(s);
                        s = JOptionPane.showInputDialog(rootPane, "Introduzca el tamaño de la ventana superior en pixeles", "Introduzca un valor");
                        int umbralSuperior = Integer.parseInt(s);
                        s = JOptionPane.showInputDialog(rootPane, "Introduzca el tamaño de la ventana inferior en pixeles", "Introduzca un valor");
                        int umbralInferior = Integer.parseInt(s);
                        if (umbralInferior > 10 && umbralLateral > 10 && umbralSuperior > 10) {
                            ExtractorMinucias n = ExtractorMinucias.getInstance(Filtros.getInstance().getMypicture());
                            Filtros.getInstance().setPictureFinal(n.getMinucias(umbralLateral, umbralSuperior, umbralInferior));
                            JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getPictureFinal()));

                            imagenFinPanel.removeAll();
                            imagenFinPanel.add(picLabel);
                            imagenFinPanel.revalidate();
                            imagenFinPanel.repaint();
                        }else
                            JOptionPane.showMessageDialog(rootPane, "Todos los valores de los margenes deben ser mayores de 10", "Error al intentar obtener las minucias.", JOptionPane.ERROR_MESSAGE);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(rootPane, "El valor debe ser un valor entre 0 y el máximo tamaño de la imagen.", "Error al intentar obtener las minucias.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        pruebaMatching1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    ExtractorMinucias n = ExtractorMinucias.getInstance();
                    MatchingAlg m = MatchingAlg.getInstance();
                    m.setMinucias(n.getListaMinB());
                    m.compute();

                    if (m.compararHuellas()) {
                        JOptionPane.showMessageDialog(rootPane, "Las huella corresponde a la almacenada("+m.probAcierto()*100+"%).", "Proceso completado", JOptionPane.INFORMATION_MESSAGE);
                    } else
                        JOptionPane.showMessageDialog(rootPane, "La huella no corresponde con la almacenada("+m.probAcierto()*100+"%).", "Proceso completado", JOptionPane.ERROR_MESSAGE);
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getPictureFinal()));

                    imagenFinPanel.removeAll();
                    imagenFinPanel.add(picLabel);
                    imagenFinPanel.revalidate();
                    imagenFinPanel.repaint();
                }
            }
        });

        guardarMinuciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad() && Filtros.getInstance().isGrey()) {
                    ExtractorMinucias n = ExtractorMinucias.getInstance();
                    MatchingAlg m = MatchingAlg.getInstance();
                    m.setMinucias(n.getListaMinB());
                    m.compute();
                    m.guardarTriangulos();
                    JOptionPane.showMessageDialog(rootPane, "Las huella ha sido almacenada.", "Proceso completado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

}
