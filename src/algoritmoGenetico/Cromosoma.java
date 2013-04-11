package algoritmoGenetico;

/**
* Clase que describe a un cromosoma
* formador por 9 genes (9 cuadriculas)
*
*/
public class Cromosoma 
{
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
	private class Gen
	{
		private byte[] cuadricula;
		private byte[] fijos;
		
		/**
		 * Genera una cuadricula aleatoria donde no
		 * habra numeros repetidos
		 * 
		 * @param numeros fijos: no seran erroneos
		 */
		public Gen(byte[] fijos)
		{
			this.fijos = fijos;
			cuadricula = new byte[9];
			for(int i=0; i<9; i++){
				if (fijos[i]==0){
					cuadricula[i]=(byte)Operaciones.aleatorioEntreExcepto(1,9,fijos);
				}
				else
					cuadricula[i]=fijos[i];
			}
		}
		
		public Gen(byte[] cuadricula,byte[] fijos){
			this.cuadricula = cuadricula;
			this.fijos = fijos;
		}
		
		public Gen clone(){
			return new Gen(cuadricula.clone(),fijos.clone());
		}
	}
	// ----------------------
	
	
	/* Atributos */
	private Gen[] genes;
	private byte[][] fenotipo;
	private int aptitud;
	private int puntuacionAcumulada;
	
	private static int funcAptitud;
	
	/* Constructoras */
	public Cromosoma(byte[][] fijos){
		genes = new Gen[9];
		for (int i=0; i<9; i++){
			genes[i]=new Gen(fijos[i]);
		}
		fenotipo = new byte[9][9];
		evaluarCromosoma(); //Calcula fenotipo y aptitud
	}
	public Cromosoma(Gen[] genes, byte[][] fenotipo, int aptitud, int puntuacionAcumulada)
	{
		this.genes = genes;
		this.fenotipo = fenotipo;
		this.aptitud = aptitud;
		this.puntuacionAcumulada = puntuacionAcumulada;
	}
	
	/* Getters & Setters */
	public Gen[] getGenes() {return genes;}
	public void setGenes(Gen[] genes) {this.genes = genes;}
	public byte[][] getFenotipo() {return fenotipo;}
	public void setFenotipo(byte[][] fenotipo) {this.fenotipo = fenotipo;}
	public int getAptitud() {return aptitud;}
	public void setAptitud(int aptitud) {this.aptitud = aptitud;}
	public int getPuntuacionAcumulada() {return puntuacionAcumulada;}
	public void setPuntuacionAcumulada(int puntuacionAcumulada) {this.puntuacionAcumulada = puntuacionAcumulada;}
	
	/* Metodos implementados */
	
	/**
	* Metodo que genera una cadena aleatoria de genes.
	* Ejemplo: inicializarGenes(6) => [1,0,0,0,1,0]
	*
	* @param longitud de la cadena a generar
	* @return cadena de genes (valores booleanos)
	*/
	protected boolean[] inicializarGenes(int longitud)
		{
		boolean[] genes = new boolean[longitud];
		
		double numero;
		for (int i = 0; i<longitud; i++) {
			numero = Math.random();
			if (numero < 0.5){
				genes[i] = false;
			} else {
				genes[i] = true;
			}
		}	
		return genes;
	}
	
	public static void setFuncAptitud(int funcAptitud){
		Cromosoma.funcAptitud = funcAptitud;
	}
	
	@Override
	public Cromosoma clone(){
		return new Cromosoma(genes.clone(),fenotipo,aptitud,puntuacionAcumulada);
	}
	
	public void evaluarCromosoma() 
	{
		calcularFenotipo();
		calcularAptitud();
	}
	
	private void calcularFenotipo()
	{
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				this.fenotipo[i][j] = this.genes[i].cuadricula[j];
			}
		}
	}

	private void calcularAptitud()
	{	
		int aux=0;
		switch(funcAptitud) {
			case 1:
				aux=calcularAptitudConRepetidos();
				this.aptitud = 81*2 - aux; 
			break;
			case 2:
				aux=calcularAptitudConSumatorio();
				this.aptitud = 1000 - aux;
			break;
			case 3:
				aux=calcularAptitudConFactorial();
				this.aptitud = 10000000 - aux;
			break;
		}				
		this.aptitud = aux; 
	}
	
	private int calcularAptitudConRepetidos()
	{
		int repetidos=0;
		boolean[] mascara=new boolean[9];
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				if (mascara[fenotipo[i][j]-1])
					repetidos++;
				else
					mascara[fenotipo[i][j]-1]=true;
			}
			mascara=new boolean[9];
		}
		for (int j=0; j<9; j++){
			for (int i=0; i<9; i++){
				if (mascara[fenotipo[i][j]-1])
					repetidos++;
				else
					mascara[fenotipo[i][j]-1]=true;
			}
			mascara=new boolean[9];
		}
		return repetidos;
	}
	
	private int calcularAptitudConSumatorio()
	{
		int penalizacionSuma = 0;
		for (int i=0; i<9; i++){
			int suma = sumarFila(fenotipo[i]);
			penalizacionSuma += Math.abs(suma - 45);
		}
		return penalizacionSuma;
	}
	
	private int calcularAptitudConFactorial()
	{
		int penalizacionProd = 0;
		for (int i=0; i<9; i++){
			int prod = multiplicarFila(fenotipo[i]);
			penalizacionProd += Math.abs(prod - 91);
		}
		return penalizacionProd;
	}
	
	private int sumarFila(byte[] fila)
	{
		int acc = 0;
		for(int i=0; i<fila.length; i++){
			acc += fila[i];
		}
		return acc;
	}
	
	private int multiplicarFila(byte[] fila)
	{
		int acc = 1;
		for(int i=0; i<fila.length; i++){
			acc *= fila[i];
		}
		return acc;
	}
	
	/**
	 * Mutar un cuadrante
	 * 
	 * @param j e [0,8]
	 */
	public void mutaGen(int j) {
		int a = Operaciones.aleatorioEntreExcepto(1, 9, this.genes[j].fijos);
		int b = Operaciones.aleatorioEntreExcepto(1, 9, this.genes[j].fijos);
		byte aux = this.genes[j].cuadricula[a];
		this.genes[j].cuadricula[a] = this.genes[j].cuadricula[b];
		this.genes[j].cuadricula[b] = aux;
	}
	
	public void setGen(int i, Gen gen) {
		this.genes[i]=gen;
	}

}