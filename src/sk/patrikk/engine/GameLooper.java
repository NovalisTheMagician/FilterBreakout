package sk.patrikk.engine;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameLooper 
{
	public static final int LEC_ERROR = 2;
	public static final int LEC_QUIT = 0;
	public static final int LEC_RETRY = 1;
	
	protected long m_lCurTime, m_lPrevTime;
	protected int m_nDesiredFrameRate;
	protected float m_fDesiredTimeStep;
	protected float m_fAccum;
	
	private boolean m_bLooping;
	private int m_nLooperExitCode;
	
	private Supplier<Boolean> m_pInitFunction;
	private Consumer<Float> m_pUpdateFunction;
	private Consumer<Float> m_pDrawFunction;
	
	public GameLooper(int nDesiredFrameRate)
	{
		m_nDesiredFrameRate = nDesiredFrameRate;
		m_fDesiredTimeStep = 1.0f / m_nDesiredFrameRate;
		m_lCurTime = System.nanoTime();
		m_lPrevTime = m_lCurTime;
		m_fAccum = 0;
		
		m_pInitFunction = null;
		m_pUpdateFunction = null;
		m_pDrawFunction = null;
		
		m_bLooping = false;
		m_nLooperExitCode = LEC_QUIT;
	}
	
	public void SetInitFunc(Supplier<Boolean> pInit)
	{
		m_pInitFunction = pInit;
	}
	
	public void SetUpdateFunc(Consumer<Float> pUpdate)
	{
		m_pUpdateFunction = pUpdate;
	}
	
	public void SetDrawFunc(Consumer<Float> pDraw)
	{
		m_pDrawFunction = pDraw;
	}
	
	private boolean ShouldUpdateNextFrame()
	{
		m_lCurTime = System.nanoTime();
		float delta = (float)((double)(m_lCurTime - m_lPrevTime) / (1000.0 * 1000.0 * 1000.0));
		m_lPrevTime = m_lCurTime;
		
		m_fAccum += delta;
		if(m_fAccum >= m_fDesiredTimeStep)
		{
			m_fAccum -= m_fDesiredTimeStep;
			return true;
		}
		return false;
	}
	
	public void StopLoop(int nReason)
	{
		m_bLooping = false;
		m_nLooperExitCode = nReason;
	}
	
	public int StartLoop()
	{
		if(m_pInitFunction != null)
			if(!m_pInitFunction.get())
				return LEC_ERROR;
		m_bLooping = true;
		while(m_bLooping)
		{	
			if(ShouldUpdateNextFrame())
				if(m_pUpdateFunction != null)
					m_pUpdateFunction.accept(m_fDesiredTimeStep);
			if(m_pDrawFunction != null)
				m_pDrawFunction.accept(1.0f);
		}
		return m_nLooperExitCode;
	}
}
