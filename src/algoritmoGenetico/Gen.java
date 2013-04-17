package algoritmoGenetico;

/**
 * Clase que codifica una cuadricula
 * 
 * --------------
 * | 4 | 5 | 8 |
 * --------------
 * | 3 | 1 | 9 |
 * -------------
 * | 2 | 7 | 6 |
 * -------------
 */
public class Gen
{
	private int[] cuadricula;
	private int[] fijos;
	
	/**
	 * Genera una cuadricula aleatoria donde no
	 * habra numeros repetidos
	 * 
	 * @param numeros fijos: no seran erroneos
	 */
	public Gen(int[] f)
	{
		this.fijos = f;
		int[] tmp = f.clone();
		cuadricula = new int[9];
		int aleatorio; 
		for(int i=0; i<9; i++){
			if (tmp[i]==0){
				aleatorio = (int) Operaciones.aleatorioEntreExcepto(1,9,tmp);
				tmp[i] = aleatorio;
				cuadricula[i] = aleatorio;
			}
			else
				cuadricula[i]=tmp[i];
		}
	}
	
	public Gen(int[] cuadricula,int[] fijos){
		this.cuadricula = cuadricula;
		this.fijos = fijos;
	}
	
	public Gen clone(){
		return new Gen(cuadricula.clone(),fijos.clone());
	}
	
	public int[] getCuadricula() {
		return cuadricula;
	}

	public int[] getFijos() {
		return fijos;
	}
	
	public void setNumero(int pos, int n){
		this.cuadricula[pos] = n;
	}
}
