package sk.patrikk.rawrr;

import java.util.Arrays;

import sk.patrikk.engine.math.Color32;

public class PixelBuffer 
{
	protected int m_nWidth;
	protected int m_nHeight;
	
	private int m_pBuffer[];
	
	public PixelBuffer()
	{
		m_nWidth = m_nHeight = 0;
		m_pBuffer = new int[0];
	}
	
	public PixelBuffer(int nWidth, int nHeight)
	{
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		
		m_pBuffer = new int[m_nWidth * m_nHeight];
	}
	
	public void Resize(int nWidth, int nHeight)
	{
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		
		int tmp[] = new int[m_nWidth * m_nHeight];
		System.arraycopy(m_pBuffer, 0, tmp, 0, m_pBuffer.length);
		m_pBuffer = tmp;
	}
	
	public void Assign(int pPixels[])
	{
		System.arraycopy(pPixels, 0, m_pBuffer, 0, m_nWidth * m_nHeight);
	}
	
	public void Assign(int nWidth, int nHeight, int pPixels[])
	{
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		System.arraycopy(pPixels, 0, m_pBuffer, 0, m_nWidth * m_nHeight);
	}
	
	public void Clear(int nColor)
	{
		Arrays.fill(m_pBuffer, nColor);
	}
	
	public int GetWidth()
	{
		return m_nWidth;
	}
	
	public int GetHeight()
	{
		return m_nHeight;
	}
	
	public int GetPixel(int x, int y)
	{
		return m_pBuffer[y * m_nWidth + x];
	}
	
	public int GetPixel(int nLoc)
	{
		return m_pBuffer[nLoc];
	}
	
	public void SetPixel(int x, int y, int nColor)
	{
		m_pBuffer[y * m_nWidth + x] = nColor;
	}
	
	public void SetPixel(int nLoc, int nColor)
	{
		m_pBuffer[nLoc] = nColor;
	}
	
	public void Blit(int x, int y, PixelBuffer pPB)
	{
		for(int i = 0; i < pPB.m_nHeight; ++i)
			for(int j = 0; j < pPB.m_nWidth; ++j)
			{
				if(x + j < 0 || y + i < 0) continue;
				if(x + j >= m_nWidth || y + i >= m_nHeight) continue;
				
				int srcClr = pPB.GetPixel(j, i);
				float srcAlpha = ((srcClr >> 24) & 0xFF) / 255.0f;
				int dstClr = Color32.FromInt(srcClr & 0x00FFFFFF)
							.Mul(srcAlpha, null)
							.Add(Color32.FromInt(GetPixel(x + j, y + i) & 0x00FFFFFF)
										.Mul(1 - srcAlpha,  null), null)
							.ToInt() | 0xFF000000;
				SetPixel(x + j, y + i, dstClr);
			}
	}
	
	public int[] ToArray()
	{
		int tmp[] = new int[m_nWidth * m_nHeight];
		System.arraycopy(m_pBuffer, 0, tmp, 0, m_nWidth * m_nHeight);
		return tmp;
	}
}
