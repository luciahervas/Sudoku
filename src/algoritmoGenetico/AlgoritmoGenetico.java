package algoritmoGenetico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Iterator;

import controlador.Parametros;

/**
 * Clase que implementa el algoritmo genetico principal
 * 
 */
public class AlgoritmoGenetico 
{
	private static double[] numerosAleatorios = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
	private static int numeroActual;

	private static double siguienteAleatorio()
	{
		double n = numerosAleatorios[numeroActual];
		n++;
		if(n==8)
			n = 0;
		return n;
	}
	
	// Informacion para las graficas
	private Cromosoma[] gokus;
	private Cromosoma[] mejoresCromosomas;
	private double[] medias;
	
	
	
	/**
	 * Implementacion del algoritmo genetico.
	 * Busca: 
	 *  - el mejor cromosoma obtenido tras todas las genraciones (Goku)
	 *  - la lista de los mejores cromosomas en cada generacion (mejoresCromosomas)
	 *  
	 * @param parametros del problema
	 */
	public void algoritmo_genetico(Parametros parametros) {
		Cromosoma.setFuncAptitud(parametros.getFuncAptitud());
		double porcElite = parametros.getElitismo();
		// Obtenemos la poblacion inicial 
		Cromosoma[] pob = poblacion_inicial(parametros); 
		int pos_mejor = evaluarPoblacion(pob);	

		// Informacion para las graficas
		mejoresCromosomas = new Cromosoma[parametros.getNumGeneraciones()];
		gokus = new Cromosoma[parametros.getNumGeneraciones()];
		medias = new double[parametros.getNumGeneraciones()];
		Cromosoma mejor = null;
		
		Cromosoma[] elite = new Cromosoma[(int) (parametros.getTamPoblacion()*porcElite)];
		
		// bucle de evolucion
		for (int i = 0; i < parametros.getNumGeneraciones(); i++) {
			// 0) cogemos a la elite
			elite = separaMejores(pob,porcElite);
			
			// 1) seleccion
			switch(parametros.getFuncSeleccion()){
				case 0: 
					pob = seleccionTorneo(pob, parametros); 
					break;
				case 1:
					pob = seleccionRuleta(pob, parametros);
					break;
				case 2: 
					pob = seleccionUniversalEstocastico(pob, parametros); 
					break;
				case 3:
					pob = seleccionRanking(pob, parametros);
					break;
				default:
			}	
			
			// 2) reproduccion
			switch(parametros.getFuncCruce()) {
				case 0:
					pob = reproduccion(pob, parametros); 
					break;
				case 1:
					pob = reproduccionDosPuntos(pob, parametros);
					break;
				case 2:
					pob = reproduccionPMX(pob, parametros);
					break;
				case 3:
					pob = reproduccionOX(pob, parametros);
					break;
				default:
			}	
			
			// 3) mutacion
			switch(parametros.getFuncMutacion()) {
				case 0:
					pob = mutacion(pob, parametros); 
					break;
				case 1:
					pob = mutacionIntervalo(pob, parametros);
					break;
				default:
			}
			
			// 4) volvemos a integrar a la elite			
			incluye(elite,pob);
			
			// 5) tratar la nueva solucion
			pos_mejor = evaluarPoblacion(pob);
			
			if (mejor == null) {	
				mejor = pob[pos_mejor].clone();
			} else {
				if (pob[pos_mejor].getAptitud() > mejor.getAptitud()){
					mejor=pob[pos_mejor].clone();
				}
			}
			
			// 6) guardar los resultados
			mejoresCromosomas[i]=pob[pos_mejor].clone();
			gokus[i]=mejor.clone();
			medias[i]=calcularMedia(pob);
		}		
	}

	/**
	 * Inserta una elite en una poblacion sustituyendo los primeros cromosomas 
	 * por los mejores extraidos anteriormente.
	 * @param elite
	 * @param poblacion 
	 */
	private void incluye(Cromosoma[] elite, Cromosoma[] pob) {
		for (int i=0; i<elite.length; i++){
			pob[i]=elite[i];
		}
	}

	/**
	 * Dada una poblacion devuelve a los mejores
	 * @param poblacion inicial
	 * @param porcentaje de elite a seleccionar
	 * @return mejores cromosomas
	 */
	private Cromosoma[] separaMejores(Cromosoma[] pob, double porcElite) {
		int tamElite = (int) (pob.length*porcElite);
		Cromosoma[] elite = new Cromosoma[tamElite];
		Cromosoma[] pobAux = pob.clone();
		int posMejor;
		for (int i=0; i<tamElite; i++){
			posMejor=dameMejor(pobAux);
			elite[i]=pobAux[posMejor].clone();
			pobAux[posMejor]=null;
		}
		return elite;
	}
	/**
	 * Dada una poblacion calcula la media de sus aptitudes
	 * @param poblacion
	 * @return media
	 */
	private double calcularMedia(Cromosoma[] pob) {
		double media=0;
		for (int i=0; i<pob.length;i++){
			media+=pob[i].getAptitud();
		}
		return media/pob.length;
	}

	/** 
	 * Metodo para obtener una poblacion inicial aleatoria
	 * 
	 * @param parametros del problema
	 * @return poblacion generada
	 */
	private Cromosoma[] poblacion_inicial(Parametros param) 
	{
		int tam = param.getTamPoblacion();
		Cromosoma[] pob = new Cromosoma[tam];
		
		for (int i = 0; i < tam; i++) {
			pob[i] = new Cromosoma(param.getFijos());
		}		
		return pob;
	}
	
	/**
	 * La funcion de seleccion escoge un numero de supervivientes
	 * igual al tam de la poblacion.
	 * 
	 * Metodo de seleccion => Seleccion por ruleta.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return Poblacion seleccionada
	 */
	private Cromosoma[] seleccionRuleta(Cromosoma[] pob, Parametros parametros) 
	{
		Cromosoma[] poblacionSeleccionada = new Cromosoma[parametros.getTamPoblacion()];
		int posicionSuperviviente;
		
		double random;
		for (int i = 0; i < parametros.getTamPoblacion(); i++) {
			if (parametros.modoDebug){
				random = siguienteAleatorio();
			} else {
				random = Math.random();
			}
			posicionSuperviviente = 0;
			while ((posicionSuperviviente < parametros.getTamPoblacion()) &&
					(random > pob[posicionSuperviviente].getPuntuacionAcumulada()) ) {
				posicionSuperviviente++;
			}			
			poblacionSeleccionada[i] = (Cromosoma) pob[posicionSuperviviente].clone();
		}
		
		return poblacionSeleccionada;
	}
	
	/**
	 * La funcion de seleccion escoge un numero de supervivientes
	 * igual al tam de la poblacion.
	 * 
	 * Metodo de seleccion => Seleccion por torneo.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return Poblacion seleccionada
	 */
	private Cromosoma[] seleccionTorneo(Cromosoma[] pob, Parametros parametros)
	{
		Cromosoma[] poblacionSeleccionada = new Cromosoma[parametros.getTamPoblacion()];
		int tamTorneo=3;
		Cromosoma mejor=null;
		int random;
		for(int i=0; i<parametros.getTamPoblacion();i++) {
			for(int j=0; j<tamTorneo; j++){
				if(parametros.modoDebug){
					random = (int) (siguienteAleatorio() * 10);
				} else {
					random=Operaciones.aleatorioEntre(0,parametros.getTamPoblacion()-1);
				}
				if (mejor==null || (pob[random].getAptitud() > mejor.getAptitud()))
					mejor = pob[random];
			}
			poblacionSeleccionada[i]=mejor.clone();
		}
		return poblacionSeleccionada;
	}
	
	/**
	 * Seleccionar TAM_POBLACION individuos
	 * 
	 * Metodo de seleccion => Ranking lineal
	 * 
	 * Ejemplo
	 * 
	 * | A | 21     | C | 1
	 * | B | 32 ==> | A | 2
	 * | C | 3      | B | 3
	 * 
	 * puntuacion = (2-SP) + 2(SP -1) * [(pos - 1)/TAM_POBLACION -1]
	 * ruleta.
	 * 
	 * fuente : http://www.herrera.unt.edu.ar/gapia/Curso_AG/Curso_AG_08_Clase_4.pdf
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return Poblacion seleccionada
	 */
	private Cromosoma[] seleccionRanking(Cromosoma[] pob, Parametros parametros)
	{
		// 1) ordenar los individuos segun su aptitud
		Comparator<Cromosoma> c = new Comparator<Cromosoma>()
		{
			@Override
			public int compare(Cromosoma o1, Cromosoma o2)
			{
				if (o1.getAptitud() > o2.getAptitud()) return 1;
				else if(o1.getAptitud() < o2.getAptitud()) return -1;
				else return 0;
			}
		};
		Arrays.sort(pob, c);
		
		// 2) en el campo "puntuacionAcumulada" almacenamos el valor lineal segun la formula
		int tam = parametros.getTamPoblacion();
		double puntuacion=0;
		for (int i=0; i<tam; i++){
			double beta = 1.5; 
			double aux = ((double) (i-1)) / ( ((double)(tam)) - 1);
			aux = 2*(beta-1)*aux;
			aux = beta - aux;
			double aux2 = 1/((double)tam); 
			aux = aux2*aux; 
			
			puntuacion += aux ;
			pob[i].setPuntuacionAcumulada(puntuacion);
		}
		
		// 3) normalizamos
		double punt=0;
		for (int i=0; i<tam; i++){
			
			punt = pob[i].getPuntuacionAcumulada()/puntuacion ;
			pob[i].setPuntuacionAcumulada(punt);
		}
		
		// 4) llamar a ruleta
		return seleccionRuleta(pob, parametros);
	}
	
	/**
	 * Seleccionar TAM_POBLACION individuos.
	 * 
	 * Metodo de seleccion => Universal estocastico
	 * 
	 * Ejemplo
	 * 
	 * | A | 23 | 
	 * | B | 26 |
	 * | C | 27 |
	 * 
	 * escoger 3 individuos: 27/3 = 9 => random(0,9) = 3
	 * 
	 * | 3  | A |
	 * | 12 | A |
	 * | 21 | A |
	 * 
	 * fuente : http://catarina.udlap.mx/u_dl_a/tales/documentos/msp/rodriguez_m_m/capitulo3.pdf
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return Poblacion seleccionada
	 */
	private Cromosoma[] seleccionUniversalEstocastico(Cromosoma[] pob, Parametros parametros)
	{
		Cromosoma[] poblacionSeleccionada = new Cromosoma[parametros.getTamPoblacion()];
		
		// obtener la suma total de aptitudes de la poblacion y la puntuacion acumulada para cada individuo
		int suma = 0;
		for(int i=0; i<pob.length; i++){
			suma+=pob[i].getAptitud();
			pob[i].setPuntuacionAcumulada(suma);
		}
		
		// obtener los marcadores equalitariamente y generar el primer marcador
		int distanciaMarcadores = (int) (suma / parametros.getTamPoblacion());
		int marcadorActual;
		
		if (parametros.modoDebug)
			marcadorActual = 3;
		else
			marcadorActual = Operaciones.aleatorioEntre(0, distanciaMarcadores);
		
		// escogemos TAM_POBLACION individuos segun el metodo estocastico universal
		int indivudoInspeccionado = 0;
		boolean escogido = false;
		for(int i = 0; i<parametros.getTamPoblacion(); i++){
			escogido = false;
			while (!escogido) {
				if ( pob[indivudoInspeccionado].getPuntuacionAcumulada() >= marcadorActual ){
					poblacionSeleccionada[i] = pob[indivudoInspeccionado].clone();
					escogido = true;
				} else {
					indivudoInspeccionado++;
				}
			}
			marcadorActual += distanciaMarcadores;
		}
		
		return poblacionSeleccionada;
	}

	/**
	 * La reproduccion consiste en la seleccion de los individuos a reproducirse
	 * y en la aplicacion del operador de cruce a cada una de las parejas.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return nueva poblacion resultante
	 */
	private Cromosoma[] reproduccion(Cromosoma[] pob, Parametros parametros) 
	{
		// 1. Seleccionados para reproducir
		int[] selCruce= new int[parametros.getTamPoblacion()];
		
		// 2. Contador seleccionados
		int numSelCruce = 0;
		int puntoCruce;
		double prob;	
		
		// 3. Se eligen los individuos a cruzar
		for (int i=0; i<parametros.getTamPoblacion(); i++){
			if(parametros.modoDebug){
				prob = siguienteAleatorio();
			} else {
				prob=Math.random();
			}
			if (prob < parametros.getProbCruce()){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		
		// 4. El numero de seleccionados se hace par
		if ((numSelCruce % 2) == 1){
			numSelCruce--;
		}
		
		// 5. Se cruzan los individuos elegidos en un punto al azar
		for (int i=0; i<numSelCruce; i+=2){
			if (parametros.modoDebug){
				puntoCruce = (int) (siguienteAleatorio()*10 - 1);
			} else {
				puntoCruce = Operaciones.aleatorioEntre(0, 9 - 1);
			}
			Cromosoma[] cromos = cruce(pob[selCruce[i]],pob[selCruce[i+1]],puntoCruce,parametros);
			//los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]]=cromos[0];
			pob[selCruce[i+1]]=cromos[1];
		}
		return pob;
	}
	
	/**
	 * La reproduccion consiste en la seleccion de los individuos a reproducirse
	 * y en la aplicacion del operador de cruce a cada una de las parejas.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return nueva poblacion resultante
	 */
	private Cromosoma[] reproduccionDosPuntos(Cromosoma[] pob, Parametros parametros) 
	{
		// 1. Seleccionados para reproducir
		int[] selCruce= new int[parametros.getTamPoblacion()];
		
		// 2. Contador seleccionados
		int numSelCruce = 0;
		double prob;	
		
		// 3. Se eligen los individuos a cruzar
		for (int i=0; i<parametros.getTamPoblacion(); i++){
			if(parametros.modoDebug){
				prob = siguienteAleatorio();
			} else {
				prob=Math.random();
			}
			if (prob < parametros.getProbCruce()){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		
		// 4. El numero de seleccionados se hace par
		if ((numSelCruce % 2) == 1){
			numSelCruce--;
		}
		
		// 5. Se cruzan los individuos elegidos en dos puntos al azar
		int random1 = Operaciones.aleatorioEntre(0,8);
		int random2 = Operaciones.aleatorioEntre(0,8);
		// tien que ser (random1 < random2)
		if (random1>random2){
			int aux=random1;
			random1=random2;
			random2=aux;
		}
		for (int i=0; i<numSelCruce; i+=2){
			Cromosoma[] cromos = cruceDosPuntos(pob[selCruce[i]],pob[selCruce[i+1]],random1,random2,parametros);
			//los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]]=cromos[0];
			pob[selCruce[i+1]]=cromos[1];
		}
		return pob;
	}
	
	/**
	 * La reproduccion consiste en la seleccion de los individuos a reproducirse
	 * y en la aplicacion del operador de cruce a cada una de las parejas.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return nueva poblacion resultante
	 */
	private Cromosoma[] reproduccionPMX(Cromosoma[] pob, Parametros parametros) 
	{
		// 1. Seleccionados para reproducir
		int[] selCruce= new int[parametros.getTamPoblacion()];
		
		// 2. Contador seleccionados
		int numSelCruce = 0;
		double prob;	
		
		// 3. Se eligen los individuos a cruzar
		for (int i=0; i<parametros.getTamPoblacion(); i++){
			if(parametros.modoDebug){
				prob = siguienteAleatorio();
			} else {
				prob=Math.random();
			}
			if (prob < parametros.getProbCruce()){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		
		// 4. El numero de seleccionados se hace par
		if ((numSelCruce % 2) == 1){
			numSelCruce--;
		}
		
		// 5. Se cruzan los individuos elegidos en dos puntos al azar
		int random1 = Operaciones.aleatorioEntre(0,8);
		int random2 = Operaciones.aleatorioEntre(0,8);
		// tiene que ser (random1 < random2)
		if (random1>random2){
			int aux=random1;
			random1=random2;
			random2=aux;
		}
		for (int i=0; i<numSelCruce; i+=2){
			Cromosoma[] cromos = new Cromosoma[2];
			cromos[0] = new Cromosoma();
			cromos[1] = new Cromosoma();
			for (int j=0; j<9; j++)
			{
				Gen[] gen = crucePMX(pob[selCruce[i]].getGenes()[j],pob[selCruce[i+1]].getGenes()[j], random1, random2, parametros);
				cromos[0].setGen(j,gen[0]);
				cromos[1].setGen(j,gen[1]);
			}
			cromos[0].evaluarCromosoma();
			cromos[1].evaluarCromosoma();
			
			//los nuevos individuos sustituyen a sus progenitores
			pob[selCruce[i]]=cromos[0];
			pob[selCruce[i+1]]=cromos[1];
		}
		return pob;
	}
	

	/**
	 * La reproduccion consiste en la seleccion de los individuos a reproducirse
	 * y en la aplicacion del operador de cruce a cada una de las parejas.
	 * 
	 * @param poblacion
	 * @param parametros del problema
	 * @return nueva poblacion resultante
	 */
	private Cromosoma[] reproduccionOX(Cromosoma[] pob, Parametros parametros) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * La mutacion cambia ciertos genes de ciertos individuos de la poblacion
	 * 
	 * @param poblacion
	 * @param paramametros del problema
	 */
	private Cromosoma[] mutacion(Cromosoma[] pob, Parametros parametros)
	{
		Cromosoma[] pobMutante = new Cromosoma[parametros.getTamPoblacion()];
		
		double random;
		for (int i = 0; i < parametros.getTamPoblacion(); i++) {
			pobMutante[i] = pob[i].clone();
			for (int j = 0; j < 9; j++) {
				if (parametros.modoDebug) {
					random = siguienteAleatorio();
				}else{
					random = Math.random();
				}
				if (random < parametros.getProbMutacion()) {
					pobMutante[i].mutaGen(j);
					pobMutante[i].evaluarCromosoma();
				}
			}
		}
		
		return pobMutante;
		
	}
	

	/**
	 * La mutacion cambia ciertos genes de ciertos individuos de la poblacion
	 * 
	 * @param poblacion
	 * @param paramametros del problema
	 */
	private Cromosoma[] mutacionIntervalo(Cromosoma[] pob, Parametros parametros) {
		Cromosoma[] pobMutante = new Cromosoma[parametros.getTamPoblacion()];
		
		double random;
		for (int i = 0; i < parametros.getTamPoblacion(); i++) {
			pobMutante[i] = pob[i].clone();
			for (int j = 0; j < 9; j++) {
				if (parametros.modoDebug) {
					random = siguienteAleatorio();
				}else{
					random = Math.random();
				}
				if (random < parametros.getProbMutacion()) {
					pobMutante[i].mutaGenIntervalo(j);
					pobMutante[i].evaluarCromosoma();
				}
			}
		}
		
		return pobMutante;
	}

	/**
	 * El operador de cruce toma dos padres y genera dos cadenas hijas.
	 * La función calcula la aptitud de los nuevos individuos.
 	 *
	 * @param Cromosoma padre
	 * @param Cromosoma madre
	 * @param Cromosoma hijo1 (hijo)
	 * @param Cromosoma hijo2 (hija)
	 * @param puntoCruce
	 */
	private Cromosoma[] cruce(Cromosoma padre, Cromosoma madre, int puntoCruce, Parametros parametros) 
	{
		
		//Inicializamos los hijos
		Cromosoma hijo=new Cromosoma();
		Cromosoma hija=new Cromosoma();
		
		// primera parte del intercambio: 
		// copiar progenitor desde el principio hasta el punto de cruce
		
		for (int i = 0; i < puntoCruce; i++){
			hijo.setGen(i, padre.getGenes()[i].clone());
			hija.setGen(i, madre.getGenes()[i].clone());
		}
		
		// segunda parte del intercambio:
		// copiar desde el punto de cruce hasta el final del otro progenitor
		
		for (int i = puntoCruce; i < 9; i++){
			hija.setGen(i, padre.getGenes()[i].clone());
			hijo.setGen(i, madre.getGenes()[i].clone());
		}
		
		// evaluar los nuevos individuos		
		hijo.evaluarCromosoma();
		hija.evaluarCromosoma();
		
		//devuelve los hijos
		Cromosoma[] cromos = new Cromosoma[2];
		cromos[0]=hijo;
		cromos[1]=hija;
		return cromos;
		
	}
	
	/**
	 * |A|B|C|   |A'|B'|C'|    |A |B |C |   
	 * |D|E|F| + |D'|E'|F'| => |D'|E'|F'| 
	 * |G|H|I|   |G'|H'|I'|    |G |H |I |
 	 *       (DEF)
 	 * 
	 * @param Cromosoma padre
	 * @param Cromosoma madre
	 * @param Cromosoma hijo1 (hijo)
	 * @param Cromosoma hijo2 (hija)
	 * @param puntoCruce
	 */
	public Cromosoma[] cruceDosPuntos(Cromosoma padre, Cromosoma madre, int puntoCruce1, int puntoCruce2,Parametros parametros) 
	{
		
		//Inicializamos los hijos
		Cromosoma hijo=new Cromosoma();
		Cromosoma hija=new Cromosoma();
		
		// primera parte del intercambio: 
		// copiar progenitor desde el principio hasta el punto de cruce 1
		
		for (int i = 0; i < puntoCruce1; i++){
			hijo.setGen(i, padre.getGenes()[i].clone());
			hija.setGen(i, madre.getGenes()[i].clone());
		}
		
		// segunda parte del intercambio:
		// copiar desde el punto de cruce 1 hasta el punto de cruce 2
		
		for (int i = puntoCruce1; i < puntoCruce2; i++){
			hija.setGen(i, padre.getGenes()[i].clone());
			hijo.setGen(i, madre.getGenes()[i].clone());
		}
		
		// tercera parte del intercambio:
		// copiar desde el punto de cruce 2 hasta el final del otro progenitor
		
		for (int i = puntoCruce2; i < 9; i++){
			hijo.setGen(i, padre.getGenes()[i].clone());
			hija.setGen(i, madre.getGenes()[i].clone());
		}
		
		// evaluar los nuevos individuos		
		hijo.evaluarCromosoma();
		hija.evaluarCromosoma();
		
		//devuelve los hijos
		Cromosoma[] cromos = new Cromosoma[2];
		cromos[0]=hijo;
		cromos[1]=hija;
		return cromos;
		
	}
	
	/**
	 * @param Cromosoma padre
	 * @param Cromosoma madre
	 * @param Cromosoma hijo1 (hijo)
	 * @param Cromosoma hijo2 (hija)
	 * @param puntoCruce
	 */
	public Gen[] crucePMX(Gen padre, Gen madre, int random1, int random2, Parametros parametros) 
	{		
		//Inicializamos los hijos
		Gen hijo=new Gen(padre.getFijos());
		Gen hija=new Gen(madre.getFijos());
		ArrayList<Integer> usadoshijo=new ArrayList<Integer>();
		ArrayList<Integer> usadoshija=new ArrayList<Integer>();
		
		for (int i=random1; i<random2; i++){
			hijo.setNumero(i, padre.getCuadricula()[i]);
			hija.setNumero(i, madre.getCuadricula()[i]);
			if (usadoshijo.contains(madre.getCuadricula()[i]))
				usadoshijo.remove((Integer)madre.getCuadricula()[i]);
			else
				usadoshija.add(madre.getCuadricula()[i]);

			if (usadoshija.contains(padre.getCuadricula()[i]))
				usadoshija.remove((Integer)padre.getCuadricula()[i]);
			else
				usadoshijo.add(padre.getCuadricula()[i]);
		}
		
		Iterator<Integer> ithijo = usadoshijo.iterator();
		Iterator<Integer> ithija = usadoshija.iterator();
		for (int i=0; i<random1; i++){
			if (usadoshijo.contains(madre.getCuadricula()[i]))
				hijo.setNumero(i, ithija.next());
			else
				hijo.setNumero(i, madre.getCuadricula()[i]);
			if (usadoshija.contains(padre.getCuadricula()[i]))
				hija.setNumero(i, ithijo.next());
			else
				hija.setNumero(i, padre.getCuadricula()[i]);
		}
		
		for (int i=random2; i<9; i++){
			if (usadoshijo.contains(madre.getCuadricula()[i]))
				hijo.setNumero(i, ithija.next());
			else
				hijo.setNumero(i, madre.getCuadricula()[i]);
			if (usadoshija.contains(padre.getCuadricula()[i]))
				hija.setNumero(i, ithijo.next());
			else
				hija.setNumero(i, padre.getCuadricula()[i]);
		}
		
		Gen[] genes=new Gen[2];
		genes[0]=hijo;
		genes[1]=hija;
		return genes;
	}
	
	
	/**
	 * 
	 * @param poblacion a evaluar
	 * @return la posicion del mejor individuo
	 */
	private int evaluarPoblacion(Cromosoma[] poblacion)
	{
		double puntuacionAcumulada = 0;
		double aptitudMejor = 0;
		double sumaAptitudes = 0;
		
		int pos_mejor = 0;
		
		for (int i = 0; i< poblacion.length; i++) {
			poblacion[i].evaluarCromosoma();
		}
		
		for (int i = 0; i< poblacion.length; i++) {
			//Calculamos la suma de las aptitudes
			sumaAptitudes += poblacion[i].getAptitud();
			//Buscamos la aptitud mejor y su posicion
			if (poblacion[i].getAptitud() > aptitudMejor) {
				pos_mejor = i;
				aptitudMejor = poblacion[i].getAptitud();
			}
		}
		
		for (int i = 0; i< poblacion.length; i++) {
			puntuacionAcumulada+=poblacion[i].getAptitud();
			poblacion[i].setPuntuacionAcumulada(puntuacionAcumulada/sumaAptitudes);
		}
		
		return pos_mejor;
	}
	
	/**
	 * Dada una poblacion nos devuelve al mejor individuo
	 * @param poblacion para buscar al mejor
	 * @return la posicion del mejor individuo
	 */
	private int dameMejor(Cromosoma[] poblacion)
	{
		double aptitudMejor = 0;
		
		int pos_mejor = 0;
		
		for (int i = 0; i< poblacion.length; i++) {
			if (poblacion[i]!=null && poblacion[i].getAptitud() > aptitudMejor) {
				pos_mejor = i;
				aptitudMejor = poblacion[i].getAptitud();
			}
		}
		
		return pos_mejor;
	}
	
	
	/* Getters */
	public Cromosoma[] getGokus(){ return this.gokus; }
	public Cromosoma[] getMejoresCromosomas() {return this.mejoresCromosomas; }
	public double[] getMedias() {return this.medias; }
	
}

