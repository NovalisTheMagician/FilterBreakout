package sk.patrikk.game;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;

import sk.patrikk.engine.JavaRenderer;
import sk.patrikk.engine.math.Vector2f;
import sk.patrikk.game.Resources.TextureID;

public class Brick extends Entity
{
	private boolean m_bDead;
	
	public Brick(float x, float y, TextureID texID) 
	{
		super(texID, 64, 32, null);
		m_vecPosition = new Vector2f(x, y);
		m_bDead = false;
	}

	@Override
	public boolean Intersects(Entity pOther, Vector2f vecRes)
	{
		if(m_bDead)
			return false;
		
		boolean collision = super.Intersects(pOther, vecRes);
		if(collision)
		{
			m_bDead = true;
			
			if(vecRes.x > 0 || vecRes.x < 0)
				pOther.m_vecVelocity.x = -pOther.m_vecVelocity.x;
			else if(vecRes.y > 0 || vecRes.y < 0)
				pOther.m_vecVelocity.y = -pOther.m_vecVelocity.y;
		}
		
		return collision;
	}
	
	@Override
	public void Update(float delta) 
	{}

	@Override
	public void Draw(JavaRenderer r, float alpha, ConvolveOp pFilter) 
	{
		if(m_bDead)
			return;
		
		BufferedImage src = Resources.Texture(m_nImageID);
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		pFilter.filter(src, dst);
		r.R().drawImage(dst, (int)m_vecPosition.x, (int)m_vecPosition.y, null);
	}
}
