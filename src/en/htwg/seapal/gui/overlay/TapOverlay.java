package en.htwg.seapal.gui.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.activity.MapViewActivity;

public class TapOverlay extends Overlay {

	private Point point;
	private MapViewActivity mapViewActivity;
	
	public TapOverlay(MapViewActivity mapViewActivity, Point point) {
		this.point = point;
		this.mapViewActivity = mapViewActivity;
	}
	
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		Paint paint = new Paint();
		
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		
		Bitmap bmp = BitmapFactory.decodeResource(mapViewActivity.getResources(), R.drawable.curlokaiconmini);
		
		canvas.drawBitmap(bmp, point.x, point.y, paint);
		
		return true;		
	}
}
