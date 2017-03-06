package sk.patrikk.engine.math;

public class Vector2f 
{
	public float x, y;
	
	public Vector2f()
	{
		x = y = 0.0f;
	}
	
	public Vector2f(float s)
	{
		x = y = s;
	}
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f other)
	{
		if(this != other)
		{
			x = other.x;
			y = other.y;
		}
	}
	
	public Vector2f Add(Vector2f v, Vector2f result)
	{
		if(result != null)
		{
			result.x = x + v.x;
			result.y = y + v.y;
			return result;
		}
		return new Vector2f(x + v.x, y + v.y);
	}
	
	public Vector2f Sub(Vector2f v, Vector2f result)
	{
		if(result != null)
		{
			result.x = x - v.x;
			result.y = y - v.y;
			return result;
		}
		return new Vector2f(x - v.x, y - v.y);
	}
	
	public Vector2f Mul(float s, Vector2f result)
	{
		if(result != null)
		{
			result.x = x * s;
			result.y = y * s;
			return result;
		}
		return new Vector2f(x * s, y * s);
	}
	
	public Vector2f Div(float s, Vector2f result)
	{
		if(result != null)
		{
			result.x = x / s;
			result.y = y / s;
			return result;
		}
		return new Vector2f(x / s, y / s);
	}
	
	public float Dot(Vector2f v)
	{
		return x * v.x + y * v.y;
	}
	
	public Vector2f Perp(Vector2f v, Vector2f result)
	{
		if(result != null)
		{
			result.x = y;
			result.y = -x;
		}
		return new Vector2f(y, -x);
	}
	
	public float Length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public Vector2f Normalize(Vector2f result)
	{
		float l = Length();
		if(result != null)
		{
			result.x = x / l;
			result.y = y / l;
			return result;
		}
		return new Vector2f(x / l, y / l);
	}
}
