package sk.patrikk.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources 
{
	static public enum TextureID
	{
		TID_PADDLERED,
		
		TID_BALLGREY,
		
		TID_BRICK1,
		TID_BRICK2,
		TID_BRICK3,
		TID_BRICK4,
		TID_BRICK5,
		TID_BRICK6,
		
		TID_BCKGR1,
		TID_BCKGR2,
		TID_BCKGR3,
		TID_BCKGR4,
		
		TID_COUNT
	};
	
	private static BufferedImage pTextures[];
	
	static 
	{
		pTextures = new BufferedImage[TextureID.TID_COUNT.ordinal()];
	}
	
	public static int InitializeAll()
	{
		int numErrors = 0;
		
		for(int i = 0; i < TextureID.TID_COUNT.ordinal(); ++i)
		{
			String name = TextureID.values()[i].name().toLowerCase();
			name = name.substring(name.indexOf('_', 0) + 1) + ".png";
			try
			{
				pTextures[i] = ImageIO.read(new File("assets/" + name));
			}
			catch(IOException e)
			{
				//e.printStackTrace();
				numErrors++;
			}
		}
		
		return numErrors;
	}
	
	public static BufferedImage Texture(TextureID nTID)
	{
		return pTextures[nTID.ordinal()];
	}
}
