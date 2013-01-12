package en.htwg.seapal.gui.overlay;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.listener.routeOverlayList.FinishClickListener;
import en.htwg.seapal.gui.listener.routeOverlayList.SaveClickListener;
import en.htwg.seapal.gui.listener.routeOverlayList.UndoClickListener;
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

public class RouteOverlayList extends Overlay {
	
	private List<IOverlay> overlays = null;
	private MapViewActivity activity = null;
	private View view = null;
	private List<Overlay> list = null;
	private GeoInformationController geoInformationController = null;
	private Button save = null;
	private Button undo = null;
	private Button finish = null;
	
	public RouteOverlayList(MapViewActivity activity) {
		this.overlays = new ArrayList<IOverlay>();
		this.activity = activity;
		list = this.activity.getMapView().getOverlays();
		geoInformationController = new GeoInformationController(activity);
		initView();
	}
	
	public RouteOverlayList(MapViewActivity activity, List<IOverlay> list) {
		this.overlays = list;
		this.activity = activity;
		initView();
	}
	
	private void initView() {
		if(view == null) {
			RelativeLayout rel = (RelativeLayout) activity.findViewById(R.id.mapViewDistance);
			LayoutInflater inf = activity.getLayoutInflater();
			view = inf.inflate(R.layout.routemap, null);
			rel.addView(view);
			view.setVisibility(View.INVISIBLE);
			
			save = (Button) view.findViewById(R.id.saveRouteButton);
			save.setClickable(true);
			save.setOnClickListener(new SaveClickListener(activity));
			
			undo = (Button) view.findViewById(R.id.undoRouteButton);
			undo.setClickable(true);
			undo.setOnClickListener(new UndoClickListener(this));
			
			finish = (Button) view.findViewById(R.id.finishRouteButton);
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
				paint.setColor(Color.RED);
				mapView.getProjection().toPixels(((IOverlay) overlays.get(0)).getGeoPoint(), p);
				path.moveTo(p.x, p.y);
				
				for(IOverlay over: overlays.subList(1, overlays.size())) {
					
					mapView.getProjection().toPixels(((IOverlay) over).getGeoPoint(), p);
					path.lineTo(p.x, p.y);
				}
				canvas.drawPath(path, paint);
				for(IOverlay over:overlays) {
					((Overlay) over).draw(canvas, mapView, shadow, when);
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
		if(o instanceof RouteOverlay) {
			initView();
			Boolean b =  overlays.add(o);
			redraw();
			return b;
		}
		return false;
	}
	
	public Boolean add(RouteOverlay o){
		initView();
		Boolean b =  overlays.add(o);
		redraw();
		return b;
	}
	
	public Boolean remove(IOverlay o){
		if(o instanceof RouteOverlay) {
			Boolean b =  overlays.remove((Overlay) o);
			redraw();
			return b;
		}
		return false;
	}
	
	public Boolean remove(RouteOverlay o){
		Boolean b =  overlays.remove(o);
		redraw();
		return b;
	}
	
	public void replace(RouteOverlay orginal, RouteOverlay newer) {
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
				
				TextView editDist = (TextView) view.findViewById(R.id.textRouteNumber);
				editDist.setText("" + numberFormat.format(dist/1852) + " nm");
			} else {
				view.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	public List<IOverlay> getList() {
		return overlays;
	}
	
	public void undo() {
		IOverlay o = overlays.get(overlays.size()-1);
		list.remove(o);
		overlays.remove(o);
		redraw();
	}
}
