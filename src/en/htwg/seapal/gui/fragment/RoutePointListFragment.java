package en.htwg.seapal.gui.fragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import en.htwg.seapal.R;
import en.htwg.seapal.Observer.IObserver;
import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.gui.listener.route.ListItemSelectedListener;
import en.htwg.seapal.model.models.Route;
import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class RoutePointListFragment extends ListFragment implements IObserver {

	private SimpleCursorAdapter adapter;
	private RouteController routeController;
	private Route route;

	private class RouteListViewBinder implements SimpleCursorAdapter.ViewBinder{

		private Activity activity = null;
		
		public RouteListViewBinder(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			
			switch(view.getId()) {
			case R.id.routeListID:
				((TextView) view).setText("" + cursor.getPosition());
				return true;
			case R.id.routeListLat:
			case R.id.routeListLon:
				((TextView) view).setText(Location.convert(cursor.getDouble(columnIndex) * 0.000001, Location.FORMAT_SECONDS));
				return true;
			case R.id.routeListBearing:
				GeoInformationController geoInformationController = new GeoInformationController(activity);
				NumberFormat numberFormat = new DecimalFormat("000.00");
				numberFormat.setRoundingMode(RoundingMode.DOWN);
				
				if(cursor.getPosition() == 0) {
					int destLat = cursor.getInt(cursor.getColumnIndex("lat"));
					int destLon = cursor.getInt(cursor.getColumnIndex("lon"));
					((TextView) view).setText("" + numberFormat.format(geoInformationController.bearingTo(destLat, destLon)));
				} else {
					int destLat = cursor.getInt(cursor.getColumnIndex("lat"));
					int destLon = cursor.getInt(cursor.getColumnIndex("lon"));
					cursor.moveToPrevious();
					int curLat = cursor.getInt(cursor.getColumnIndex("lat"));
					int curLon = cursor.getInt(cursor.getColumnIndex("lon"));
					((TextView) view).setText("" + numberFormat.format(geoInformationController.calcBearingTo(curLat, curLon, destLat, destLon)));
				}
				return true;
			case R.id.routeListDistance:
				GeoInformationController geoInformationController2 = new GeoInformationController(activity);
				NumberFormat numberFormat2 = new DecimalFormat("000.00");
				numberFormat2.setRoundingMode(RoundingMode.DOWN);
				
				if(cursor.getPosition() == 0) {
					int destLat2 = cursor.getInt(cursor.getColumnIndex("lat"));
					int destLon2 = cursor.getInt(cursor.getColumnIndex("lon"));
					((TextView) view).setText("" + numberFormat2.format(geoInformationController2.distanceTo(destLat2, destLon2)) + "°");
				} else {
					int destLat2 = cursor.getInt(cursor.getColumnIndex("lat"));
					int destLon2 = cursor.getInt(cursor.getColumnIndex("lon"));
					cursor.moveToPrevious();
					int curLat2 = cursor.getInt(cursor.getColumnIndex("lat"));
					int curLon2 = cursor.getInt(cursor.getColumnIndex("lon"));
					((TextView) view).setText("" + numberFormat2.format((geoInformationController2.calcDistance(curLat2, curLon2, destLat2, destLon2)/1852)) + " nm");
				}
				return true;
			}
			return false;
		}
		
	}
	
	public RoutePointListFragment(Route route) {
		this.route = route;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		routeController = new RouteController(getActivity());
		
		String[] fromColumns = {"_id", "name", "lat", "lon", "lat", "lon"};
		int[] toViews = {R.id.routeListID,R.id.routeListMarkName, R.id.routeListLat, R.id.routeListLon, R.id.routeListBearing, R.id.routeListDistance};
		
		adapter = new SimpleCursorAdapter(getActivity(), R.layout.routelist, null, fromColumns, toViews, 0);
		adapter.setViewBinder(new RouteListViewBinder(getActivity()));
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Override
	public void update() {
		Cursor c = adapter.swapCursor(routeController.getRoute(route.ID));	
		adapter.notifyDataSetChanged();
		this.getListView().invalidateViews();
		if(c != null) c.close();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		ListView listView = this.getListView();
		
		listView.addHeaderView(getActivity().getLayoutInflater().inflate(R.layout.routelist, null), null, false);
		listView.setHeaderDividersEnabled(true);
		listView.setOnItemClickListener(new ListItemSelectedListener((RouteActivity) getActivity()));
		
		setListAdapter(adapter);
		
		routeController.observe(this);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		routeController.stopObserving(this);
		super.onDestroyView();
	}
	
}
