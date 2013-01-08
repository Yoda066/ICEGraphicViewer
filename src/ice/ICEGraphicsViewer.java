// Copyright David Horvath 2012.
// Distributed under the Boost Software License, Version 1.0.
// (See accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)\
package ice;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    private ArrayList<Float> widths = new ArrayList<Float>();
    //private BufferedImage bi;
    float gridSize = 20;
    ///Point, where dragging starts
    Point currentPoint;
    ///Color of first vertex in drawing
    Color c1 = Color.white;
    Color c2 = Color.white;
    private boolean button1down = false;
    private boolean button3down = false;

    public ICEGraphicsViewer() {
        super();
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //if middle mouse butooon was clicked, scene will centre
                if (e.getButton() == 2) {
                    scale = 1 ;
                    dx = 0;
                    dy = 0;
                    repaint();
                }
                //if eight mouse button was pressed during drawing, drawned line will be erased
                if (e.getButton() == 3) {
                    if (button1down && vertices.size() >= 2) {
                        button1down=false;
                        vertices.remove(vertices.size() - 1);
                        vertices.remove(vertices.size() - 1);
                        widths.remove(widths.size() - 1);
                        button1down = false;
                    }
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //iniciate drawing by adding two new vertices
                if (e.getButton() == 1) {
                    button1down = true;
                    vertices.add(new Vertex((250) / scale, (200) / scale, c1));
                    vertices.add(new Vertex((e.getX() - 250 + dx) / scale, (e.getY() - 200 + dy) / scale, c2));
                    widths.add((float) 1);
                }
                if (e.getButton() == 3) {
                    button3down = true;
                }
                currentPoint = new Point(e.getX() + dx, e.getY() + dy);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getButton() == 1) {
                    button1down = false;
                }
                if (e.getButton() == 3) {
                    button3down = false;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(Color.red);
                //Moves scene     
                if (button3down) {
                    if (-getWidth() / 2 < (currentPoint.x - e.getX()) / scale && (currentPoint.x - e.getX()) / scale < getWidth() / 2) {
                        dx = currentPoint.x - e.getX();
                        repaint();
                    }
                    if (-getHeight() / 2 < currentPoint.y - e.getY() && currentPoint.y - e.getY() < getHeight() / 2) {
                        dy = currentPoint.y - e.getY();
                        repaint();
                    }
                }

                //draw line by changing last two vertices in "vertices"
                if (button1down) {
                    if (vertices.size() >= 2) {
                        vertices.set(vertices.size() - 2, new Vertex((currentPoint.x - 250) / scale, (currentPoint.y - 200) / scale, c1));
                        vertices.set(vertices.size() - 1, new Vertex((e.getX() - 250 + dx) / scale, (e.getY() - 200 + dy) / scale, c2));
                        widths.set(widths.size() - 1, (float) 1);
                    }
                    repaint();
                }
            }
        });
    }

    public void setColors(Color c1, Color c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

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

        g2d.setPaint(new Color(255, 255, 255, 150));
        g2d.setStroke(new BasicStroke((float) 0.7));

        //if grid button is selected, grid is drawn
        if (grid) {
            for (int i = 0; i < w / gridSize * 2; i++) {
                g2d.draw(new Line2D.Double(i * gridSize, -3 * h, i * gridSize, h * 3));
                g2d.draw(new Line2D.Double(-i * gridSize, -3 * h, -i * gridSize, h * 3));
            }
            for (int i = 0; i < h / gridSize * 2; i++) {
                g2d.draw(new Line2D.Double(-3 * w, i * gridSize, w * 3, i * gridSize));
                g2d.draw(new Line2D.Double(-3 * w, -i * gridSize, w * 3, -i * gridSize));
            }
        }

        for (int i = 0; i < vertices.size() / 2; i++) {
            Vertex v1 = vertices.get(i * 2);
            Vertex v2 = vertices.get(i * 2 + 1);
            GradientPaint color = new GradientPaint((float) v1.X, (float) v1.Y, v1.color, (float) v2.X, (float) v2.Y, v2.color, true);
            g2d.setPaint(color);
            g2d.setStroke(new BasicStroke(widths.get(i)));
            g2d.draw(new Line2D.Double(v1.X, v1.Y, v2.X, v2.Y));
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