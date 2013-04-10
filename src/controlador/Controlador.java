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
		parametros.setFijos(GeneradorSudokus.enunciadoSudoku());
		genetico = new AlgoritmoGenetico();
		genetico.algoritmo_genetico(parametros);
		//gui = new Gui(this);
	}
	
	public byte[][] getTablero(){ return parametros.getFijos(); }
	
	public static void main(String[] args)
	{
		Controlador c = new Controlador();
		
	}
}
