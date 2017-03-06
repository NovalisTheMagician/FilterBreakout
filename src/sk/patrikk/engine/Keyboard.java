package sk.patrikk.engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Keyboard extends KeyAdapter
{
	public static class KeyboardState
	{
		public boolean keys[] = new boolean[256];
		
		public boolean IsKeyDown(int key)
		{
			return keys[key];
		}
	}

	private static boolean keys[] = new boolean[256];
	static { Arrays.fill(keys, false); }
	
	public static KeyboardState GetState()
	{
		KeyboardState kb = new KeyboardState();
		System.arraycopy(keys, 0, kb.keys, 0, 256);
		return kb;
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
		super.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
		super.keyReleased(e);
	}
}
