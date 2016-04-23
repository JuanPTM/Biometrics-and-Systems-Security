import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by juanp on 16/04/16.
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
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JPanel botonesPanel;
    private JPanel infoPanel;

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

                    imagenFuentePanel.add(picLabel);
                    imagenFuentePanel.revalidate();
                    imagenFuentePanel.repaint();

                }
            }
        });

        gris.addActionListener(new ActionListener() {
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
        });

        destinoAFuenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Filtros.getInstance().isLoad()) {
                    JLabel picLabel = new JLabel(new ImageIcon(Filtros.getInstance().getPictureFinal()));
                    imagenFuentePanel.removeAll();
                    Filtros.getInstance().setMypicture(Filtros.getInstance().getPictureFinal());
                    imagenFuentePanel.add(picLabel);
                    imagenFuentePanel.revalidate();
                    imagenFuentePanel.repaint();
                }

            }
        });
    }

}
