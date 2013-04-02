package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controlador.Controlador;

public class Gui extends JFrame
{
	private Controlador controlador;
	
	private static final long serialVersionUID = 1L;
	
	private SudokuGrid sudokiGrid;
	private static final int CELDAS_ANCHO = 9;
	private static final int CELDAS_ALTO = 9;
	private static final int FRAME_WEIGHT = 800;
	private static final int FRAME_HEIGHT = 600;

	
	private JPanel panelPrincipal;
	private JMenuBar menu;
	private JMenu menuArchivo;
	private JMenuItem itemNuevo;
	private JMenuItem itemSalir;

	/**
	 * Constructora por defecto: tablero 9x9
	 * 
	 * @param controlador
	 * @wbp.parser.constructor
	 */
	public Gui(Controlador controlador)
	{
		super();
		this.sudokiGrid = new SudokuGrid(CELDAS_ANCHO, CELDAS_ALTO);
		crearInterfaz();
	}
	
	/**
	 * Crear una interfaz con un tablero de nxm
	 * 
	 * @param controlador
	 * @param n
	 * @param m
	 */
	public Gui(Controlador controlador, int n, int m)
	{
		super();
		this.sudokiGrid = new SudokuGrid(n, m);
		crearInterfaz();
	}
	
	// ============================================================= //
	
	private void crearInterfaz()
	{
		this.setContentPane(obtenerPanelPrincipal());
		crearBarraMenu();
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FRAME_WEIGHT, FRAME_HEIGHT);
		setResizable(false);
	}
	
	private JPanel obtenerPanelPrincipal()
	{
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(0, 1, 0, 0));	
		panelPrincipal.add(this.sudokiGrid);
		return panelPrincipal;
	}
	
	private void crearBarraMenu()
	{
		menu = new JMenuBar();
		setJMenuBar(menu);

		menuArchivo = new JMenu("Archivo");
		// 1
		itemNuevo = new JMenuItem("Nuevo");
		itemNuevo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO	
			}
		});
		menuArchivo.add(itemNuevo);
		// 2
		itemSalir = new JMenuItem("Salir");
		itemSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menuArchivo.add(itemSalir);

		menu.add(menuArchivo);
	}

}
