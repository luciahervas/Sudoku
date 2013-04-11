package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SudokuCanvas extends Canvas implements MouseListener, KeyListener
{

	private static final long serialVersionUID = 1L;
	//--------------------ATRIBUTOS---------------------//
	public byte[][] tablero;
	public boolean[][] fijos;
	int[] pulsado;
	private static final int M = 35;
	private static final int K = 28;
	
	//-------------------CONSTRUCTORAS---------------------//
	
	public SudokuCanvas (){
		this.tablero=new byte[9][9];
		fijos = new boolean[9][9];
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				fijos[i][j]=true;
		this.addMouseListener(this);
		this.addKeyListener(this);
	}

	public void paint(Graphics g){
		pintarTablero(g);
		dibujarNumeros(g);
	} 

	//-------------------METODOS---------------------//
	public byte[][] getTablero(){return this.tablero;}
	public void setTablero(byte[][] tablero)
	{
		this.tablero = tablero;
	}
	
	private void pintarCuadradito(int a, int b, Graphics g)
	{
		int xOrigen = a * M;
		int yOrigen = b * M;
		for (int i = 0; i <= 3; i++){
			g.setColor(Color.BLACK);
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
	
	private int[] devuelveCoordenadas(int a, int b){
		int[] coordenadas = new int[2];
		coordenadas[0] = a * M + 70;
		coordenadas[1] = b * M + 70;
		return coordenadas;
	}
	
	private void pintarNumeritos(int a, int b, Graphics g, int cuadrado) {
		int xOrigen = a * M + 10;
		int yOrigen = b * M + 26;
		byte n;
		String s;
		for (int i=0; i<3; i++){
			g.setColor(Color.WHITE);
			g.fillRect(xOrigen+6-10, yOrigen+6-26, M - 12, M - 12);
			if (fijos[cuadrado][i]){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GRAY);
			}
			n = tablero[cuadrado][i];
			s = String.valueOf(n);
			if (n!=0)
				g.drawString(s, xOrigen, yOrigen);
			xOrigen += M;
		}
		xOrigen = a * M + 10;
		yOrigen += M;
		for (int i=3; i<6; i++){
			g.setColor(Color.WHITE);
			g.fillRect(xOrigen+6-10, yOrigen+6-26, M - 12, M - 12);
			if (fijos[cuadrado][i]){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GRAY);
			}
			n = tablero[cuadrado][i];
			s = String.valueOf(n);
			if (n!=0)
				g.drawString(s, xOrigen, yOrigen);
			xOrigen += M;
		}
		xOrigen = a * M + 10;
		yOrigen += M;
		for (int i=6; i<9; i++){
			g.setColor(Color.WHITE);
			g.fillRect(xOrigen+6-10, yOrigen+6-26, M - 12, M - 12);
			if (fijos[cuadrado][i]){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.GRAY);
			}
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	private int[] encuentraCuadradito(int x, int y) {
		int[] coord = new int[2];
		int[] cuadradito = new int[2];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				coord = devuelveCoordenadas(i,j);
				if (x>=coord[0] && y>=coord[1] && x<coord[0]+M && y<coord[1]+M){
					cuadradito[0]=i;
					cuadradito[1]=j;
					transformar(cuadradito);
					return cuadradito;
				}
			}
		}
		return coord;
	}
	
	public void cambiarTablero(byte[][] tableroNuevo, boolean[][] bofijos){
		fijos=bofijos;
		tablero=tableroNuevo;
		this.repaint();
	}

	private void transformar(int[] cuadrado)
	{
		int x = cuadrado[0];
		int y = cuadrado[1];
		cuadrado[0] = x/3 + (y/3)*3;
		cuadrado[1] = x%3 + (y%3)*3;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x<70 || y<70 || x>385 || y>385) {}
		else
		{
			pulsado = new int[2];
			pulsado=encuentraCuadradito(x,y);
			//System.out.println(cuadradito[0]+" "+cuadradito[1]);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (pulsado!=null){
			String n = KeyEvent.getKeyText( e.getKeyCode() );
			if (isNumeric(n)) {
				tablero[pulsado[0]][pulsado[1]]=Byte.valueOf(n);
				pulsado=null;
				this.repaint();
			}
		}
	}
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}