package sk.patrikk.rawrr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import sk.patrikk.engine.SurfaceInfo;

public class RawRenderer 
{
	private SurfaceInfo m_pSInfo;
	private PixelBuffer m_pBackbuffer;
	
	private BufferedImage m_pB;
	
	public RawRenderer(SurfaceInfo pSInfo)
	{
		m_pSInfo = pSInfo;
		m_pBackbuffer = new PixelBuffer(m_pSInfo.nWidth, m_pSInfo.nHeight);
		m_pB = new BufferedImage(m_pSInfo.nWidth, m_pSInfo.nHeight, BufferedImage.TYPE_INT_ARGB);
	}
	
	public void ClearScreen(int nColor)
	{
		m_pBackbuffer.Clear(nColor);
	}
	
	public PixelBuffer R()
	{
		return m_pBackbuffer;
	}
	
	private void ConvertBuffer(PixelBuffer pBuff, Graphics2D context)
	{
		Image img = m_pSInfo.pOwner.createImage(new MemoryImageSource(m_pSInfo.nWidth, m_pSInfo.nHeight, pBuff.ToArray(), 0, m_pSInfo.nWidth));
		context.drawImage(img, 0, 0, null);
	}
	
	public void Flip()
	{
		Graphics2D bi = m_pB.createGraphics();
		ConvertBuffer(m_pBackbuffer, bi);
		bi.dispose();
		m_pSInfo.pSurfaceContext.drawImage(m_pB, 0, 0, null);
	}
}
