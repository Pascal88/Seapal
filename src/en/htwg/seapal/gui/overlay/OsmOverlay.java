package en.htwg.seapal.gui.overlay;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.maps.Overlay;

public class OsmOverlay extends Overlay {
	
	private org.osmdroid.views.MapView MapView;
	
	public OsmOverlay(org.osmdroid.views.MapView MapView) {
		this.MapView = MapView;
	}

	@Override
	public void draw(Canvas canvas, com.google.android.maps.MapView arg1,
			boolean arg2) {
		super.draw(canvas, arg1, arg2);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		
		
		MapView.draw(canvas);
	}

}
