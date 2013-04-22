package controlador;

import gui.GeneradorSudokus;
import gui.Gui;
import gui.VentanaEmergente;
import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.Cromosoma;

public class Controlador 
{
	private AlgoritmoGenetico genetico;
	private Gui gui;
	private VentanaEmergente ventanaEmergente;
	private Cromosoma ultimaSolucion;
	
	public Controlador()
	{
		genetico = new AlgoritmoGenetico();
		gui = new Gui(this);
		ventanaEmergente = new VentanaEmergente();
		ultimaSolucion = null;
	}
	
	public void ejecutar()
	{
		Parametros p=gui.getParametros();
		genetico.algoritmo_genetico(p);
		
		Cromosoma[] gokus = genetico.getGokus();
		Cromosoma[] mejores = genetico.getMejoresCromosomas();
		this.ultimaSolucion = gokus[gokus.length - 1];
		
		double[] aptitudesGokus = obtenerAptitudes(gokus);
		double[] aptitudesMejores = obtenerAptitudes(mejores);
		double[] aptitudesMedias = genetico.getMedias();
		volverMaximo(p,aptitudesGokus);
		volverMaximo(p,aptitudesMejores);
		volverMaximo(p,aptitudesMedias);
		
		gokus[gokus.length - 1].evaluarCromosoma();
		gui.pintaEsto(ultimaSolucion);
		ventanaEmergente.pinta(aptitudesGokus, aptitudesMejores, aptitudesMedias);
	}
	
	public void remarcar()
	{

		if (ultimaSolucion != null) {
			boolean[][] remarcar = new boolean[9][9];
			int[][] matriz = ultimaSolucion.dameMatriz();
			
			for(int i=0; i<9; i++){
				for(int j=0; j<9; j++){
					if (estaNumero(matriz[i][j], matriz[i], j+1)){
						remarcar[i][j] = true;
					}
				}
			}
			
		gui.remarcaEsto(matriz,remarcar);
		}
		
	}
	
	private boolean estaNumero(int numero, int[] vector, int desdeAqui)
	{
		if(desdeAqui >= 3) return false;
		for (int i = desdeAqui; i<3; i++){
			if (vector[i] == numero)
				return true;
		}
		return false;
	}
	
	private void volverMaximo(Parametros p,double[] vector){
		for (int i=0; i<vector.length; i++)
			switch(p.getFuncAptitud()) {
				case 0:
					vector[i] = 72 - vector[i]; 
				break;
				case 1:
					vector[i] = 405*9 - vector[i];
				break;
				case 2:
					vector[i] = 3265920*9 - vector[i];
				break;
			}
	}
	
	private double[] obtenerAptitudes(Cromosoma[] array)
	{
		double[] a = new double[array.length];
		for (int i = 0; i<array.length; i++){
			a[i] = array[i].getAptitud();
		}
		return a;
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Controlador c = new Controlador();		
	}
	
	// --------------------------------------------
	
	public static void _main(String[] args)
	{
		AlgoritmoGenetico g = new AlgoritmoGenetico();
		//int[][] m1 = GeneradorSudokus.enunciadoTest1();
		//int[][] m2 = GeneradorSudokus.enunciadoTest2();
		int[][] m1 = GeneradorSudokus.enunciadoSudoku1();
		int[][] m2 = GeneradorSudokus.enunciadoSudoku2();
		Cromosoma s1 = new Cromosoma(m1);
		Cromosoma s2 = new Cromosoma(m2);
		
		// Cruces
		Cromosoma[] hijos = new Cromosoma[2];
		hijos = g.cruceDosPuntos(s1, s2, 3, 6, new Parametros());
		if (m2.equals(hijos[0].getFenotipo())){
			if(m1.equals(hijos[1].getFenotipo())){
				System.out.println("Correcto");
			}
		}
	}
}
