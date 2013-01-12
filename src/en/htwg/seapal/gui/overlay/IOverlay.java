package en.htwg.seapal.gui.overlay;

import com.google.android.maps.GeoPoint;

import android.graphics.Bitmap;
import android.graphics.Point;

public interface IOverlay {
	public Bitmap getBitmap();
	public GeoPoint getGeoPoint();
	public void setGeoPoint(Point p);
	public void setGeoPoint(GeoPoint p);
	public void drag();
	public void drop();
}
