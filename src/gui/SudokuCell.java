package gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SudokuCell extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	int num; //the number it would display
    int x, y;  //the x,y position on the grid

    SudokuCell(int x, int y) 
    {
        super();

        this.x = x;
        this.y = y;

        /* DEBUG */
        this.num = 3;
        this.add(new JLabel( String.valueOf(num)) );
        /* *** */
        
        /* create a black border */
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        /* set size to 50x50 pixel for one square */
        //setPreferredSize(new Dimension(50,50));
    }

    /* Getters & Setters */
    public int getNum() { return this.num; }
    public void setNum(int num) { this.num = num; }
    public int getX() { return x; }
    public int getY() { return y; }

}
