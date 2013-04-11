package controlador;

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
		Controlador c = new Controlador();		
	}
}
