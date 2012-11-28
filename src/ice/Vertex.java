/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import java.awt.Color;

/**
 *
 * @author student
 */
 public class Vertex {
        double X;
        double Y;
        Color color;

        public Vertex(double x, double y, Color c) {
            this.X = x;
            this.Y = y;
            this.color = c;
        }

    @Override
    public String toString() {
        return (X+" "+Y+" "+color+color.getAlpha());
    }
        
        
    }