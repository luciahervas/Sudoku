package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import algoritmoGenetico.Cromosoma;

import controlador.Controlador;
import controlador.Parametros;

public class Gui extends JFrame
{	
	private static final long serialVersionUID = 1L;

	private Controlador c;
	
	private SudokuCanvas tableroJuego;
	private static final int FRAME_WEIGHT = 750;
	private static final int FRAME_HEIGHT = 500;
	
	private JSplitPane panelPrincipal;
	private JPanel panelFormulario;
	private JPanel panelCanvas;
	
	/**/
	private JTextField formPoblacion;
	private JTextField formGeneraciones;
	private JTextField formCruce;
	private JTextField formMutacion;
	private JTextField formElitismo;
	private JComboBox<String> formFuncionSeleccion;
	private JComboBox<String> formFuncionAptitud;
	private JComboBox<String> formFuncionCruce;
	private JComboBox<String> formFuncionMutacion;
	/**/
	
	private JMenuBar menu;
	private JMenu menuArchivo;
	private JMenu menuNuevo;
	private JMenuItem itemVacio;
	private JMenuItem itemPredefinido;
	private JMenuItem itemSalir;

	/**
	 * Constructora por defecto: tablero 9x9
	 * 
	 * @param controlador
	 */
	public Gui(Controlador controlador)
	{
		super();
		c=controlador;
		this.tableroJuego = new SudokuCanvas(controlador.getTablero());
		crearInterfaz();
	}
	
	public Parametros getParametros()
	{
		Parametros p = new Parametros();
		p.setTamPoblacion(Integer.valueOf(this.formPoblacion.getText()));
		p.setNumGeneraciones(Integer.valueOf(this.formGeneraciones.getText()));
		p.setProbCruce(Float.valueOf(this.formCruce.getText()));
		p.setProbMutacion(Float.valueOf(this.formMutacion.getText()));
		p.setElitismo(Float.valueOf(this.formElitismo.getText()));
		p.setFuncSeleccion(this.formFuncionSeleccion.getSelectedIndex());
		p.setFuncAptitud(this.formFuncionAptitud.getSelectedIndex());
		p.setFuncCruce(this.formFuncionCruce.getSelectedIndex());
		p.setFuncMutacion(this.formFuncionMutacion.getSelectedIndex());
		p.setFijos(this.tableroJuego.getTablero());
		return p;
	}
	
	// ============================================================= //
	
	private void crearInterfaz()
	{
		//this.setContentPane(obtenerPanelPrincipal());
		obtenerPanelPrincipal();
		crearBarraMenu();
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, FRAME_WEIGHT, FRAME_HEIGHT);
		setResizable(false);
	}
	
	private void obtenerPanelPrincipal()
	{
		this.setLayout(new BorderLayout());
		
		panelPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panelPrincipal.setResizeWeight(0.85);
		panelPrincipal.setDividerSize(1);
		
		// 1) panel izquierdo: formulario
		panelFormulario = obtenerFormulario();
		// obtenerDetalles(); // TODO
		
		// 2) panel derecho: sudoku
		panelCanvas = obtenerPanelCanvas();
		
		// 3) insertar los paneles
		//panelPrincipal.setLayout(new BorderLayout());	
		panelPrincipal.add(panelCanvas);
		panelPrincipal.add(panelFormulario);


		this.add(panelPrincipal, BorderLayout.CENTER);
		return ;
	}
	
	private JPanel obtenerFormulario()
	{
		panelFormulario = new JPanel();
		
		panelFormulario.setLayout(new GridLayout(14, 1, 0, 0));

		// 1) panelPoblacion
			JPanel panelPoblacion = new JPanel();
			panelPoblacion.setLayout(new GridLayout(1,0,0,0)); 
			JLabel labelPoblacion = new JLabel("  Poblacion: ");
			panelPoblacion.add(labelPoblacion);
			//panelPoblacion.add(new JPanel()); // hueco intermedio
			formPoblacion = new JTextField(String.valueOf(Parametros.TAM_POBLACION_DEFECTO));
			panelPoblacion.add(formPoblacion);
		panelFormulario.add(panelPoblacion);

		// 2) panelPoblacionInvalido
			JPanel panelPoblacionInvalido = new JPanel();
			panelPoblacionInvalido.setLayout(new GridLayout(1,2,0,0));
			//panelPoblacionInvalido.add(new JPanel()); //hueco intermedio
			JLabel labelPoblacionInvalido = new JLabel("Par‡metro poblaci—n incorrecto");
			labelPoblacionInvalido.setForeground(Color.RED);
			labelPoblacionInvalido.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			labelPoblacionInvalido.setVisible(false);
			panelPoblacionInvalido.add(labelPoblacionInvalido);
		panelFormulario.add(panelPoblacionInvalido);


		// 3) panelGeneraciones
			JPanel panelGeneraciones = new JPanel();
			panelGeneraciones.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelGeneraciones = new JLabel("  Generaciones: ");
			panelGeneraciones.add(labelGeneraciones);
			//panelGeneraciones.add(new JPanel()); // hueco intermedio
			formGeneraciones = new JTextField(String.valueOf(Parametros.NUM_GENERACIONES_DEFECTO));
			panelGeneraciones.add(formGeneraciones);
		panelFormulario.add(panelGeneraciones);

		// 4) panelGeneracionesInvalido
			JPanel panelGeneracionesInvalido = new JPanel();
			panelGeneracionesInvalido.setLayout(new GridLayout(1,2,0,0));
			//panelGeneracionesInvalido.add(new JPanel()); // hueco intermedio
			JLabel labelgeneracionesInvalido = new JLabel("Par‡metro generaciones inv‡lido");
			labelgeneracionesInvalido.setForeground(Color.RED);
			labelgeneracionesInvalido.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			labelgeneracionesInvalido.setVisible(false);
			panelGeneracionesInvalido.add(labelgeneracionesInvalido);
		panelFormulario.add(panelGeneracionesInvalido);

		// 5) panelCruce
			JPanel panelCruce = new JPanel();
			panelCruce.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelCruce = new JLabel("  Prob. Cruce: ");
			panelCruce.add(labelCruce);
			//panelCruce.add(new JPanel()); // hueco intermedio
			formCruce = new JTextField(String.valueOf(Parametros.PROB_CRUCE_DEFECTO));
			panelCruce.add(formCruce);
		panelFormulario.add(panelCruce);

		// 6) panelCruceInvalido
			JPanel panelCruceInvalido = new JPanel();
			panelCruceInvalido.setLayout(new GridLayout(1,2,0,0));
			//panelCruceInvalido.add(new JPanel()); // hueco intermedio
			JLabel labelCruceInvalido = new JLabel("Parametro Cruce invalido");
			labelCruceInvalido.setForeground(Color.RED);
			labelCruceInvalido.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			labelCruceInvalido.setVisible(false);
			panelCruceInvalido.add(labelCruceInvalido);
		panelFormulario.add(panelCruceInvalido);

		// 7) panelMutacion
			JPanel panelMutacion = new JPanel();
			panelMutacion.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelMutacion = new JLabel("  Prob. Mutacion: ");
			panelMutacion.add(labelMutacion);
			//panelMutacion.add(new JPanel()); // hueco intermedio
			formMutacion = new JTextField(String.valueOf(Parametros.PROB_MUTACION_DEFECTO));
			panelMutacion.add(formMutacion);
		panelFormulario.add(panelMutacion);

		// 8) panelMutacionesInvalido
			JPanel panelMutacionesInvalido = new JPanel();
			panelMutacionesInvalido.setLayout(new GridLayout(1,2,0,0));
			//panelMutacionesInvalido.add(new JPanel()); // hueco intermedio
			JLabel labelMutacionesInvalido = new JLabel("Parametro mutaciones invalido");
			labelMutacionesInvalido.setForeground(Color.RED);
			labelMutacionesInvalido.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			labelMutacionesInvalido.setVisible(false);
			panelMutacionesInvalido.add(labelMutacionesInvalido);
		panelFormulario.add(panelMutacionesInvalido);

		// 9) panelElitismo
			JPanel panelElitismo = new JPanel();
			panelElitismo.setLayout(new GridLayout(1,0,0,0));
			JLabel labelElitismo = new JLabel("  Porc. Elitismo: ");
			panelElitismo.add(labelElitismo);
			//panelElitismo.add(new JPanel()); // hueco intermedio
			formElitismo = new JTextField(String.valueOf(Parametros.ELITISMO_DEFECTO)); 
			panelElitismo.add(formElitismo);
		panelFormulario.add(panelElitismo);

		// 10) panelElitismoInvalido
			JPanel  panelElitismoInvalido = new JPanel();
			panelElitismoInvalido.setLayout(new GridLayout(1,0,0,0));
			//panelElitismoInvalido.add(new JPanel()); // hueco intermedio
			JLabel labelElitismoInvalido = new JLabel("Parametro Elitismo invalido");
			labelElitismoInvalido.setForeground(Color.RED);
			labelElitismoInvalido.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			labelElitismoInvalido.setVisible(false);
			panelElitismoInvalido.add(labelElitismoInvalido);
		panelFormulario.add(panelElitismoInvalido);

		// 11) panelSeleccion
			JPanel panelSeleccion = new JPanel();
			panelSeleccion.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelSeleccion = new JLabel("  F. Seleccion: ");
			panelSeleccion.add(labelSeleccion);
			//panelSeleccion.add(new JPanel()); // hueco intermedio
			formFuncionSeleccion = new JComboBox<String>();
			formFuncionSeleccion.setModel(new DefaultComboBoxModel<String>(new String[] {"Torneo", "Ruleta"}));
			formFuncionSeleccion.setSelectedIndex(1);
			panelSeleccion.add(formFuncionSeleccion);
		panelFormulario.add(panelSeleccion);

		// 12) panelAptitud
			JPanel panelAptitud = new JPanel();
			panelAptitud.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelAptitud = new JLabel("  F. Aptitud: ");
			panelAptitud.add(labelAptitud);
			//panelAptitud.add(new JPanel()); // hueco intermedio
			formFuncionAptitud = new JComboBox<String>();
			formFuncionAptitud.setModel(new DefaultComboBoxModel<String>(new String[] {"Repetidos","Sumatorio", "Factorial"}));
			formFuncionAptitud.setSelectedIndex(1);
			panelAptitud.add(formFuncionAptitud);
		panelFormulario.add(panelAptitud);
		
		// 13) panelFuncionCruce
			JPanel panelFuncionCruce = new JPanel();
			panelFuncionCruce.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelFuncionCruce = new JLabel("  F. Cruce: ");
			panelFuncionCruce.add(labelFuncionCruce);
			//panelAptitud.add(new JPanel()); // hueco intermedio
			formFuncionCruce = new JComboBox<String>();
			formFuncionCruce.setModel(new DefaultComboBoxModel<String>(new String[] {"Cruce 1", "Cruce 2"}));
			formFuncionCruce.setSelectedIndex(1);
			panelFuncionCruce.add(formFuncionCruce);
		panelFormulario.add(panelFuncionCruce);
		
		// 14) panelFuncionMutacion
			JPanel panelFuncionMutacion = new JPanel();
			panelFuncionMutacion.setLayout(new GridLayout(1, 0, 0, 0));
			JLabel labelFuncionMutacion = new JLabel("  F. Mutacion: ");
			panelFuncionMutacion.add(labelFuncionMutacion);
			//panelAptitud.add(new JPanel()); // hueco intermedio
			formFuncionMutacion = new JComboBox<String>();
			formFuncionMutacion.setModel(new DefaultComboBoxModel<String>(new String[] {"Mutacion 1", "Mutacion 2"}));
			formFuncionMutacion.setSelectedIndex(1);
			panelFuncionMutacion.add(formFuncionMutacion);
		panelFormulario.add(panelFuncionMutacion);
		
		return panelFormulario;
	}
	
	private JPanel obtenerPanelCanvas()
	{
		panelCanvas = new JPanel();
		panelCanvas.setLayout(new GridLayout(0, 1, 0, 0));	
		panelCanvas.add(this.tableroJuego);
		return panelCanvas;
	}
	
	private void crearBarraMenu()
	{
		menu = new JMenuBar();
		setJMenuBar(menu);

		menuArchivo = new JMenu("Archivo");
		// 1
		menuNuevo = new JMenu("Nuevo");
		//1.1
		itemVacio = new JMenuItem("Vacio");
		itemVacio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[][] tablero = new byte[9][9];
				boolean[][] fijos = new boolean[9][9];
				fijos = new boolean[9][9];
				for (int i=0; i<9; i++)
					for (int j=0; j<9; j++)
						fijos[i][j]=true;
				tableroJuego.cambiarTablero(tablero,fijos);
			}
		});
		menuNuevo.add(itemVacio);
		//1.2
		itemPredefinido = new JMenuItem("Predefinido");
		itemPredefinido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				byte[][] tablero = GeneradorSudokus.enunciadoSudoku();
				boolean[][] fijos = new boolean[9][9];
				fijos = new boolean[9][9];
				for (int i=0; i<9; i++)
					for (int j=0; j<9; j++)
						fijos[i][j]=true;
				tableroJuego.cambiarTablero(tablero,fijos);
			}
		});
		menuNuevo.add(itemPredefinido);
		menuArchivo.add(menuNuevo);
		// 2
		itemSalir = new JMenuItem("Salir");
		itemSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		// 3
		itemSalir = new JMenuItem("Ejecutar");
		itemSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.ejecutar();
			}
		});
		menuArchivo.add(itemSalir);

		menu.add(menuArchivo);
	}

	public void pintaEsto(Cromosoma cromosoma, byte[][] fijos) {
		byte[][] tablero = cromosoma.getFenotipo();
		boolean[][] bofijos = new boolean[9][9];
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (fijos[i][j]!=0)
					bofijos[i][j]=true;
		tableroJuego.cambiarTablero(tablero,bofijos);
	}

}
