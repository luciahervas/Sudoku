package test;

import static org.junit.Assert.*;

import org.junit.Test;

import controlador.Parametros;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.Cromosoma;

public class AlgoritmoGeneticoTest 
{
	AlgoritmoGenetico g = new AlgoritmoGenetico();
	
	int[][] m1 = 
	{
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
	};
	
	int[][] m2 = 
	{
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		
		{	4,5,6,	1,2,3,	7,8,9, 	},
		{	4,5,6,	1,2,3,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
		
	};
	
	Cromosoma sudoku1 = new Cromosoma(m1);
	Cromosoma sudoku2 = new Cromosoma(m2);
	
	// -------------------------------------

	@Test
	public void testCruceDosPuntos()
	{
		int[][] m1_esperado = 
			{
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				
				{	4,5,6,	1,2,3,	7,8,9, 	},
				{	4,5,6,	1,2,3,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
			};
			
			int[][] m2_esperado = 
			{
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
				{	1,2,3,	4,5,6,	7,8,9, 	},
			};
			
		// ---------------------------------
		
		Cromosoma[] r = new Cromosoma[2];
		r = g.cruceDosPuntos(sudoku1, sudoku2, 3, 6, new Parametros());
		int[][] m1_obtenido = r[0].getFenotipo();
		int[][] m2_obtenido = r[1].getFenotipo();
		
		for (int i=0; i<9; i++){
			assertArrayEquals(m1_esperado[i], m1_obtenido[i]);
			assertArrayEquals(m2_esperado[i], m2_obtenido[i]);
		}
	}  
	
}
