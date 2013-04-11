package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.math.plot.Plot2DPanel;

public class VentanaEmergente extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Plot2DPanel grafica;
	
	private static final int FRAME_WEIGHT = 750;
	private static final int FRAME_HEIGHT = 500;
	
	
	public VentanaEmergente()
	{
		super();
		grafica = new Plot2DPanel();
		this.add(grafica);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FRAME_WEIGHT, FRAME_HEIGHT);
		this.setResizable(false);
	}
	
	public void pinta(double[] gokus, double[] mejores, double[] medias)
	{
		this.setVisible(true);
		// borrar la grafica anterior
		grafica.removeAllPlots();
		
		// definir los datos
		int n = gokus.length;
		double[] x = new double[n];
		for(int i=0;i<n;i++){
			x[i]=i;
		}
		// definir la layenda de datos
		grafica.addLegend("SOUTH");
		// dibujar la grafica
		grafica.addLinePlot("Cromosoma mejor de cada generaci—n", x, mejores);
		grafica.addLinePlot("Cromosoma mejor encontrado", x, gokus);
		grafica.addLinePlot("Media de cada generaci—n", x, medias);
	}
}
