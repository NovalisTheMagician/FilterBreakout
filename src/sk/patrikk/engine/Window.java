package sk.patrikk.engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Window 
{
	static public enum WindowEvents
	{
		WE_CLOSE,
		WE_LOST_FOCUS,
		WE_GAINED_FOCUS
	};
	
	public static final int WF_LOCATION_CENTER 		= 1 << 0;
	public static final int WF_LOCATION_USER 		= 1 << 1;
	public static final int WF_LOCATION_PLATFORM	= 1 << 2;
	public static final int WF_STYLE_BORDERLESS		= 1 << 3;
	public static final int WF_STYLE_RESIZEABLE		= 1 << 4;
	public static final int WF_INIT_KEYBOARD		= 1 << 5;
	public static final int WF_INIT_MOUSE			= 1 << 6;
	
	private String 	m_szTitle;
	protected int 	m_nWidth;
	protected int 	m_nHeight;
	
	protected int 	m_nX;
	protected int 	m_nY;
	
	protected boolean m_bHasFocus;
	protected boolean m_bIsOpen;
	
	private int m_nSetFlags;
	
	protected JFrame m_pWnd;
	protected JPanel m_pSurface;
	
	private Consumer<WindowEvents> m_pWindowCallback;
	
	public Window()
	{
		m_szTitle = "By Patrik";
		m_nWidth = 800;
		m_nHeight = 480;
		
		m_nX = m_nY = 0;
		
		m_nSetFlags = 0;
		
		m_bHasFocus = false;
		m_bIsOpen = false;
		
		m_pWnd = new JFrame();
		m_pSurface = new JPanel();
	}
	
	public Window(String szTitle, int nWidth, int nHeight)
	{
		m_szTitle = szTitle;
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		
		m_nX = m_nY = 0;
		
		m_nSetFlags = 0;
		
		m_bHasFocus = false;
		m_bIsOpen = false;
		
		m_pWnd = new JFrame();
		m_pSurface = new JPanel();
	}
	
	public SurfaceInfo GetSurfaceInfo()
	{
		SurfaceInfo si = new SurfaceInfo();
		si.pOwner = m_pWnd;
		si.nWidth = m_nWidth;
		si.nHeight = m_nHeight;
		si.pSurfaceContext = (Graphics2D)m_pSurface.getGraphics();
		return si;
	}
	
	public Window SetSize(int nWidth, int nHeight)
	{
		m_nWidth = nWidth;
		m_nHeight = nHeight;
		return this;
	}
	
	public Window SetLocation(int x, int y)
	{
		m_nX = x;
		m_nY = y;
		return this;
	}
	
	public Window SetTitle(String szTitle)
	{
		m_szTitle = szTitle;
		return this;
	}
	
	public Window SetFlags(int nFlags)
	{
		m_nSetFlags = nFlags;
		return this;
	}
	
	public void SetWindowCallback(Consumer<WindowEvents> pCallback)
	{
		m_pWindowCallback = pCallback;
	}
	
	public boolean HasFocus()
	{
		return m_bHasFocus;
	}
	
	public boolean IsOpen()
	{
		return m_bIsOpen;
	}
	
	public int GetWidth()
	{
		return m_nWidth;
	}
	
	public int GetHeight()
	{
		return m_nHeight;
	}
	
	public int Open()
	{
		m_pWnd.setTitle(m_szTitle);
		m_pWnd.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		m_pSurface.setPreferredSize(new Dimension(m_nWidth, m_nHeight));
		m_pSurface.setBackground(new Color(0x0));
		
		Container cp = m_pWnd.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(m_pSurface);
		
		if((m_nSetFlags & WF_STYLE_RESIZEABLE) == 0)
			m_pWnd.setResizable(false);
		
		if((m_nSetFlags & WF_STYLE_BORDERLESS) > 0)
			m_pWnd.setUndecorated(true);
		
		m_pWnd.pack();
		
		if((m_nSetFlags & WF_LOCATION_CENTER) > 0)
			m_pWnd.setLocationRelativeTo(null);
		else if((m_nSetFlags & WF_LOCATION_USER) > 0)
			m_pWnd.setLocation(m_nX, m_nY);
		else if((m_nSetFlags & WF_LOCATION_PLATFORM) > 0)
			m_pWnd.setLocationByPlatform(true);
		
		m_pWnd.addWindowListener(new WindowCallbacker());
		
		if((m_nSetFlags & WF_INIT_KEYBOARD) > 0)
			m_pWnd.addKeyListener(new Keyboard());
		
		if((m_nSetFlags & WF_INIT_MOUSE) > 0)
		{
			Mouse m = new Mouse();
			m_pWnd.addMouseListener(m);
			m_pWnd.addMouseMotionListener(m);
		}
		
		m_pWnd.setVisible(true);
		
		if(!m_pWnd.isActive())
			return 1;
		
		m_bIsOpen = true;
		m_bHasFocus = true;
		
		return 0;
	}
	
	public void Close()
	{
		if(m_bIsOpen)
		{
			m_pWnd.setVisible(false);
			m_pWnd.dispose();
			m_bIsOpen = false;
			m_bHasFocus = false;
		}
	}
	
	protected class WindowCallbacker extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e) 
		{
			if(m_pWindowCallback != null)
				m_pWindowCallback.accept(WindowEvents.WE_CLOSE);
			Close();
			super.windowClosed(e);
		}

		@Override
		public void windowGainedFocus(WindowEvent e) 
		{
			if(m_pWindowCallback != null)
				m_pWindowCallback.accept(WindowEvents.WE_GAINED_FOCUS);
			m_bHasFocus = true;
			super.windowGainedFocus(e);
		}

		@Override
		public void windowLostFocus(WindowEvent e) 
		{
			if(m_pWindowCallback != null)
				m_pWindowCallback.accept(WindowEvents.WE_LOST_FOCUS);
			m_bHasFocus = false;
			super.windowLostFocus(e);
		}
	}
}
