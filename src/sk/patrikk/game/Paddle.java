package sk.patrikk.game;

import static sk.patrikk.game.Resources.TextureID.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;

import sk.patrikk.engine.JavaRenderer;
import sk.patrikk.engine.Mouse;
import sk.patrikk.engine.math.Vector2f;

public class Paddle extends Entity
{
	private Rectangle2D m_rectLeftArea, m_rectMiddleArea, m_rectRightArea;
	
	protected Paddle(float x, float y, Rectangle2D pLevelBounds) 
	{
		super(TID_PADDLERED, 104, 24, pLevelBounds);
		
		m_rectLeftArea = new Rectangle2D.Float(x, y, 0.25f * m_nWidth, m_nHeight);
		m_rectMiddleArea = new Rectangle2D.Float(x + 0.25f * m_nWidth, y, 0.5f * m_nWidth, m_nHeight);
		m_rectRightArea = new Rectangle2D.Float(x + 0.75f * m_nWidth, y, 0.25f * m_nWidth, m_nHeight);
		
		m_vecPosition = new Vector2f(x, y);
	}

	@Override
	public boolean Intersects(Entity pOther, Vector2f vecRes)
	{
		boolean collision = super.Intersects(pOther, vecRes);
		
		if(collision)
		{
			if(m_rectLeftArea.intersects(pOther.m_vecPosition.x, pOther.m_vecPosition.y, pOther.m_nWidth, pOther.m_nHeight))
			{
				float rx = (float)(Math.random()) * -720.0f;
				float ry = (float)(Math.random() + 0.1) * -720.0f;
				pOther.m_vecVelocity.x = rx;
				pOther.m_vecVelocity.y = ry;
			}
			else if(m_rectRightArea.intersects(pOther.m_vecPosition.x, pOther.m_vecPosition.y, pOther.m_nWidth, pOther.m_nHeight))
			{
				float rx = (float)(Math.random()) * 720.0f;
				float ry = (float)(Math.random() + 0.1) * -720.0f;
				pOther.m_vecVelocity.x = rx;
				pOther.m_vecVelocity.y = ry;
			}
			else
				pOther.m_vecVelocity.y = -pOther.m_vecVelocity.y;
		}
		
		return collision;
	}
	
	@Override
	public void Update(float delta) 
	{
		Mouse.MouseState ms = Mouse.GetState();
		m_vecPosition.x = ms.x - m_nWidth / 2;
		
		if(m_vecPosition.x < 0)
			m_vecPosition.x = 0;
		if(m_vecPosition.x > m_pLevelBounds.getWidth() - m_nWidth)
			m_vecPosition.x = (float)(m_pLevelBounds.getWidth() - m_nWidth);
		
		m_rectLeftArea.setRect(m_vecPosition.x, m_vecPosition.y, 0.25f * m_nWidth, m_nHeight);
		m_rectMiddleArea.setRect(m_vecPosition.x + 0.25f * m_nWidth, m_vecPosition.y, 0.5f * m_nWidth, m_nHeight);
		m_rectRightArea.setRect(m_vecPosition.x + 0.75f * m_nWidth, m_vecPosition.y, 0.25f * m_nWidth, m_nHeight);
	}

	@Override
	public void Draw(JavaRenderer r, float alpha, ConvolveOp pFilter) 
	{
		BufferedImage src = Resources.Texture(m_nImageID);
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		pFilter.filter(src, dst);
		r.R().drawImage(dst, (int)m_vecPosition.x, (int)m_vecPosition.y, null);
	}
}
