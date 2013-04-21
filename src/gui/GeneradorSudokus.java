package gui;

public class GeneradorSudokus 
{
	public static int numeroEnunciados = 2;
	
	public static int[][] enunciadoSudoku1()
	{
		int[] fa = {0,6,0,0,0,8,2,0,0};
		int[] fb = {1,0,4,3,0,5,0,0,0};
		int[] fc = {0,5,0,6,0,0,0,0,1};
		
		int[] fd = {8,0,0,0,0,6,7,0,0};
		int[] fe = {4,0,7,0,0,0,9,0,1};
		int[] ff = {0,0,6,3,0,0,0,0,4};
		
		int[] fg = {5,0,0,0,0,7,0,4,0};
		int[] fh = {0,0,0,2,0,6,5,0,8};
		int[] fi = {0,0,2,9,0,0,0,7,0};

		int[][] r = {fa,fb,fc,fd,fe,ff,fg,fh,fi};
		return r;
	}
	
	public static int[][] enunciadoSudoku2()
	{
		int[] fa= {0,0,7,1,9,0,0,2,8};
		int[] fb= {0,0,6,0,0,4,7,0,0};
		int[] fc= {0,0,8,0,0,5,4,0,0};
		
		int[] fd= {3,0,0,0,0,0,0,7,0};
		int[] fe= {4,0,1,0,0,8,0,0,0};
		int[] ff= {0,0,6,9,1,0,0,2,0};
		
		int[] fg= {0,0,0,0,0,0,2,0,0};
		int[] fh= {0,0,0,1,0,0,0,9,0};
		int[] fi= {3,5,2,0,0,0,0,6,0};
		
		int[][] r = {fa,fb,fc,fd,fe,ff,fg,fh,fi};
		return r;
	}
	
	public static int[][] enunciadoResuleto()
	{
		int[] a = {5,4,7,1,9,3,6,2,8};
		int[] b = {9,1,6,2,8,4,7,5,3};
		int[] c = {2,3,8,6,7,5,4,9,1};
		int[] d = {3,5,9,4,6,2,8,7,1};
		int[] e = {4,2,1,5,7,8,6,3,9};
		int[] f = {7,8,6,9,1,3,5,2,4};
		int[] g = {9,1,6,7,3,5,2,8,4};
		int[] h = {8,4,7,1,6,2,3,9,5};
		int[] i = {3,5,2,8,4,9,1,6,7};
		
		int[][] r = {a,b,c,d,e,f,g,h,i};
		return r;
	}
	
	/*
	public static int[][] enunciadoSudokuCaca()
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
			{	1,2,3,	4,5,6,	7,8,9, 	}
		};
		return m1;
	}
	*/
}
