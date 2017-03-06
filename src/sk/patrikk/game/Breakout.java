package sk.patrikk.game;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;

import javax.swing.JOptionPane;

import sk.patrikk.engine.GameLooper;
import sk.patrikk.engine.JavaRenderer;
import sk.patrikk.engine.Keyboard;
import sk.patrikk.engine.Window;
import sk.patrikk.engine.math.Vector2f;
import sk.patrikk.game.Filters.FilterType;
import sk.patrikk.game.Resources.TextureID;
import static java.awt.event.KeyEvent.*;

public class Breakout
{
	private Window m_pWindow;
	private GameLooper m_pLooper;
	
	private JavaRenderer m_pRenderer;
	
	private Paddle m_pPaddle;
	private Ball m_pBall;

	private ConvolveOp m_pActiveFilter;
	
	private Brick m_pBricks[][];
	
	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 20;
	
	public Breakout(Window pWindow, GameLooper pLooper)
	{
		m_pWindow = pWindow;
		m_pLooper = pLooper;
	}
	
	private void CreateAndPlaceBricks()
	{
		m_pBricks = new Brick[NUM_ROWS][NUM_COLS];
		for(int y = 0; y < NUM_ROWS; ++y)
			for(int x = 0; x < NUM_COLS; ++x)
			{
				int r = (int)(Math.random() * 6 + 2);
				m_pBricks[y][x] = new Brick(x * 64, y * 32, TextureID.values()[r]);
			}
	}
	
	public boolean Init()
	{
		int err = Resources.InitializeAll();
		if(err > 0)
		{
			JOptionPane.showMessageDialog(null, "Could not find " + err + " asset file(s)", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		Filters.InitializeAll();
		m_pActiveFilter = Filters.Get(FilterType.FT_NONE);
		
		m_pRenderer = new JavaRenderer(m_pWindow.GetSurfaceInfo());
		
		CreateAndPlaceBricks();
		
		Rectangle2D lvlRect = new Rectangle2D.Float(0, 0, m_pWindow.GetWidth(), m_pWindow.GetHeight());
		
		m_pPaddle = new Paddle(m_pWindow.GetWidth() / 2 - 64, m_pWindow.GetHeight() - 22, lvlRect);
		m_pBall = new Ball(lvlRect);
		
		return true;
	}
	
	Keyboard.KeyboardState oldKB;
	
	public void Update(float delta)
	{
		Keyboard.KeyboardState kb = Keyboard.GetState();
		
		if(kb.IsKeyDown(VK_ESCAPE))
			m_pLooper.StopLoop(GameLooper.LEC_QUIT);
		
		if(kb.IsKeyDown('1') && !oldKB.IsKeyDown('1'))
			m_pActiveFilter = Filters.Get(FilterType.FT_NONE);
		if(kb.IsKeyDown('2') && !oldKB.IsKeyDown('2'))
			m_pActiveFilter = Filters.Get(FilterType.FT_SHARPEN1);
		if(kb.IsKeyDown('3') && !oldKB.IsKeyDown('3'))
			m_pActiveFilter = Filters.Get(FilterType.FT_MOTIONBLUR);
		if(kb.IsKeyDown('4') && !oldKB.IsKeyDown('4'))
			m_pActiveFilter = Filters.Get(FilterType.FT_EDGE_ALL);
		if(kb.IsKeyDown('5') && !oldKB.IsKeyDown('5'))
			m_pActiveFilter = Filters.Get(FilterType.FT_EDGE_V);
		if(kb.IsKeyDown('6') && !oldKB.IsKeyDown('6'))
			m_pActiveFilter = Filters.Get(FilterType.FT_BLUR_HEAVY);
		if(kb.IsKeyDown('7') && !oldKB.IsKeyDown('7'))
			m_pActiveFilter = Filters.Get(FilterType.FT_SHARPEN3);
		if(kb.IsKeyDown('8') && !oldKB.IsKeyDown('8'))
			m_pActiveFilter = Filters.Get(FilterType.FT_EMBOS);
		if(kb.IsKeyDown('9') && !oldKB.IsKeyDown('9'))
			m_pActiveFilter = Filters.Get(FilterType.FT_EMBOS_EX);
		if(kb.IsKeyDown('0') && !oldKB.IsKeyDown('0'))
			m_pActiveFilter = Filters.Get(FilterType.FT_SHARPEN2);
		
		m_pPaddle.Intersects(m_pBall, null);
		
		Vector2f res = new Vector2f();
		for(int y = 0; y < NUM_ROWS; ++y)
			for(int x = 0; x < NUM_COLS; ++x)
			{
				res.x = 0; res.y = 0;
				m_pBricks[y][x].Intersects(m_pBall, res);
			}
		
		m_pPaddle.Update(delta);
		m_pBall.Update(delta);
		
		if(m_pBall.IsOutOfBounds())
		{
			int ret = JOptionPane.showConfirmDialog(null, "Wanna try again?", "You died!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if(ret == JOptionPane.YES_OPTION)
				m_pLooper.StopLoop(GameLooper.LEC_RETRY);
			else
				m_pLooper.StopLoop(GameLooper.LEC_QUIT);
		}
		
		oldKB = kb;
	}
	
	public void Draw(float alpha)
	{
		m_pRenderer.Begin();
		m_pRenderer.ClearScreen(0xFFFFFF);
		
		BufferedImage src = Resources.Texture(TextureID.TID_BCKGR3);
		BufferedImage dst = m_pActiveFilter.filter(src, null);
		m_pRenderer.R().drawImage(dst, 0, 0, m_pWindow.GetWidth(), m_pWindow.GetHeight(), 0, 0, dst.getWidth(), dst.getHeight(), null);
		
		m_pPaddle.Draw(m_pRenderer, alpha, m_pActiveFilter);
		m_pBall.Draw(m_pRenderer, alpha, m_pActiveFilter);
		
		for(Brick ba[] : m_pBricks)
			for(Brick b : ba)
				b.Draw(m_pRenderer, alpha, m_pActiveFilter);
		
		m_pRenderer.EndAndFlip();
	}
	
	public void WindowCallback(Window.WindowEvents e)
	{
		switch(e)
		{
		case WE_CLOSE: m_pLooper.StopLoop(GameLooper.LEC_QUIT); break;
		case WE_GAINED_FOCUS: break;
		case WE_LOST_FOCUS: break;
		}
	}
}
