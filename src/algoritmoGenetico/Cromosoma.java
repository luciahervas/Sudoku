package algoritmoGenetico;

public class Cromosoma
{
	/* Atributos */
	private Gen[] genes;
	private int[][] fenotipo;
	private int aptitud;
	private int puntuacionAcumulada;
	
	private static int funcAptitud;
	
	/* Constructoras */
	public Cromosoma(int[][] fijos){
		genes = new Gen[9];
		for (int i=0; i<9; i++){
			genes[i]=new Gen(fijos[i]);
		}
		fenotipo = new int[9][9];
		evaluarCromosoma(); //Calcula fenotipo y aptitud
	}
	public Cromosoma(Gen[] genes, int[][] fenotipo, int aptitud, int puntuacionAcumulada)
	{
		this.genes = genes;
		this.fenotipo = fenotipo;
		this.aptitud = aptitud;
		this.puntuacionAcumulada = puntuacionAcumulada;
	}
	
	/* Getters & Setters */
	public Gen[] getGenes() {return genes;}
	public void setGenes(Gen[] genes) {this.genes = genes;}
	public int[][] getFenotipo() {return fenotipo;}
	public void setFenotipo(int[][] fenotipo) {this.fenotipo = fenotipo;}
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
				this.fenotipo[i][j] = this.genes[i].getCuadricula()[j];
			}
		}
	}

	private void calcularAptitud()
	{	
		int aux=0;
		switch(funcAptitud) {
			case 0:
				aux=calcularAptitudConRepetidos();
				this.aptitud = 72 - aux; 
			break;
			case 1:
				aux=calcularAptitudConSumatorio();
				this.aptitud = aux;
			break;
			case 2:
				aux=calcularAptitudConFactorial();
				this.aptitud = aux;
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
			penalizacionProd += Math.abs(prod - 362880);
		}
		return penalizacionProd;
	}
	
	private int sumarFila(int[] fila)
	{
		int acc = 0;
		for(int i=0; i<fila.length; i++){
			acc += fila[i];
		}
		return acc;
	}
	
	private int multiplicarFila(int[] fila)
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
		int a = Operaciones.aleatorioEntreExcepto(1, 9, this.genes[j].getFijos());
		int b = Operaciones.aleatorioEntreExcepto(1, 9, this.genes[j].getFijos());
		int aux = this.genes[j].getCuadricula()[a];
		this.genes[j].getCuadricula()[a] = this.genes[j].getCuadricula()[b];
		this.genes[j].getCuadricula()[b] = aux;
	}
	
	public void setGen(int i, Gen gen) {
		this.genes[i]=gen;
	}

}
