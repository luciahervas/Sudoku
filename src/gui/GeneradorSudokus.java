package gui;

public class GeneradorSudokus {
	public static byte[][] enunciadoSudoku()
	{
		byte[][] tablero = new byte[9][9];
		tablero[0][1] = (byte) 6;
		tablero[0][5] = (byte) 8;
		tablero[0][6] = (byte) 2;
		tablero[1][0] = (byte) 1;
		tablero[1][2] = (byte) 4;
		tablero[1][3] = (byte) 3;
		tablero[1][5] = (byte) 5;
		tablero[2][1] = (byte) 5;
		tablero[2][3] = (byte) 6;
		tablero[2][8] = (byte) 1;
		tablero[3][0] = (byte) 8;
		tablero[3][5] = (byte) 6;
		tablero[3][6] = (byte) 7;
		tablero[4][0] = (byte) 4;
		tablero[4][2] = (byte) 7;
		tablero[4][6] = (byte) 9;
		tablero[4][8] = (byte) 1;
		tablero[5][2] = (byte) 6;
		tablero[5][3] = (byte) 3;
		tablero[5][8] = (byte) 4;
		tablero[6][0] = (byte) 5;
		tablero[6][5] = (byte) 7;
		tablero[6][7] = (byte) 4;
		tablero[7][3] = (byte) 2;
		tablero[7][5] = (byte) 6;
		tablero[7][6] = (byte) 5;
		tablero[7][8] = (byte) 8;
		tablero[8][2] = (byte) 2;
		tablero[8][3] = (byte) 9;
		tablero[8][7] = (byte) 7;
		return tablero;
	}
	
}
