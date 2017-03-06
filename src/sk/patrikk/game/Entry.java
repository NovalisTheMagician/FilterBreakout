package sk.patrikk.game;

import sk.patrikk.engine.GameLooper;
import sk.patrikk.engine.Window;
import static sk.patrikk.engine.Window.*;

public class Entry 
{
	public static void main(String... args)
	{
		Window w = new 	 Window()
						.SetSize(1280, 660)
						.SetTitle("Breakout")
						.SetFlags(WF_LOCATION_CENTER | WF_INIT_KEYBOARD | WF_INIT_MOUSE);
		GameLooper l = new GameLooper(60);
		Breakout b = new Breakout(w, l);
		
		w.SetWindowCallback(b::WindowCallback);
		l.SetInitFunc(b::Init);
		l.SetUpdateFunc(b::Update);
		l.SetDrawFunc(b::Draw);
		
		int lec = 0;
		w.Open();
		lec = l.StartLoop();
		while(lec > 0)
			lec = l.StartLoop();
		w.Close();
		
		System.exit(0);
	}
}
