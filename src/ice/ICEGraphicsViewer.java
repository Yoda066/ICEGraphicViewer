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
    ///Offset of entity from center.
    int dx, dy = 0;
    ///Says if grid should be drawn.
    boolean grid = false;
    ///Vertices of entity
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    ///Widths of lines from vertices 2n and 2n+1 
    private ArrayList<Float> widths;
    //private BufferedImage bi;
    float gridSize = 20;

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices.clear();
        this.vertices.addAll(vertices);
    }

    public void setWidths(ArrayList<Float> widths) {
        this.widths = widths;
    }

    /*
     * Draws entity using List of vertices and List of lines widths
     */
    private void drawEntity(Graphics2D g2d) {
        float w = this.getWidth() / 2;
        float h = this.getHeight() / 2;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(w - dx, h - dy);
        g2d.scale(scale, scale);

        for (int i = 0; i < vertices.size() / 2; i++) {
            Vertex v1 = vertices.get(i * 2);
            Vertex v2 = vertices.get(i * 2 + 1);
            GradientPaint color = new GradientPaint((float) v1.X, (float) v1.Y, v1.color, (float) v2.X, (float) v2.Y, v2.color, true);
            g2d.setPaint(color);
            g2d.setStroke(new BasicStroke(widths.get(i)));
            g2d.draw(new Line2D.Double(v1.X, v1.Y, v2.X, v2.Y));
        }
        
        g2d.setPaint(new Color(255, 255, 255, 150));
        g2d.setStroke(new BasicStroke((float) 0.7));        
        
        //if grid button is selected, grid is drawn
        if (grid) {
            for (int i = 0; i < w / gridSize*2; i++) {
                g2d.draw(new Line2D.Double(i * gridSize, -3*h, i * gridSize, h*3));
                g2d.draw(new Line2D.Double(-i * gridSize, -3*h,-i * gridSize, h*3));
            }
            for (int i = 0; i < h / gridSize*2; i++) {
                g2d.draw(new Line2D.Double(-3*w, i * gridSize, w*3, i * gridSize));
                g2d.draw(new Line2D.Double(-3*w, - i * gridSize, w*3, - i * gridSize));
            }
        }        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (vertices != null) {
            drawEntity(g2d);
        }
    }
}