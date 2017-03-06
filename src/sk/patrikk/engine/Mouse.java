package sk.patrikk.engine;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Mouse extends MouseAdapter
{
	public static class MouseState
	{
		public int x, y;
		public boolean buttons[] = new boolean[3];
		
		public boolean IsButtonDown(int button)
		{
			return buttons[button];
		}
	}
	
	private static int x, y;
	private static boolean buttons[] = new boolean[3];
	static { Arrays.fill(buttons, false); }
	
	public static MouseState GetState()
	{
		MouseState ms = new MouseState();
		ms.x = x; ms.y = y;
		System.arraycopy(buttons, 0, ms.buttons, 0, 3);
		return ms;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
		super.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
		super.mouseMoved(e);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		buttons[e.getID()] = true;
		super.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		buttons[e.getID()] = false;
		super.mouseReleased(e);
	}
}
