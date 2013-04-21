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
	
	public Controlador()
	{
		genetico = new AlgoritmoGenetico();
		gui = new Gui(this);
		ventanaEmergente = new VentanaEmergente();
	}
	
	public void ejecutar()
	{
		Parametros p=gui.getParametros();
		genetico.algoritmo_genetico(p);
		
		Cromosoma[] gokus = genetico.getGokus();
		Cromosoma[] mejores = genetico.getMejoresCromosomas();
		
		double[] aptitudesGokus = obtenerAptitudes(gokus);
		double[] aptitudesMejores = obtenerAptitudes(mejores);
		double[] aptitudesMedias = genetico.getMedias();
		
		gui.pintaEsto(gokus[gokus.length - 1]);
		ventanaEmergente.pinta(aptitudesGokus, aptitudesMejores, aptitudesMedias);
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
		int[][] m1 = GeneradorSudokus.enunciadoTest1();
		int[][] m2 = GeneradorSudokus.enunciadoTest2();
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
		
		// Cruces
	}
}
