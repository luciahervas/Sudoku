package gui;

public class GeneradorSudokus {
	public static int[][] enunciadoSudoku()
	{
		int[][] tablero = new int[9][9];
		tablero[0][1] = 6;
		tablero[0][5] = 8;
		tablero[0][6] = 2;
		tablero[1][0] = 1;
		tablero[1][2] = 4;
		tablero[1][3] = 3;
		tablero[1][5] = 5;
		tablero[2][1] = 5;
		tablero[2][3] = 6;
		tablero[2][8] = 1;
		tablero[3][0] = 8;
		tablero[3][5] = 6;
		tablero[3][6] = 7;
		tablero[4][0] = 4;
		tablero[4][2] = 7;
		tablero[4][6] = 9;
		tablero[4][8] = 1;
		tablero[5][2] = 6;
		tablero[5][3] = 3;
		tablero[5][8] = 4;
		tablero[6][0] = 5;
		tablero[6][5] = 7;
		tablero[6][7] = 4;
		tablero[7][3] = 2;
		tablero[7][5] = 6;
		tablero[7][6] = 5;
		tablero[7][8] = 8;
		tablero[8][2] = 2;
		tablero[8][3] = 9;
		tablero[8][7] = 7;
		return tablero;
	}
	
}
