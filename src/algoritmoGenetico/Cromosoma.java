package algoritmoGenetico;

public class Cromosoma
{
	/* Atributos */
	private Gen[] genes;
	private int[][] fenotipo;
	private int aptitud;
	private double puntuacionAcumulada;
	
	private static int funcAptitud;
	
	/* Constructoras */
	public Cromosoma(){
		genes = new Gen[9];
		fenotipo = new int[9][9];
	}
	
	public Cromosoma(int[][] fijos){
		genes = new Gen[9];
		for (int i=0; i<9; i++){
			genes[i]=new Gen(fijos[i]);
		}
		fenotipo = new int[9][9];
		evaluarCromosoma(); //Calcula fenotipo y aptitud
	}

	
	public Cromosoma(Gen[] genes, int[][] fenotipo, int aptitud, double puntuacionAcumulada)
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
	public double getPuntuacionAcumulada() {return puntuacionAcumulada;}
	public void setPuntuacionAcumulada(double puntuacionAcumulada) {this.puntuacionAcumulada = puntuacionAcumulada;}
	public static void setFuncAptitud(int funcAptitud){Cromosoma.funcAptitud = funcAptitud;}
	
	
	/* Metodos implementados */
	
	@Override
	public Cromosoma clone(){
		Gen[] aux = new Gen[9];
		for (int i=0;i<9;i++)
			aux[i]=genes[i].clone();
		return new Cromosoma(aux,fenotipo.clone(),aptitud,puntuacionAcumulada);
	}
	
	/**
	 * Calcula el fenotipo y la aptitud
	 */
	public void evaluarCromosoma() 
	{
		calcularFenotipo();
		calcularAptitud();
	}
	
	public int[][] dameMatriz()
	{
		int [][] matriz=new int[9][9];
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				matriz[i][j] = this.getGenes()[i].getCuadricula()[j];
			}
		}
		return matriz;
	}

	@Override
	public String toString(){return this.fenotipo.toString();}
	
	
	private void calcularFenotipo()
	{
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				this.fenotipo[i][j] = tranformar(this.genes,i,j);
			}
			
		}
	}

	private int tranformar(Gen[] cromosoma, int i, int j) {
		int x = i;
		int y = j;
		i = x/3 + (y/3)*3;
		j = x%3 + (y%3)*3;
		return cromosoma[i].getCuadricula()[j];
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
				this.aptitud = 405*9 - aux;
			break;
			case 2:
				aux=calcularAptitudConFactorial();
				this.aptitud = 3265920*9 - aux;
			break;
		}				
	}
	
	private int calcularAptitudConRepetidos()
	{
		int repetidos=0;
		boolean[] mascara=new boolean[9];
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				int num = fenotipo[i][j]-1;
				if (mascara[num])
					repetidos++;
				else
					mascara[num]=true;
			}
			mascara=new boolean[9];
		}
		for (int j=0; j<9; j++){
			for (int i=0; i<9; i++){
				int num = fenotipo[i][j]-1;
				if (mascara[num])
					repetidos++;
				else
					mascara[num]=true;
			}
			mascara=new boolean[9];
		}
		//if (repetidos<10)
			//return calcularAptitudConRepetidos();
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
		int a = Operaciones.aleatorioEntreExcepto(1, 8, this.genes[j].getFijos());
		int b = Operaciones.aleatorioEntreExcepto(1, 8, this.genes[j].getFijos());
		if ( (a == -1) || (b == -1) ) return;
		
		int aux = this.genes[j].getCuadricula()[a];
		this.genes[j].getCuadricula()[a] = this.genes[j].getCuadricula()[b];
		this.genes[j].getCuadricula()[b] = aux;
	}
	

	
	/**
	 * Mutar un cuadrante en un intervalo
	 * 
	 * @param j e [0,8]
	 */
	public void mutaGenIntervalo(int j) {
		int random1 = Operaciones.aleatorioEntreExcepto(1, 8, this.genes[j].getFijos());
		int random2 = Operaciones.aleatorioEntreExcepto(1, 8, this.genes[j].getFijos());
		// tien que ser (random1 < random2)
		if (random1>random2){
			int aux=random1;
			random1=random2;
			random2=aux;
		}
		int[] aux = new int[random2-random1];
		for (int i=random1; i<random2; i++){
			aux[i-random1]=this.genes[j].getCuadricula()[i];
		}
		int indice = 0;
		for (int i=random2-1; i>=random1; i--){
			this.genes[j].getCuadricula()[i]=aux[indice];
			indice++;
		}
	}
	
	public void setGen(int i, Gen gen) {
		this.genes[i]=gen;
	}

}
