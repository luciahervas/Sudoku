package algoritmoGenetico;

public class Operaciones {
	public static int aleatorioEntre(int a, int b){
		return a + (int)(Math.random() * ((b - a) + 1));		
	}
}
