package controlador;

import gui.GeneradorSudokus;
import gui.Gui;
import algoritmoGenetico.AlgoritmoGenetico;

public class Controlador 
{
	private AlgoritmoGenetico genetico;
	private Gui gui;
	private Parametros parametros;
	
	public Controlador()
	{
		parametros = new Parametros();
		genetico = new AlgoritmoGenetico();
		gui = new Gui(this);
	}
	
	public void ejecutar()
	{
		genetico.algoritmo_genetico(gui.getParametros());
		gui.pintaEsto(genetico.getGokus()[parametros.getNumGeneraciones()-1],gui.getParametros().getFijos());
	}
	
	public byte[][] getTablero(){ return parametros.getFijos(); }
	
	public static void main(String[] args)
	{
		Controlador c = new Controlador();
		
	}
}
