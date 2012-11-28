/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Currency;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author student
 */
public class skuska extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public skuska() {
        initComponents();
        setTitle("ICE Graphic Viewer");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(ff);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1.setText("File");
        jMenuItem1.setText("Open");

        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });

        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }

            private void jSlider1StateChanged(ChangeEvent evt) {
                jPanel3.velkost = jSlider1.getValue();
                jPanel3.repaint();

            }
        });


        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Clicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                    currentPoint = new Point( e.getX()+jPanel3.dx, e.getY()+jPanel3.dy);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);               
                    jPanel3.dx = currentPoint.x - e.getX();
                    jPanel3.dy = currentPoint.y - e.getY();
                    jPanel3.repaint();
            }
        });

        addMouseWheelListener(new MouseInputAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                jSlider1.setValue(jSlider1.getValue() - (int) e.getPreciseWheelRotation());
                if (jSlider1.getValue() != jSlider1.getMinimum() && jSlider1.getValue() != jSlider1.getMaximum()) {
                    jPanel3.scale -= 10 * (int) e.getPreciseWheelRotation();
                }
            }
        });
    }

    public void Clicked(MouseEvent e) {
        //jPanel3.theCat = createImage(jPanel4);
        jPanel3.repaint();
    }

    public BufferedImage createImage(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }

    private void initComponents() {
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel3 = new ICEGraphicsViewer();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jSlider1.setMaximum(50);
        jSlider1.setMinimum(10);
        jSlider1.setValue((jSlider1.getMaximum() + jSlider1.getMinimum()) / 2);
        jFileChooser1.setDialogTitle("Open YAML file");
        jFileChooser1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jFileChooser1.setPreferredSize(new java.awt.Dimension(800, 600));
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel3.setBackground(Color.black);
        jPanel4 = new JPanel();
        jPanel4.setSize(800, 600);
        jPanel4.setBackground(Color.red);
        parser = new YamlParser();
        currentPoint = new Point();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel3.setPreferredSize(new java.awt.Dimension(500, 400));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        add(jToggleButton1);

        jToggleButton1.setText("Grid");
        jLabel1.setText("Zoom");
        jMenu1.setText("File");
        jMenuItem1.setText("Open");
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenuItem2.setText("Save");
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItem2);
        jMenuBar1.add(jMenu1);
        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);
        setJMenuBar(jMenuBar1);

//      start of GUI generated code
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jToggleButton1)).addGroup(layout.createSequentialGroup().addGap(8, 8, 8).addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(layout.createSequentialGroup().addGap(130, 130, 130).addComponent(jLabel1))).addContainerGap(71, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jToggleButton1).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1)))));

        pack();
//      end of GUI generated code
    }

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jToggleButton1.isSelected()) {
            jPanel3.grid = true;
        } else {
            jPanel3.grid = false;
        }
        jPanel3.repaint();
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getPath().endsWith("yaml")) {
                System.err.println("Vyberte subor s priponou YAML!");
            } else {
                parser.setFile(selectedFile);
                try {
                    parser.loadShip();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(skuska.class.getName()).log(Level.SEVERE, null, ex);
                }
                jPanel3.setVertices(parser.getEntity());
                jPanel3.setWidths(parser.getWidths());
                jPanel3.repaint();
            }
        } else if (status == JFileChooser.CANCEL_OPTION) {
        }
    }

    private void jMenuItem2ActionPerformed(ActionEvent evt) {
        System.out.println("saving");
    }
    JFileChooser fileChooser;
    FileFilter ff = new FileFilter() {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = f.getName();
            if (extension.endsWith(".yaml")) {
                return true;
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "YAML files";
        }
    };

    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new skuska().setVisible(true);
            }
        });
    }
    // Variables declaration
    private YamlParser parser;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private ICEGraphicsViewer jPanel3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToggleButton jToggleButton1;
    private Point currentPoint;
    // End of variables declaration
}
