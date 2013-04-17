package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
	private JButton go;
	
	/**/
	private JTextField formPoblacion;
	private JTextField formGeneraciones;
	private JTextField formCruce;
	private JTextField formMutacion;
	private JTextField formElitismo;
	private JComboBox formFuncionSeleccion;
	private JComboBox formFuncionAptitud;
	private JComboBox formFuncionCruce;
	private JComboBox formFuncionMutacion;
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
		this.tableroJuego = new SudokuCanvas();
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
		
		// 2) panel derecho: sudoku
		panelCanvas = obtenerPanelCanvas();
		
		// 3) insertar los paneles
		panelPrincipal.add(panelCanvas);
		panelPrincipal.add(panelFormulario);


		this.add(panelPrincipal, BorderLayout.CENTER);
		return ;
	}
	
	/**
	 * Crea un panel para el formulario con un label y el formulario
	 * 
	 * @param nombre que se muestra en el label
	 * @param formulario : atributo de clase
	 * @param (boolean) false para los formularios, true si from es NULL
	 * @return panel creado
	 */
	private JPanel crearPanelInterno(String nombre, Component form, boolean esAviso)
	{
		JPanel p = new JPanel();
		JLabel l = new JLabel(nombre);
		if(!esAviso){
			p.setLayout(new GridLayout(1,0,0,0)); 	
			p.add(l);
			p.add(form);
		} else {
			// panel de aviso
			p.setLayout(new GridLayout(1,2,0,0));
			l.setForeground(Color.RED);
			l.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			l.setVisible(false);
			p.add(l);
		}
		return p;
	}
	
	/**
	 * Crea el panel derecho - el formulario para los parametros de entrada
	 * 
	 * @return panel
	 */
	private JPanel obtenerFormulario()
	{
		panelFormulario = new JPanel();
		panelFormulario.setLayout(new GridLayout(15, 1, 0, 0));

		// 1) panelPoblacion
		formPoblacion = new JTextField(String.valueOf(Parametros.TAM_POBLACION_DEFECTO));
		JPanel p1 = crearPanelInterno("Poblacion: ", formPoblacion, false);
		panelFormulario.add(p1);
		
		// 2) panelPoblacionInvalido
		JPanel p2 = crearPanelInterno("Parametro poblacion incorrecto", null, true);
		panelFormulario.add(p2);

		// 3) panelGeneraciones
		formGeneraciones = new JTextField(String.valueOf(Parametros.NUM_GENERACIONES_DEFECTO));
		JPanel p3 = crearPanelInterno("Generaciones: ", formGeneraciones, false);
		panelFormulario.add(p3);

		// 4) panelGeneracionesInvalido
		JPanel p4 = crearPanelInterno("Parametro generaciones invalido", null, true);
		panelFormulario.add(p4);

		// 5) panelCruce
		formCruce = new JTextField(String.valueOf(Parametros.PROB_CRUCE_DEFECTO));
		JPanel p5 = crearPanelInterno("Prob. de cruce", formCruce, false);
		panelFormulario.add(p5);

		// 6) panelCruceInvalido
		JPanel p6 = crearPanelInterno("Parametro p. cruce invalido", null, true);
		panelFormulario.add(p6);

		// 7) panelMutacion
		formMutacion = new JTextField(String.valueOf(Parametros.PROB_MUTACION_DEFECTO));
		JPanel p7 = crearPanelInterno("Prob. de mutacion", formMutacion, false);
		panelFormulario.add(p7);

		// 8) panelMutacionesInvalido
		JPanel p8 = crearPanelInterno("Parametro mutaciones invalido", null, true);
		panelFormulario.add(p8);

		// 9) panelElitismo
		formElitismo = new JTextField(String.valueOf(Parametros.ELITISMO_DEFECTO)); 
		JPanel p9 = crearPanelInterno("Proporcion de elitismo", formElitismo, false);
		panelFormulario.add(p9);

		// 10) panelElitismoInvalido
		JPanel p10 = crearPanelInterno("Parametro Elitismo invalido", null, true);
		panelFormulario.add(p10);

		// 11) panelSeleccion
		formFuncionSeleccion = new JComboBox();
		formFuncionSeleccion.setModel(new DefaultComboBoxModel(new String[] {"Torneo", "Ruleta","Universal","Ranking"}));
		formFuncionSeleccion.setSelectedIndex(0);
		JPanel p11 = crearPanelInterno("Funcion de seleccion", formFuncionSeleccion, false);
		panelFormulario.add(p11);

		// 12) panelAptitud
		formFuncionAptitud = new JComboBox();
		formFuncionAptitud.setModel(new DefaultComboBoxModel(new String[] {"Repetidos","Sumatorio", "Factorial"}));
		formFuncionAptitud.setSelectedIndex(0);
		JPanel p12 = crearPanelInterno("Funcion de aptitud", formFuncionAptitud, false);
		panelFormulario.add(p12);
		
		// 13) panelFuncionCruce
		formFuncionCruce = new JComboBox();
		formFuncionCruce.setModel(new DefaultComboBoxModel(new String[] {"Cruce 1", "Cruce 2","Cruce3"}));
		formFuncionCruce.setSelectedIndex(0);
		JPanel p13 = crearPanelInterno("Funcion de cruce", formFuncionCruce, false);
		panelFormulario.add(p13);
		
		// 14) panelFuncionMutacion
		formFuncionMutacion = new JComboBox();
		formFuncionMutacion.setModel(new DefaultComboBoxModel(new String[] {"Mutacion 1", "Mutacion 2"}));
		formFuncionMutacion.setSelectedIndex(0);
		JPanel p14 = crearPanelInterno("Funcion de mutacion", formFuncionMutacion, false);
		panelFormulario.add(p14);
		
		// 15) boton
		go = new JButton("GO!");
		JPanel p15 = new JPanel();
		p15.add(go);
		panelFormulario.add(p15);
		
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
				int[][] tablero = new int[9][9];
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
				int[][] tablero = GeneradorSudokus.enunciadoSudoku();
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
		JMenuItem itemEjecutar = new JMenuItem("Ejecutar");
		itemEjecutar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.ejecutar();
			}
		});
		menu.add(itemEjecutar);

		menu.add(menuArchivo);
	}

	public void pintaEsto(Cromosoma cromosoma) {
		int[][] fijos = this.getParametros().getFijos(); 
		int[][] tablero = cromosoma.getFenotipo();
		boolean[][] bofijos = new boolean[9][9];
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (fijos[i][j]!=0)
					bofijos[i][j]=true;
		tableroJuego.cambiarTablero(tablero,bofijos);
	}

}
