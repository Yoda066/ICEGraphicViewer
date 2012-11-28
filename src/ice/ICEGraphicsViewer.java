/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Yoda066
 */
public class ICEGraphicsViewer extends JPanel {

    int velkost = 30;
    int dx, dy=0;
    //BufferedImage theCat = null;
    int scale = 250;
    boolean grid = false;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Integer> widths;

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    public void setWidths(ArrayList<Integer> widths) {
        this.widths = widths;
    }

    //
//    @Override
//    protected void paintComponent(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//
//        if (theCat != null) {
//            int w = theCat.getWidth() / 2;
//            int h = theCat.getHeight() / 2;
//            g2d.drawImage(theCat, 0, 0, this.getWidth(), this.getHeight(), w - scale, h - scale, w + scale, h + scale, null);
//        }
    private void drawGrid(Graphics2D g2d) {
        Dimension size = getSize();
        Insets insets = getInsets();
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        int pocetX = w / this.velkost;
        int pocetY = h / this.velkost;
        g2d.setColor(new Color(255, 255, 255, 100));

        for (int i = 0; i < pocetY; i++) {
            g2d.drawLine(0, h / 2 + i * this.velkost, size.width - insets.right, h / 2 + i * this.velkost);
            g2d.drawLine(0, h / 2 - i * this.velkost, size.width - insets.right, h / 2 - i * this.velkost);
        }

        for (int i = 0; i < pocetX; i++) {
            g2d.drawLine(w / 2 + i * this.velkost, 0, w / 2 + i * this.velkost, size.height - insets.top);
            g2d.drawLine(w / 2 - i * this.velkost, 0, w / 2 - i * this.velkost, size.height - insets.top);
        }
        g2d.setColor(Color.red);
        g2d.drawLine(w / 2, h / 2 - this.velkost, w / 2, h / 2 + this.velkost);
        g2d.drawLine(w / 2 - this.velkost, h / 2, w / 2 + this.velkost, h / 2);
    }

    private void drawEntity(Graphics2D g2d) {
        int h = this.getWidth() / 2;
        int w = this.getHeight() / 2;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < vertices.size() / 2; i++) {
            Vertex v1 = vertices.get(i * 2);
            Vertex v2 = vertices.get(i * 2 + 1);
            GradientPaint redtowhite = new GradientPaint((int) v1.X, (int) v1.Y, v1.color, (int) v2.X, (int) v2.Y, v2.color);
            g2d.setPaint(redtowhite);
            Stroke s = new BasicStroke(widths.get(i));
            g2d.setStroke(s);
            g2d.draw(new Line2D.Double(h + (int) vertices.get(i * 2).X, w + (int) vertices.get(i * 2).Y, h + (int) vertices.get(i * 2 + 1).X, w + (int) vertices.get(i * 2 + 1).Y));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (grid) {
            drawGrid(g2d);
        }
        if (vertices != null) {
            drawEntity(g2d);
        }
    }
}