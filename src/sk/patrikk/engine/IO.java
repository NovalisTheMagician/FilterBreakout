package sk.patrikk.engine;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sk.patrikk.rawrr.PixelBuffer;

public class IO 
{
	final public static BufferedImage LoadImage(String szPath)
	{	
		try
		{
			return ImageIO.read(new File(szPath));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	final public static int LoadImageRaw(PixelBuffer pBuffer, String szPath)
	{
		if(pBuffer == null)
			return 1;
		
		BufferedImage image;
		try
		{
			image = ImageIO.read(new File(szPath));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return 2;
		}
		
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		
		System.out.printf("%d x %d %s\n", width, height, hasAlphaChannel ? "+ Alpha Channel" : "");
		
		pBuffer.Resize(image.getWidth(), image.getHeight());
		if(hasAlphaChannel) 
		{
			final int pixelLength = 4;
			for(int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength)
			{
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				pBuffer.SetPixel(row * width + col, argb);
				col++;
				if (col == width)
				{
					col = 0;
					row++;
				}
			}
		} 
		else
		{
			final int pixelLength = 3;
			for(int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength)
			{
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				pBuffer.SetPixel(row * width + col, argb);
				col++;
				if(col == width) 
				{
					col = 0;
					row++;
				}
			}
		}
		
		return 0;
	}
}
