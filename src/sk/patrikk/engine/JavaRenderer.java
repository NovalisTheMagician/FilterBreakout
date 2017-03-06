package sk.patrikk.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class JavaRenderer 
{
	private SurfaceInfo m_pSInfo;
	private int m_nWidth, m_nHeight;
	
	private BufferedImage m_pBackbuffer;
	private Graphics2D m_pBackCtx;
	
	public JavaRenderer(SurfaceInfo pSInfo)
	{
		m_pSInfo = pSInfo;
		m_nWidth = pSInfo.nWidth;
		m_nHeight = pSInfo.nHeight;
		m_pBackbuffer = new BufferedImage(m_nWidth, m_nHeight, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void Begin()
	{
		m_pBackCtx = m_pBackbuffer.createGraphics();
	}
	
	public void EndAndFlip()
	{
		m_pBackCtx.dispose();
		m_pSInfo.pSurfaceContext.drawImage(m_pBackbuffer, 0, 0, null);
	}
	
	public void ClearScreen(int nColor)
	{
		m_pBackCtx.setColor(new Color(nColor));
		m_pBackCtx.fillRect(0, 0, m_nWidth, m_nHeight);
	}
	
	public Graphics2D R()
	{
		return m_pBackCtx;
	}
	
	public void Flip()
	{
		m_pSInfo.pSurfaceContext.drawImage(m_pBackbuffer, 0, 0, null);
	}
}
