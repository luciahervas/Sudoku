package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SudokuCanvas extends Canvas
{

	private static final long serialVersionUID = 1L;
	//--------------------ATRIBUTOS---------------------//
	public byte[][] tablero;
	private static final int M = 35;
	private static final int K = 28;
	//-------------------CONSTRUCTORAS---------------------//
	
	/*
	public SudokuCanvas (){
		//tablero = new byte[9][9]; 
		tablero=GeneradorSudokus.enunciadoSudoku();
		//generar(tablero);
		
	}
	*/
	public SudokuCanvas (byte[][] tablero){
		this.tablero = tablero;
	}

	public void paint(Graphics g){
		pintarTablero(g);
		dibujarNumeros(g);
	} 

	//-------------------METODOS---------------------//
	public void setTablero(byte[][] tablero)
	{
		this.tablero = tablero;
	}
	
	private void pintarCuadradito(int a, int b, Graphics g)
	{
		int xOrigen = a * M;
		int yOrigen = b * M;
		for (int i = 0; i <= 3; i++){
			if ( (i==0) || (i==3) ) {
				g.fillRect(xOrigen + i*M, yOrigen, 2, 3*M);
				g.fillRect(xOrigen, yOrigen + i*M, 3*M, 2);
			}
			g.drawLine(xOrigen + i*M, yOrigen, xOrigen + i*M, yOrigen + 3*M);
			g.drawLine(xOrigen, yOrigen + i*M, xOrigen + 3 *M, yOrigen + i *M);
		}
		
	}
	
	private void pintarTablero(Graphics g) {		
		pintarCuadradito(2, 2, g);
		pintarCuadradito(2, 5, g);
		pintarCuadradito(2, 8, g);
		pintarCuadradito(5, 2, g);
		pintarCuadradito(5, 5, g);
		pintarCuadradito(5, 8, g);
		pintarCuadradito(8, 2, g);
		pintarCuadradito(8, 5, g);
		pintarCuadradito(8, 8, g);
	}
	
	private void pintarNumeritos(int a, int b, Graphics g, int cuadrado) {
		int xOrigen = a * M + 10;
		int yOrigen = b * M + 26;
		byte n;
		String s;
		for (int i=0; i<3; i++){
			n = tablero[cuadrado][i];
			s = String.valueOf(n);
			if (n!=0)
				g.drawString(s, xOrigen, yOrigen);
			xOrigen += M;
		}
		xOrigen = a * M + 10;
		yOrigen += M;
		for (int i=3; i<6; i++){
			n = tablero[cuadrado][i];
			s = String.valueOf(n);
			if (n!=0)
				g.drawString(s, xOrigen, yOrigen);
			xOrigen += M;
		}
		xOrigen = a * M + 10;
		yOrigen += M;
		for (int i=6; i<9; i++){
			n = tablero[cuadrado][i];
			s = String.valueOf(n);
			if (n!=0)
				g.drawString(s, xOrigen, yOrigen);
			xOrigen += M;
		}		
		
	}
	
	private void dibujarNumeros(Graphics g){
		Font f = new Font ("Courier", Font.BOLD, K);
		g.setFont(f);
		pintarNumeritos(2, 2, g, 0);
		pintarNumeritos(5, 2, g, 1);
		pintarNumeritos(8, 2, g, 2);
		pintarNumeritos(2, 5, g, 3);
		pintarNumeritos(5, 5, g, 4);
		pintarNumeritos(8, 5, g, 5);
		pintarNumeritos(2, 8, g, 6);
		pintarNumeritos(5, 8, g, 7);
		pintarNumeritos(8, 8, g, 8);
	}
		
	private void generar(byte[][] tablero)
	{
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				tablero[i][j] = (byte) 1;
			}
		}
	}
}