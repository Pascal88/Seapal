package en.htwg.seapal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.text.format.Time;
import android.widget.Spinner;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.gui.adapter.RouteSpinnerAdapter;
import en.htwg.seapal.gui.fragment.RoutePointListFragment;
import en.htwg.seapal.gui.overlay.IOverlay;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.models.Route;
import en.htwg.seapal.model.models.RoutePoint;
import en.htwg.seapal.model.tables.TableRoute;
import en.htwg.seapal.model.tables.TableRoutePoint;

public class RouteController {
	
	private Activity activity = null;
	private static TableRoutePoint tableRoutePoint ;
	
	public RouteController(Activity activity) {
		this.activity = activity;
		if(tableRoutePoint == null) tableRoutePoint = new TableRoutePoint(activity);
	}

	public Boolean addRoute(List<IOverlay> list) {
		Time now = new Time();
		now.setToNow();
		
		Route route = new Route(activity, activity.getResources().getString(R.string.routeText), new Date(now.toMillis(false)));
		
		List<RoutePoint> routePointlist = new ArrayList<RoutePoint>();
		int count = 0;
		for(IOverlay over: list) {
			routePointlist.add(new RoutePoint(route.ID, "" + activity.getResources().getString(R.string.mark) + count, over.getGeoPoint().getLatitudeE6(), over.getGeoPoint().getLongitudeE6()));
			count++;
		}
		
		route.setPoints(routePointlist);
		
		TableRoute tableRoute = new TableRoute(activity);
		return tableRoute.addRoute(route);
	}
	
	public RouteSpinnerAdapter getRouteSpinnerAdapter() {
		TableRoute tableRoute = new TableRoute(activity);
		return new RouteSpinnerAdapter(activity, tableRoute.selectAll());
	}
	
	public void swap(Cursor one, String s){
		//don´t touch this it works just fine
		int pos = one.getPosition();
		one.moveToFirst();
		RoutePoint[] rps = new RoutePoint[one.getCount()];
		for(int i = 0; i < one.getCount(); i++) {
			rps[i] = new RoutePoint(one);
			one.moveToNext();
		}
		RoutePoint rp1;
		RoutePoint rp2;
		
		if(s.equals("up")) {
			rp1 = rps[pos-1];
			if((pos-1) == 0) {
				rp2 = rps[rps.length-1];
				((RouteActivity) activity).setSelected(rps.length+1);
			} else {
				rp2 = rps[pos-2];
			}
		} else {
			rp1 = rps[pos];
			if(pos == (rps.length-1)) {
				rp2 = rps[0];
				((RouteActivity) activity).setSelected(0);
			} else {
				rp2 = rps[pos+1];
			}
		}
		
		int _id = rp1._id;
		rp1._id = rp2._id;
		rp2._id = _id;
		tableRoutePoint.alter(rp1);
		tableRoutePoint.alter(rp2);
	}
	
	public Mark getAsMark(Cursor cursor){
		RoutePoint rp = new RoutePoint(cursor);
		Spinner routes = ((RouteActivity) activity).getRoute();
		Route route = (Route) routes.getSelectedItem();
		return new Mark(rp.name, rp.lat, rp.lon, "", route.date);
	}
	
	public void alterRoute (Route route) {
		TableRoute table = new TableRoute(activity);
		table.alterRoute(route);
	}
	
	public void observe(RoutePointListFragment observer){
		tableRoutePoint.registerObserver(observer);
		tableRoutePoint.notifyObservers();
	}
	
	public void stopObserving(RoutePointListFragment observer) {
		tableRoutePoint.removeObserver(observer);
	}
	
	public Cursor getRoute(int ID) {
		return tableRoutePoint.getRoute(ID);
	}
	
	public void addRoutePoint(Route route, Mark mark) {
		RoutePoint rp = new RoutePoint(route.ID,mark.name, mark.lat, mark.lon);
		tableRoutePoint.addRoutePoint(rp);
	}
}
