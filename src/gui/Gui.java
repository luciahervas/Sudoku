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
import javax.swing.JOptionPane;
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
	private static final int FRAME_WEIGHT = 800;
	private static final int FRAME_HEIGHT = 510;
	
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
	
	private JLabel incorrecto_poblacion;
	private JLabel incorrecto_generaciones;
	private JLabel incorrecto_cruce;
	private JLabel incorrecto_mutacion;
	private JLabel incorrecto_elitismo;
	/**/
	
	private JMenuBar menu;
	private JMenu menuArchivo;
	private JMenu menuNuevo;
	private JMenuItem itemVacio;
	private JMenuItem itemPredefinido;
	private JMenuItem itemResuelto;
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
	 * @param formulario - OptionDialog: atributo de clase ; null si es un aviso
	 * @return panel creado
	 */
	private JPanel crearPanelInterno(JLabel label, Component form)
	{
		JPanel p = new JPanel();
		if(form != null){
			p.setLayout(new GridLayout(1,0,0,0)); 	
			p.add(label);
			p.add(form);
		} else {
			p.setLayout(new GridLayout(1,2,0,0));
			label.setForeground(Color.RED);
			label.setFont(new Font("Lucida Grande", Font.BOLD, 11));
			label.setVisible(false);
			p.add(label);
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
		JPanel p1 = crearPanelInterno(new JLabel("Poblacion: "), formPoblacion);
		panelFormulario.add(p1);
		
		// 2) panelPoblacionInvalido
		incorrecto_poblacion = new JLabel("Parametro incorrecto");
		JPanel p2 = crearPanelInterno(incorrecto_poblacion, null);
		panelFormulario.add(p2);

		// 3) panelGeneraciones
		formGeneraciones = new JTextField(String.valueOf(Parametros.NUM_GENERACIONES_DEFECTO));
		JPanel p3 = crearPanelInterno(new JLabel("Generaciones: "), formGeneraciones);
		panelFormulario.add(p3);

		// 4) panelGeneracionesInvalido
		incorrecto_generaciones = new JLabel("Parametro incorrecto");
		JPanel p4 = crearPanelInterno(incorrecto_generaciones, null);
		panelFormulario.add(p4);

		// 5) panelCruce
		formCruce = new JTextField(String.valueOf(Parametros.PROB_CRUCE_DEFECTO));
		JPanel p5 = crearPanelInterno(new JLabel("Prob. de cruce"), formCruce);
		panelFormulario.add(p5);

		// 6) panelCruceInvalido
		incorrecto_cruce = new JLabel("Parametro incorrecto");
		JPanel p6 = crearPanelInterno(incorrecto_cruce, null);
		panelFormulario.add(p6);

		// 7) panelMutacion
		formMutacion = new JTextField(String.valueOf(Parametros.PROB_MUTACION_DEFECTO));
		JPanel p7 = crearPanelInterno(new JLabel("Prob. de mutacion"), formMutacion);
		panelFormulario.add(p7);

		// 8) panelMutacionesInvalido
		incorrecto_mutacion = new JLabel("Parametro incorrecto");
		JPanel p8 = crearPanelInterno(incorrecto_mutacion, null);
		panelFormulario.add(p8);

		// 9) panelElitismo
		formElitismo = new JTextField(String.valueOf(Parametros.ELITISMO_DEFECTO)); 
		JPanel p9 = crearPanelInterno(new JLabel("% de elitismo"), formElitismo);
		panelFormulario.add(p9);

		// 10) panelElitismoInvalido
		incorrecto_elitismo = new JLabel("Parametro incorrecto");
		JPanel p10 = crearPanelInterno(incorrecto_elitismo, null);
		panelFormulario.add(p10);

		// 11) panelSeleccion
		formFuncionSeleccion = new JComboBox();
		formFuncionSeleccion.setModel(new DefaultComboBoxModel(new String[] {"Torneo", "Ruleta","Universal","Ranking"}));
		formFuncionSeleccion.setSelectedIndex(0);
		JPanel p11 = crearPanelInterno(new JLabel("F. seleccion"), formFuncionSeleccion);
		panelFormulario.add(p11);

		// 12) panelAptitud
		formFuncionAptitud = new JComboBox();
		formFuncionAptitud.setModel(new DefaultComboBoxModel(new String[] {"Repetidos","Sumatorio", "Factorial"}));
		formFuncionAptitud.setSelectedIndex(0);
		JPanel p12 = crearPanelInterno(new JLabel("F. aptitud"), formFuncionAptitud);
		panelFormulario.add(p12);
		
		// 13) panelFuncionCruce
		formFuncionCruce = new JComboBox();
		formFuncionCruce.setModel(new DefaultComboBoxModel(new String[] {"Cruce 1", "Cruce 2","Cruce3"}));
		formFuncionCruce.setSelectedIndex(0);
		JPanel p13 = crearPanelInterno(new JLabel("F. cruce"), formFuncionCruce);
		panelFormulario.add(p13);
		
		// 14) panelFuncionMutacion
		formFuncionMutacion = new JComboBox();
		formFuncionMutacion.setModel(new DefaultComboBoxModel(new String[] {"Mutacion 1", "Mutacion 2"}));
		formFuncionMutacion.setSelectedIndex(0);
		JPanel p14 = crearPanelInterno(new JLabel("F. mutacion"), formFuncionMutacion);
		panelFormulario.add(p14);
		
		// 15) boton
		go = new JButton("GO!");
		JPanel p15 = new JPanel();
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (validarCampos()){
					c.ejecutar();
				} else {
					JOptionPane.showMessageDialog(null, "Algunos campos no son validos \n");
				}
			}
		});
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
				int[][] tablero;
				double random = Math.random();
				if (random < 0.5){
					tablero = GeneradorSudokus.enunciadoSudoku1();
				} else {
					tablero = GeneradorSudokus.enunciadoSudoku2();
				}
				boolean[][] fijos = new boolean[9][9];
				for (int i=0; i<9; i++){
					for (int j=0; j<9; j++){
						if (tablero[i][j] != 0){
							fijos[i][j]=true;
						}
					}
				}
				tableroJuego.cambiarTablero(tablero,fijos);
			}
		});
		menuNuevo.add(itemPredefinido);
		// 1.3
		itemResuelto = new JMenuItem("Resuelto");
		itemResuelto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[][] tablero = GeneradorSudokus.enunciadoResuleto();
				boolean[][] fijos = new boolean[9][9];
				for (int i=0; i<9; i++)
					for (int j=0; j<9; j++)
							fijos[i][j]=true;
				tableroJuego.cambiarTablero(tablero, fijos);
			}
		});
		menuNuevo.add(itemResuelto);
		// --
		menuArchivo.add(menuNuevo);
		// 2
		itemSalir = new JMenuItem("Salir");
		itemSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		menu.add(menuArchivo);
	}

	public void pintaEsto(Cromosoma cromosoma) {
		int[][] fijos = this.getParametros().getFijos(); 
		int[][] tablero = cromosoma.dameMatriz();
		boolean[][] bofijos = new boolean[9][9];
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				if (fijos[i][j]!=0)
					bofijos[i][j]=true;
		tableroJuego.cambiarTablero(tablero,bofijos);
	}

	private boolean validarCampos()
	{
		boolean camposValidos = true;

		if ( !esNumeroNatural(formPoblacion.getText()) ){
			incorrecto_poblacion.setVisible(true);
			camposValidos = false;
		} else {
			incorrecto_poblacion.setVisible(false);
		}
		if ( !esNumeroNatural(formGeneraciones.getText()) ){
			incorrecto_generaciones.setVisible(true);
			camposValidos = false;
		} else {
			incorrecto_generaciones.setVisible(false);
		}
		if ( !esNumeroReal(formCruce.getText()) ){
			incorrecto_cruce.setVisible(true);
			camposValidos = false;
		} else {
			incorrecto_cruce.setVisible(false);
		}
		if ( !esNumeroReal(formMutacion.getText()) ){
			incorrecto_mutacion.setVisible(true);
			camposValidos = false;
		} else {
			incorrecto_mutacion.setVisible(false);
		}
		if ( !esNumeroReal(formElitismo.getText()) ){
			incorrecto_elitismo.setVisible(true);
			camposValidos = false;
		} else {
			incorrecto_elitismo.setVisible(false);
		}
		
		return camposValidos;
	}

	private boolean esNumeroNatural(String s)
	{
	    for (char c : s.toCharArray()){
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

	/*
	 * Comprueba que es un numero real y con el formato 0.X o 0
	 */
	private boolean esNumeroReal(String s)  
	{  
	  if(s.equals("0")){
		  return true;
	  }

	  char[] cadena = s.toCharArray();
	  // Formato 0.X
	  if ( (cadena[0] != '0') || (cadena[1] != '.') ) {
		  return false;
	  }
	  // digitos
	  for (int i = 2; i < cadena.length; i++){
	        if (!Character.isDigit(cadena[i])) {
	        	return false;
	        }
	  }
	  return true;  
	}
}
