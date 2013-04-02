package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class SudokuGrid extends JPanel
{
	private static final long serialVersionUID = 1L;
	private SudokuCell[][] casillas;
	
	SudokuGrid(int n, int m) 
    {
        super();
        this.setLayout(new GridLayout(n,m));
        casillas = new SudokuCell[n][m];
        
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
        
        
        
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
            	casillas[i][j] = new SudokuCell(i, j);
            	this.add(casillas[i][j]);
            }
        }
        
        
        /** create a black border */ 
        setBorder(BorderFactory.createLineBorder(Color.black)); 

    }

}