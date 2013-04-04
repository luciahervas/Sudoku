package controlador;

import gui.Gui;
import algoritmoGenetico.AlgoritmoGenetico;

public class Controlador 
{
	private AlgoritmoGenetico genetico;
	private Gui gui;
	
	public Controlador()
	{
		genetico = new AlgoritmoGenetico();
		gui = new Gui(this);
	}
	
	public static void main(String[] args)
	{
		Controlador c = new Controlador();
		
	}
}
