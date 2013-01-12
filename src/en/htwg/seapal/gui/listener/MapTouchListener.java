package en.htwg.seapal.gui.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MapTouchListener implements OnTouchListener {

	private GestureDetector mapActivity;
	
	public MapTouchListener(GestureDetector mapActivity) {
		this.mapActivity = mapActivity;
	}
	
	@Override
	public boolean onTouch(View arg0, MotionEvent event) {

		return mapActivity.onTouchEvent(event);		
	}

}
