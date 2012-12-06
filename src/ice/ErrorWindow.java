/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ice;

import java.awt.Window;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author student
 */
public class ErrorWindow extends JDialog {

    public ErrorWindow(Window owner, String text) {
        super(owner, "ERROR", JDialog.DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
                
        JLabel message = new JLabel(text);
        Border border = BorderFactory.createEmptyBorder(15, 15, 15, 15);        
        message.setBorder(border);
        add(message);
        setLocationRelativeTo(owner);
        pack();
    }
}   
