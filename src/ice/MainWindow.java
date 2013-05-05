// Copyright David Horvath 2012.
// Distributed under the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)
package ice;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author David Horvath
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public MainWindow() {
        initComponents();
        setTitle("ICE Graphic Viewer");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(ff);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1.setText("File");
        jMenuItem1.setText("Open");
        jSlider1.setEnabled(true);
        setFocusable(true);

        jPanel2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Color c;
                if (e.getX() <= jPanel2.getWidth() / 3) {
                    c = JColorChooser.showDialog(rootPane, null, c1);
                    if (c != null) {
                        c1 = c;
                    }
                } else if (e.getX() > 2 * jPanel2.getWidth() / 3) {
                    c = JColorChooser.showDialog(rootPane, null, c2);
                    if (c != null) {
                        c2 = c;
                    }
                } else {
                    c = JColorChooser.showDialog(rootPane, null, c2);
                    if (c != null) {
                        c2 = c;
                        c1 = c;
                    }
                }
                jPanel3.setColors(c1, c2);
                jPanel2.repaint();
            }
        });

        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawGrid(evt);
            }
        });

        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {

            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }

            private void jSlider1StateChanged(ChangeEvent evt) {
                jPanel3.scale = (float) (jSlider1.getValue()) / 10;
                jPanel3.repaint();
            }
        });

        addMouseWheelListener(new MouseInputAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                jSlider1.setValue(jSlider1.getValue() - (int) e.getPreciseWheelRotation());
            }
        });
    }

//      start of GUI generated code
    private void initComponents() {
        c1 = Color.white;
        c2 = Color.white;
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel3 = new ICEGraphicsViewer();
        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jSlider1.setMaximum(40);
        jSlider1.setMinimum(10);
        jSlider1.setValue(10);
        jFileChooser1.setDialogTitle("Open YAML file");
        jFileChooser1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jFileChooser1.setPreferredSize(new java.awt.Dimension(800, 600));
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel3.setBackground(Color.black);
        parser = new YamlParser();


        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, getHeight() / 2, c1, getWidth(), getHeight() / 2, c2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ICE graphics viewer");
        setLocationByPlatform(true);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(571, 480));
        setPreferredSize(new java.awt.Dimension(630, 500));
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(500, 400));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 500, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));

        jToggleButton1.setText("Grid");

        jLabel1.setText("Zoom");

        jSlider1.setMaximum(40);
        jSlider1.setMinimum(10);
        jSlider1.setValue(10);

        jPanel2.setBackground(new java.awt.Color(108, 208, 122));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 91, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 8, Short.MAX_VALUE));
        jLabel2.setText("0.0");
        jLabel3.setText("0.0");
        jLabel4.setText("0.0");
        jLabel5.setText("0.0");
        jMenu1.setText("File");
        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFile();
            }
        });

        jMenu1.add(jMenuItem1);
        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("New");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFile();
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jSeparator1)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jToggleButton1).addGap(44, 49, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(layout.createSequentialGroup().addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)))))).addGroup(layout.createSequentialGroup().addGap(80, 80, 80).addComponent(jLabel1)).addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(11, 11, 11).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createSequentialGroup().addComponent(jToggleButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGap(9, 9, 9).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jLabel1)).addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))));

        pack();
//      end of GUI generated code
    }

    private void drawGrid(java.awt.event.ActionEvent evt) {
        if (jToggleButton1.isSelected()) {
            jPanel3.grid = true;
        } else {
            jPanel3.grid = false;
        }
        jPanel3.repaint();
    }

    private void newFile() {
        jPanel3.setVertices(new ArrayList<Vertex>());
        jPanel3.setWidths(new ArrayList<Float>());
        jPanel3.dx = 0;
        jPanel3.dy = 0;
        jPanel3.repaint();
    }

    private void openFile() {
        int status = fileChooser.showOpenDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getPath().endsWith("yaml")) {
                ErrorWindow w = new ErrorWindow(MainWindow.this, "Please choose YAML file!");
                w.setVisible(true);
                w.dispose();
            } else {
                parser.loadEntity(selectedFile);
                if (parser.getEntity() != null) {
                    newFile();
                    jPanel3.setVertices(parser.getEntity());
                    jPanel3.setWidths(parser.getWidths());
                    parser.saveEntity("");
                } else {
                    ErrorWindow w = new ErrorWindow(MainWindow.this, parser.getError());
                    w.setVisible(true);
                    w.dispose();
                }
            }
        }
    }
    JFileChooser fileChooser;
    ///ff accepts only yaml files.
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
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration
    private Color c1;
    private Color c2;
    private JColorChooser cc;
    private YamlParser parser;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private ICEGraphicsViewer jPanel3;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration
}
