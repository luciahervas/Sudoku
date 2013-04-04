package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SudokuGrid extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int[][] tablero;
	
	SudokuGrid(int n, int m) 
    {
        super();
        this.setLayout(new GridLayout(n,m));
        tablero = new int[n][m];
        
        /** construct the grid */
        /*
        for (int i=0; i<w; i++) {
            for (int j=0; j<h; j++) {
                c.weightx = 1.0;
                c.weighty = 1.0;
                c.fill = GridBagConstraints.BOTH;
                c.gridx = i;
                c.gridy = j;
                add(new SudokuCell(i, j), c);
            }
        }
        */
        
        JTextArea cell;
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
            	
            	//this.add(cell);
            	cell = new JTextArea("A");
            	cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            	this.add(cell);
            }
        }
        
        
        /** create a black border */ 
        setBorder(BorderFactory.createLineBorder(Color.black)); 

    }

}