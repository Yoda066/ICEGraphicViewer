
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
 *
 * @author Yoda066
 */
public class ICEGraphicsViewer extends JPanel {

    int velkost = 30;
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

    private void drawGrid(Graphics2D g2d) {
        Dimension size = getSize();
        Insets insets = getInsets();
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        int pocetX = w / this.velkost;
        int pocetY = h / this.velkost;
        g2d.setColor(new Color(255, 255, 255, 75));

        for (int i = -45; i < pocetY; i++) {
            g2d.drawLine(0, h / 2 + i * this.velkost - dy, size.width - insets.right, h / 2 + i * this.velkost - dy);
        }

        for (int i = -45; i < pocetX; i++) {
            g2d.drawLine(w / 2 + i * this.velkost - dx, 0, w / 2 + i * this.velkost - dx, size.height - insets.top);
        }
        g2d.setColor(Color.red);
        g2d.drawLine(w / 2 - dx, h / 2 - this.velkost - dy, w / 2 - dx, h / 2 + this.velkost - dy);
        g2d.drawLine(w / 2 - this.velkost - dx, h / 2 - dy, w / 2 + this.velkost - dx, h / 2 - dy);
    }

    private void drawEntity(Graphics2D g2d) {
        int h = this.getWidth() / 2;
        int w = this.getHeight() / 2;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < vertices.size() / 2; i++) {
            Vertex v1 = vertices.get(i * 2);
            Vertex v2 = vertices.get(i * 2 + 1);            
            GradientPaint color = new GradientPaint((float) v1.X, (float) v1.Y, v1.color, (float) v2.X, (float) v2.Y, v2.color);
            g2d.setPaint(color);            
            g2d.setStroke(new BasicStroke(widths.get(i)));
            g2d.draw(new Line2D.Double(h + vertices.get(i * 2).X - dx, w + vertices.get(i * 2).Y - dy, h + vertices.get(i * 2 + 1).X - dx, w + vertices.get(i * 2 + 1).Y - dy));
            System.out.println(v1+" "+v2);
        }
        System.out.println("----------------------------------------------------------------------------");
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