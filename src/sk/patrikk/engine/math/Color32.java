package sk.patrikk.engine.math;

public class Color32 
{
	public int r, g, b, a;
	
	public static Color32 FromInt(int nColor)
	{
		int r = (nColor >> 16) & 0xFF;
		int g = (nColor >>  8) & 0xFF;
		int b = (nColor >>  0) & 0xFF;
		int a = (nColor >> 24) & 0xFF;
		return new Color32(r, g, b, a);
	}
	
	public Color32()
	{
		r = g = b = a = 0;
	}
	
	public Color32(int s)
	{
		r = g = b = a = s;
	}
	
	public Color32(int r, int g, int b, int a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color32(Color32 other)
	{
		if(this != other)
		{
			r = other.r;
			g = other.g;
			b = other.b;
			a = other.a;
		}
	}
	
	public Color32 Add(Color32 c, Color32 result)
	{
		if(result != null)
		{
			result.r = r + c.r;
			result.g = g + c.g;
			result.b = b + c.b;
			result.a = a + c.a;
			
			return result;
		}
		
		return new Color32(r + c.r, g + c.g, b + c.b, a + c.a);
	}
	
	public Color32 Sub(Color32 c, Color32 result)
	{
		if(result != null)
		{
			result.r = r - c.r;
			result.g = g - c.g;
			result.b = b - c.b;
			result.a = a - c.a;
			
			return result;
		}
		
		return new Color32(r - c.r, g - c.g, b - c.b, a - c.a);
	}
	
	public Color32 Mul(float s, Color32 result)
	{
		if(result != null)
		{
			result.r = (int)((float)r * s);
			result.g = (int)((float)g * s);
			result.b = (int)((float)b * s);
			result.a = (int)((float)a * s);
			
			return result;
		}
		
		return new Color32((int)((float)r * s), (int)((float)g * s), (int)((float)b * s), (int)((float)a * s));
	}
	
	public Color32 Div(float s, Color32 result)
	{
		if(result != null)
		{
			result.r = (int)((float)r / s);
			result.g = (int)((float)g / s);
			result.b = (int)((float)b / s);
			result.a = (int)((float)a / s);
			
			return result;
		}
		
		return new Color32((int)((float)r / s), (int)((float)g / s), (int)((float)b / s), (int)((float)a / s));
	}
	
	public int ToInt()
	{
		if(r > 255) r = 255; if(r < 0) r = 0;
		if(g > 255) r = 255; if(g < 0) r = 0;
		if(b > 255) r = 255; if(b < 0) r = 0;
		if(a > 255) r = 255; if(a < 0) r = 0;
		
		return ((a << 24) | (r << 16) | (g << 8) | b);
	}
}
