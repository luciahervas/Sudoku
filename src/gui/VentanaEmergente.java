package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class VentanaEmergente extends JFrame implements WindowListener
{
	private static final long serialVersionUID = 1L;

	private Plot2DPanel grafica;
	
	private static final int FRAME_WEIGHT = 750;
	private static final int FRAME_HEIGHT = 500;
	
	
	public VentanaEmergente()
	{
		super();
		this.addWindowListener(this);
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

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.setVisible(false);		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
