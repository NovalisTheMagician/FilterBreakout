package sk.patrikk.game;

import static sk.patrikk.game.Resources.TextureID.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;

import sk.patrikk.engine.JavaRenderer;
import sk.patrikk.engine.math.Vector2f;

public class Ball extends Entity
{	
	protected Ball(Rectangle2D pLevelBounds) 
	{
		super(TID_BALLGREY, 22, 22, pLevelBounds);
		
		m_vecPosition = new Vector2f((float)pLevelBounds.getWidth() / 2, (float)pLevelBounds.getHeight() / 2);
		m_vecVelocity = new Vector2f(512, 512);
	}

	public boolean IsOutOfBounds()
	{
		return !m_pLevelBounds.intersects(m_vecPosition.x, m_vecPosition.y, m_nWidth, m_nHeight);
	}
	
	@Override
	public void Update(float delta) 
	{
		if(m_vecPosition.x >= (float)m_pLevelBounds.getWidth() - m_nWidth / 2 || m_vecPosition.x < 0)
			m_vecVelocity.x *= -1;
		if(m_vecPosition.y < 0)
			m_vecVelocity.y *= -1;
		
		m_vecPosition.Add(m_vecVelocity.Mul(delta, null), m_vecPosition);
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
