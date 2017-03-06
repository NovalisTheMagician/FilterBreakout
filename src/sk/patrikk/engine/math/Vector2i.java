package sk.patrikk.engine.math;

public class Vector2i
{
	public int x, y;
	
	public Vector2i()
	{
		x = y = 0;
	}
	
	public Vector2i(int s)
	{
		x = y = s;
	}
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2i(Vector2i other)
	{
		if(this != other)
		{
			x = other.x;
			y = other.y;
		}
	}
	
	public Vector2i Add(Vector2i v, Vector2i result)
	{
		if(result != null)
		{
			result.x = x + v.x;
			result.y = y + v.y;
			return result;
		}
		return new Vector2i(x + v.x, y + v.y);
	}
	
	public Vector2i Sub(Vector2i v, Vector2i result)
	{
		if(result != null)
		{
			result.x = x - v.x;
			result.y = y - v.y;
			return result;
		}
		return new Vector2i(x - v.x, y - v.y);
	}
	
	public Vector2i Mul(float s, Vector2i result)
	{
		if(result != null)
		{
			result.x = (int)(x * s);
			result.y = (int)(y * s);
			return result;
		}
		return new Vector2i((int)(x * s), (int)(y * s));
	}
	
	public Vector2i Div(float s, Vector2i result)
	{
		if(result != null)
		{
			result.x = (int)(x / s);
			result.y = (int)(y / s);
			return result;
		}
		return new Vector2i((int)(x / s), (int)(y / s));
	}
	
	public float Dot(Vector2i v)
	{
		return x * v.x + y * v.y;
	}
	
	public Vector2i Perp(Vector2i v, Vector2i result)
	{
		if(result != null)
		{
			result.x = y;
			result.y = -x;
		}
		return new Vector2i(y, -x);
	}
	
	public float Length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public Vector2i Normalize(Vector2i result)
	{
		float l = Length();
		if(result != null)
		{
			result.x = (int)(x / l);
			result.y = (int)(y / l);
			return result;
		}
		return new Vector2i(x / (int)l, y / (int)l);
	}
}

