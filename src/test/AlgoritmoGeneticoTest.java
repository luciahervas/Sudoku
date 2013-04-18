package test;

import static org.junit.Assert.*;

import org.junit.Test;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.Cromosoma;

public class AlgoritmoGeneticoTest 
{
	
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
		
		{	1,2,3,	4,5,6,	7,8,9, 	},
		{	1,2,3,	4,5,6,	7,8,9, 	},
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
				{	1,2,3,	1,2,3,	1,2,3, 	},
				{	4,5,6,	4,5,6,	4,5,6, 	},
				{	7,8,9,	7,8,9,	7,8,9, 	},
				
				{	9,8,7,	9,8,7,	1,2,3, 	},
				{	6,5,4,	6,5,4,	4,5,6, 	},
				{	3,2,1,	3,2,1,	7,8,9, 	},
				
				{	1,2,3,	1,2,3,	1,2,3, 	},
				{	4,5,6,	4,5,6,	4,5,6, 	},
				{	7,8,9,	7,8,9,	7,8,9, 	},
			};
			
			int[][] m2_esperado = 
			{
				{	1,2,3,	1,2,3,	1,2,3, 	},
				{	4,5,6,	4,5,6,	4,5,6, 	},
				{	7,8,9,	7,8,9,	7,8,9, 	},
				
				{	1,2,3,	4,5,6,	1,2,3, 	},
				{	4,5,6,	9,8,7,	4,5,6, 	},
				{	7,8,9,	1,2,3,	7,8,9, 	},
				
				{	1,2,3,	1,2,3,	1,2,3, 	},
				{	4,5,6,	4,5,6,	4,5,6, 	},
				{	7,8,9,	7,8,9,	7,8,9, 	},
			};
			
		Cromosoma s1_esperado = new Cromosoma(m1_esperado);
		Cromosoma s2_esperado = new Cromosoma(m2_esperado);
		
		// ---------------------------------
		
		Cromosoma[] r = new Cromosoma[2];
		r = new AlgoritmoGenetico().cruceDosPuntos(sudoku1, sudoku2, 3, 6, null);
		System.out.println(r[0].getFenotipo());
		
		//for(int i : )
		
	}  
	
}
