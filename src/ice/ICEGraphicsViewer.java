// Copyright David Horvath 2012.
// Distributed under the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)\
package ice;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * ICEGraphicsViewer draws visual representation of entity
 *
 * @author David Horvath
 */
public class ICEGraphicsViewer extends JPanel {

    float scale = (float) 1;
    int dx, dy = 0;
    boolean grid = false;
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Float> widths;

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    public void setWidths(ArrayList<Float> widths) {
        this.widths = widths;
    }

    //GRID
//    private void drawGrid(Graphics2D g2d) {
//        Dimension size = getSize();
//        Insets insets = getInsets();
//        int w = size.width - insets.left - insets.right;
//        int h = size.height - insets.top - insets.bottom;
//        int pocetX = w / this.scale;
//        int pocetY = h / this.scale;
//        g2d.setColor(new Color(255, 255, 255, 75));
//
//        for (int i = -45; i < pocetY; i++) {
//            g2d.drawLine(0, h / 2 + i * this.scale - dy, size.width - insets.right, h / 2 + i * this.scale - dy);
//        }
//
//        for (int i = -45; i < pocetX; i++) {
//            g2d.drawLine(w / 2 + i * this.scale - dx, 0, w / 2 + i * this.scale - dx, size.height - insets.top);
//        }
//        g2d.setColor(Color.red);
//        g2d.drawLine(w / 2 - dx, h / 2 - this.scale - dy, w / 2 - dx, h / 2 + this.scale - dy);
//        g2d.drawLine(w / 2 - this.scale - dx, h / 2 - dy, w / 2 + this.scale - dx, h / 2 - dy);
//    }
    /*
     * Draws entity using List of vertices and List of lines widths
     */
    private void drawEntity(Graphics2D g2d) {
        int h = this.getWidth() / 2;
        int w = this.getHeight() / 2;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.scale(scale, scale);

        for (int i = vertices.size() / 2 - 1; i >= 0; i--) {
            Vertex v1 = vertices.get(i * 2);
            Vertex v2 = vertices.get(i * 2 + 1);
            GradientPaint color = new GradientPaint((float) v1.X - dx, (float) v1.Y - dy, v1.color, (float) v2.X - dx, (float) v2.Y - dy, v2.color, true);
            g2d.setPaint(color);
            g2d.setStroke(new BasicStroke(widths.get(i)));
            g2d.draw(new Line2D.Double(h + vertices.get(i * 2).X - dx, w + vertices.get(i * 2).Y - dy, h + vertices.get(i * 2 + 1).X - dx, w + vertices.get(i * 2 + 1).Y - dy));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if (grid) {
            // drawGrid(g2d);
        }
        if (vertices != null) {
            drawEntity(g2d);
        }
    }
}