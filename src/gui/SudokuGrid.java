package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class SudokuGrid extends JPanel
{
	private static final long serialVersionUID = 1L;

	SudokuGrid(int w, int h) 
    {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
      
        /** construct the grid */
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                c.weightx = 1.0;
                c.weighty = 1.0;
                c.fill = GridBagConstraints.BOTH;
                c.gridx = i;
                c.gridy = j;
                add(new SudokuPanel(i, j), c);
            }
        }

        /** create a black border */ 
        setBorder(BorderFactory.createLineBorder(Color.black)); 

    }

}