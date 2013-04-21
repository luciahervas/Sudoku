package algoritmoGenetico;

public class Operaciones 
{	
	public static int aleatorioEntre(int a, int b){
		return a + (int)(Math.random() * ((b - a) + 1));		
	}
	
	/**
	 * Devuelve un numero aleatorio entre a y b
	 * 
	 * @param a
	 * @param b
	 * @param fijos
	 * @return numero aleatorio ; -1 si no puede encontrar un aleatorio valido
	 */
	public static int aleatorioEntreExcepto(int a, int b, int[] fijos){
		boolean[] mascara = new boolean[9];
		int numeroFijos = 0;
		for (int i=0; i<9; i++){
			if (fijos[i]!=0){
				mascara[fijos[i]-1]=true;
				numeroFijos++;
			}
		}
		// Todos la cuadricula esta fija
		if (numeroFijos == 9) {return -1;}
		
		int random=0;
		boolean randomValido = false;
		while(!randomValido) {
			random = (int)Operaciones.aleatorioEntre(a, b);
			if (!mascara[random - 1]){
				randomValido=true;
				mascara[random - 1]=true;
			}
		}
		return random;
	}
}
