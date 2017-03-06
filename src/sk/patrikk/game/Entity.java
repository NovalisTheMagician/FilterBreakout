package sk.patrikk.game;

import java.awt.geom.Rectangle2D;
import java.awt.image.ConvolveOp;

import sk.patrikk.engine.JavaRenderer;
import sk.patrikk.engine.math.Vector2f;

public abstract class Entity 
{
	protected Vector2f m_vecPosition;
	protected Vector2f m_vecVelocity;
	
	protected int m_nWidth, m_nHeight;
	
	protected Resources.TextureID m_nImageID;
	
	protected Rectangle2D m_pLevelBounds;
	
	protected Entity(Resources.TextureID nTID, int nWidth, int nHeight, Rectangle2D pLevelBounds)
	{
		m_nImageID = nTID;
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		m_pLevelBounds = pLevelBounds;
	}
	
	public void SetImage(Resources.TextureID nTID)
	{
		m_nImageID = nTID;
	}
	
	public void SetWidth(int nWidth)
	{
		m_nWidth = nWidth;
	}
	
	public void SetHeight(int nHeight)
	{
		m_nHeight = nHeight;
	}
	
	public boolean Intersects(Entity pOther, Vector2f vecRes)
	{
		if( m_vecPosition.x > pOther.m_vecPosition.x + pOther.m_nWidth 	|| 
			m_vecPosition.x + m_nWidth < pOther.m_vecPosition.x 		||
			m_vecPosition.y > pOther.m_vecPosition.y + pOther.m_nHeight	||
			m_vecPosition.y + m_nHeight < pOther.m_vecPosition.y)
			return false;
		
		if(vecRes != null)
		{
			float res = Float.MIN_VALUE;
			float tx = Math.abs(m_vecPosition.x - (pOther.m_vecPosition.x + pOther.m_nWidth));
			tx = Math.min(tx, Math.abs((m_vecPosition.x + m_nWidth) - pOther.m_vecPosition.x));
			
			float ty = Math.abs(m_vecPosition.y - (pOther.m_vecPosition.y + pOther.m_nHeight));
			ty = Math.min(ty, Math.abs((m_vecPosition.y + m_nHeight) - pOther.m_vecPosition.y));
			
			res = Math.min(tx, ty);
			
			if(tx < ty)
				vecRes.x = res;
			else
				vecRes.y = res;
		}
		
		return true;
	}
	
	public abstract void Update(float delta);
	public abstract void Draw(JavaRenderer R, float alpha, ConvolveOp pFilter);
}
