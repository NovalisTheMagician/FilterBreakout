package sk.patrikk.game;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;

public class Filters 
{
	static public enum FilterType
	{
		FT_NONE,
		FT_BLUR,
		FT_BLUR_HEAVY,
		FT_MOTIONBLUR,
		FT_EDGE_H,
		FT_EDGE_V,
		FT_EDGE_45,
		FT_EDGE_ALL,
		FT_SHARPEN1,
		FT_SHARPEN2,
		FT_SHARPEN3,
		FT_EMBOS,
		FT_EMBOS_EX,
		FT_COUNT
	};
	
	private static float[][] matrices = new float[][]
			{
				{
					0, 0, 0,
					0, 1, 0,
					0, 0, 0
				},
				
				{
					0, 0.2f, 0,
					0.2f, 0.2f, 0.2f,
					0, 0.2f, 0
				},
				
				{
					0, 0, 1.0f/13.0f, 0, 0,
					0, 1.0f/13.0f, 1.0f/13.0f, 1.0f/13.0f, 0,
					1.0f/13.0f, 1.0f/13.0f, 1.0f/13.0f, 1.0f/13.0f, 1.0f/13.0f,
					0, 1.0f/13.0f, 1.0f/13.0f, 1.0f/13.0f, 0,
					0, 0, 1.0f/13.0f, 0, 0
				},
				
				{
					0.11111f, 0, 0, 0, 0, 0, 0, 0,
					0, 0.11111f, 0, 0, 0, 0, 0, 0,
					0, 0, 0.11111f, 0, 0, 0, 0, 0,
					0, 0, 0, 0.11111f, 0, 0, 0, 0,
					0, 0, 0, 0, 0.11111f, 0, 0, 0,
					0, 0, 0, 0, 0, 0.11111f, 0, 0,
					0, 0, 0, 0, 0, 0, 0.11111f, 0,
					0, 0, 0, 0, 0, 0, 0, 0.11111f, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0.11111f
				},
				
				{
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0,
					-1, -1, 2, 0, 0,
					0, 0, 0, 0, 0,
					0, 0, 0, 0, 0
				},
				
				{
					0, 0, -1, 0, 0,
					0, 0, -1, 0, 0,
					0, 0, 4, 0, 0,
					0, 0, -1, 0, 0,
					0, 0, -1, 0, 0
				},
				
				{
					-1, 0, 0, 0, 0,
					0, -2, 0, 0, 0,
					0, 0, 6, 0, 0,
					0, 0, 0, -2, 0,
					0, 0, 0, 0, -1
				},
				
				{
					-1, -1, -1,
					-1,  8, -1,
					-1, -1, -1
				},
				
				{
					-1, -1, -1,
					-1,  9, -1,
					-1, -1, -1
				},
				
				{
					-1, -1, -1, -1, -1,
					-1, 2, 2, 2, -1,
					-1, 2, 8, 2, -1,
					-1, 2, 2, 2, -1,
					-1, -1, -1, -1, -1
				},
				
				{
					1, 1, 1,
					1, -7, 1,
					1, 1, 1
				},
				
				{
					-1, -1, 0,
					-1, 0, 1,
					0, 1, 1
				},
				
				{
					-1, -1, -1, -1,  0,
					-1, -1, -1,  0,  1,
					-1, -1,  0,  1,  1,
					-1,  0,  1,  1,  1,
					 0,  1,  1,  1,  1
				},
			};
	
	private static HashMap<FilterType, ConvolveOp> pFilterMap;
	
	static
	{
		pFilterMap = new HashMap<>(FilterType.FT_COUNT.ordinal());
	}
	
	public static void InitializeAll()
	{
		int i = 0;
		Kernel kernel;
		do
		{
			float matrix[] = matrices[i];
			int cnt = (int)Math.sqrt(matrix.length);
			kernel = new Kernel(cnt, cnt, matrix);
			pFilterMap.put(FilterType.values()[i], new ConvolveOp(kernel));
		}
		while(++i < FilterType.FT_COUNT.ordinal());
	}
	
	public static ConvolveOp Get(FilterType ft)
	{
		return pFilterMap.get(ft);
	}
}
