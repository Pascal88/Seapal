package en.htwg.seapal.gui.overlay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;

public class MyGoogleLocationOverlay extends Overlay {
	
	private GeoPoint geoPoint = null;
	private Resources resources = null;
	
	public MyGoogleLocationOverlay(Resources resources, GeoPoint geoPoint) {
		this.resources = resources;
		this.geoPoint = geoPoint;
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		Paint paint = new Paint();
		
		super.draw(canvas, mapView, shadow);
		// Converts lat/lng-Point to OUR coordinates on the screen.
		Point myScreenCoords = new Point();
		mapView.getProjection().toPixels((GeoPoint) geoPoint, myScreenCoords);
		
		paint.setStrokeWidth(1);
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.STROKE);
		
		Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.curlokaiconmini);
		
		canvas.drawBitmap(bmp, myScreenCoords.x - (bmp.getWidth()/2), myScreenCoords.y - (bmp.getHeight()/2), paint);
		return true;
	}
}
