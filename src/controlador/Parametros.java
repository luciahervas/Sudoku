package controlador;

/**
 * Clase que recoge los valores de entrada del usuario
 * 
 * Valores de entrada disponibles
 *  | tipo   | nombre en la constructora    | valor por defecto 	|
 *  |:------:|:-----------------------------|:---------------------:|
 *  | int  	 | tam							| 100 					|
 *  | int  	 | generaciones 				| 50 					|
 *  | double | cruce 						| 0.6 					|
 *  | double | mutacion 					| 0.05 					|
 *  | double | elitismo 					| 0.0					|
 *  | int    | seleccion					| 1 					|
 *  | byte[] | fijos
 *  
 * Nota: llamar a la contructora vacia para asignar los valores por defecto 
 * 	
 */
public class Parametros 
{
	/* Algoritmos de seleccion disponibles */
	private int[] valoresSeleccion = {0,1};
	
	/* Valores por defecto */
	public static final int TAM_POBLACION_DEFECTO = 100;
	public static final int NUM_GENERACIONES_DEFECTO = 100;
	public static final double PROB_CRUCE_DEFECTO = 0.7;
	public static final double PROB_MUTACION_DEFECTO = 0.1;
	public static final double ELITISMO_DEFECTO = 0.05;
	public static final int FUNC_SELECCION_DEFECTO = 1;
	public static final int FUNC_CRUCE_DEFECTO = 1;
	public static final int FUNC_MUTACION_DEFECTO = 1;
	public static final int FUNC_APTITUD_DEFECTO = 1;
	
	/* Atributos */
	private int tamPoblacion;
	private int numGeneraciones;
	private double probCruce;
	private double probMutacion;
	private double elitismo;
	private int funcSeleccion;
	private int funcCruce;
	private int funcMutacion;
	private int funcAptitud;
	private byte[][] fijos;
	
	/**
	 * 
	 * @param tam
	 * @param generaciones
	 * @param cruce
	 * @param mutacion
	 * @param tolerancia
	 * @param elitismo
	 * @param funcion
	 * @param seleccion: 0 para torneo y 1 para ruleta. Cualquier otro valor se tomara un 1.
	 */
	public Parametros (int tam, int generaciones, double cruce, double mutacion, double elitismo, int funcSeleccion)
	{
		this.tamPoblacion = tam;
		this.numGeneraciones = generaciones;
		this.probCruce = cruce;
		this.probMutacion = mutacion;
		this.elitismo = elitismo;
		if (estaContenido(funcSeleccion)) {
			this.funcSeleccion = funcSeleccion;
		} else {
			this.funcSeleccion = FUNC_SELECCION_DEFECTO;
		}
	}
	
	public Parametros ()
	{
		this.tamPoblacion = TAM_POBLACION_DEFECTO;
		this.numGeneraciones = NUM_GENERACIONES_DEFECTO;
		this.probCruce = PROB_CRUCE_DEFECTO;
		this.probMutacion = PROB_MUTACION_DEFECTO;
		this.elitismo = ELITISMO_DEFECTO;
		this.funcSeleccion = FUNC_SELECCION_DEFECTO;
		this.funcCruce = FUNC_CRUCE_DEFECTO;
		this.funcMutacion = FUNC_MUTACION_DEFECTO;
		this.funcAptitud = FUNC_APTITUD_DEFECTO;
	}
	
	/* Getters & Setters */
	public int getTamPoblacion() { return tamPoblacion; }
	public void setTamPoblacion(int tamPoblacion) { this.tamPoblacion = tamPoblacion; }
	public int getNumGeneraciones() { return numGeneraciones; }
	public void setNumGeneraciones(int numGeneraciones) { this.numGeneraciones = numGeneraciones; }
	public double getProbCruce() { return probCruce; }
	public void setProbCruce(double probCruce) { this.probCruce = probCruce; }
	public double getProbMutacion() { return probMutacion; }
	public void setProbMutacion(double probMutacion) { this.probMutacion = probMutacion; }
	public double getElitismo() { return this.elitismo; }
	public void setElitismo(double elitismo) { this.elitismo = elitismo; }
	public byte[][] getFijos() { return this.fijos; }
	public void setFijos(byte[][] fijos) { this.fijos = fijos; }
	public int getFuncSeleccion() {
		return funcSeleccion;
	}

	public void setFuncSeleccion(int funcSeleccion) {
		this.funcSeleccion = funcSeleccion;
	}

	public int getFuncCruce() {
		return funcCruce;
	}

	public void setFuncCruce(int funcCruce) {
		this.funcCruce = funcCruce;
	}

	public int getFuncMutacion() {
		return funcMutacion;
	}

	public void setFuncMutacion(int funcMutacion) {
		this.funcMutacion = funcMutacion;
	}

	public int getFuncAptitud() {
		return funcAptitud;
	}

	public void setFuncAptitud(int funcAptitud) {
		this.funcAptitud = funcAptitud;
	}
	
	/* Funciones auxiliares */
	private boolean estaContenido(int v)
	{
		for (int i = 0; i < this.valoresSeleccion.length; i++) {
			if (v == valoresSeleccion[i]){
				return true;
			}
		}
		return false;
	}
	
}
