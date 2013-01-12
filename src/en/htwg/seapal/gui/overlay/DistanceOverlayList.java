package en.htwg.seapal.gui.overlay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.listener.distanceOverlayList.FinishClickListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class DistanceOverlayList extends Overlay {
	
	private List<IOverlay> overlays = null;
	private MapViewActivity activity = null;
	private View view = null;
	private volatile List<Overlay> list = null;
	private GeoInformationController geoInformationController = null;
	private Button finish = null;
	
	public DistanceOverlayList(MapViewActivity activity) {
		this.overlays = new ArrayList<IOverlay>();
		this.activity = activity;
		list = this.activity.getMapView().getOverlays();
		geoInformationController = new GeoInformationController(activity);
		initView();
	}
	
	public DistanceOverlayList(MapViewActivity activity, List<IOverlay> list) {
		this.overlays = list;
		this.activity = activity;
		initView();
	}
	
	private void initView() {
		if(view == null) {
			RelativeLayout rel = (RelativeLayout) activity.findViewById(R.id.mapViewDistance);
			LayoutInflater inf = activity.getLayoutInflater();
			view = inf.inflate(R.layout.distance, null);
			rel.addView(view);
			view.setVisibility(View.INVISIBLE);
			
			finish = (Button) view.findViewById(R.id.finishDistanceButton);
			finish.setClickable(true);
			finish.setOnClickListener(new FinishClickListener(activity));
		}
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {

			if(!overlays.isEmpty()) {
				Path path = new Path();
				Point p = new Point();
				Paint paint = new Paint();
				
				paint.setStyle(Paint.Style.STROKE);
				paint.setColor(Color.YELLOW);
				mapView.getProjection().toPixels(((IOverlay) overlays.get(0)).getGeoPoint(), p);
				path.moveTo(p.x, p.y);
				
				for(IOverlay over: overlays.subList(1, overlays.size())) {
					
					mapView.getProjection().toPixels(((IOverlay) over).getGeoPoint(), p);
					path.lineTo(p.x, p.y);
				}
				canvas.drawPath(path, paint);
				for(IOverlay over:overlays) {
					((Overlay)over).draw(canvas, mapView, shadow, when);
				}
				setDistanceText(geoInformationController.distance(overlays));
			}
			return super.draw(canvas, mapView, shadow, when);
	}
	
	private void redraw() {
			list.remove(this);
			list.add(this);
	}
	
	public Boolean add(IOverlay o){
		if(o instanceof DistanceOverlay) {
			initView();
			Boolean b =  overlays.add(o);
			redraw();
			return b;
		}
		return false;
	}
	
	public Boolean add(DistanceOverlay o){
		initView();
		Boolean b =  overlays.add(o);
		redraw();
		return b;
	}
	
	public Boolean remove(IOverlay o){
		if(o instanceof DistanceOverlay) {
			Boolean b =  overlays.remove((Overlay) o);
			redraw();
			return b;
		}
		return false;
	}
	
	public Boolean remove(DistanceOverlay o){
		Boolean b =  overlays.remove(o);
		redraw();
		return b;
	}
	
	public void replace(DistanceOverlay orginal, DistanceOverlay newer) {
		int index = overlays.indexOf(orginal);
		overlays.remove(orginal);
		overlays.add(index, newer);
		redraw();
	}
	
	public void finish() {
		if(view != null) {
			RelativeLayout rel = (RelativeLayout) activity.findViewById(R.id.mapViewDistance);
			rel.removeView(view);
			view = null;
		}
		
		if(!overlays.isEmpty()) {
			overlays.clear();
		}
		
		list.remove(this);
	}
	
	public void setDistanceText(float dist) {
		if(view != null) {
			if(dist > 0.0f) {
				if(view.getVisibility() == View.INVISIBLE) view.setVisibility(View.VISIBLE);
				
				NumberFormat numberFormat = new DecimalFormat("000.00");//init Number format
				numberFormat.setRoundingMode(RoundingMode.DOWN);
				
				TextView editDist = (TextView) view.findViewById(R.id.textDistanceNumber);
				editDist.setText("" + numberFormat.format(dist/1852) + " nm");
			} else {
				view.setVisibility(View.INVISIBLE);
			}
		}
	}
}
