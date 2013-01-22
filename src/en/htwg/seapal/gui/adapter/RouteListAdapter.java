package en.htwg.seapal.gui.adapter;

import java.util.List;

import en.htwg.seapal.model.models.Route;
import en.htwg.seapal.model.models.RoutePoint;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class RouteListAdapter extends ArrayAdapter<RoutePoint> implements ListAdapter {

	private List<RoutePoint> content = null;
	private Activity activity = null;
	
	public RouteListAdapter(Activity activity, Route route) {
		super(activity, 0);
		
		this.activity = activity;
		this.content = route.points;
	}
	/*
	private static class ViewHolder {
		protected TextView ID;
	    protected TextView name;
	    protected TextView lat;
	    protected TextView lon;
	    protected TextView brng;
	    protected TextView dist;
	}
	
	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public RoutePoint getItem(int position) {
		return content.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		
		if(convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			view = inflater.inflate(R.layout.routelist, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.ID = (TextView) view.findViewById(R.id.routeListID);
			viewHolder.ID.setTextSize(15.0f);
			viewHolder.name = (TextView) view.findViewById(R.id.routeListMarkName);
			viewHolder.name.setTextSize(15.0f);
			viewHolder.lat = (TextView) view.findViewById(R.id.routeListLat);
			viewHolder.lat.setTextSize(15.0f);
			viewHolder.lon = (TextView) view.findViewById(R.id.routeListLon);
			viewHolder.lon.setTextSize(15.0f);
			viewHolder.brng = (TextView) view.findViewById(R.id.routeListBearing);
			viewHolder.brng.setTextSize(15.0f);
			viewHolder.dist = (TextView) view.findViewById(R.id.routeListDistance);
			viewHolder.dist.setTextSize(15.0f);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		
			holder.ID.setText("" + content.get(position)._id);
			holder.name.setText(content.get(position).name);
			holder.lat.setText(Location.convert((content.get(position).lat * 0.000001), Location.FORMAT_SECONDS));
			holder.lon.setText(Location.convert((content.get(position).lon * 0.000001), Location.FORMAT_SECONDS));
			
		GeoInformationController geoInformationController = new GeoInformationController(activity);
			
			double distance;
			double bearing;
			if(position == 0) {
				GeoInformation geoInformation = geoInformationController.getLast();
				distance = geoInformationController.calcDistance(geoInformation.latitude, geoInformation.longitude, content.get(position).lat, content.get(position).lon);
				bearing = geoInformationController.calcBearingTo(geoInformation.latitude, geoInformation.longitude, content.get(position).lat, content.get(position).lon);
				
			} else {
				distance = geoInformationController.calcDistance(content.get(position-1).lat, content.get(position-1).lon, content.get(position).lat, content.get(position).lon);
				bearing = geoInformationController.calcBearingTo(content.get(position-1).lat, content.get(position-1).lon, content.get(position).lat, content.get(position).lon);
			}
			NumberFormat numberFormat = new DecimalFormat("000.00");//init Number format
			numberFormat.setRoundingMode(RoundingMode.DOWN);
			
			holder.brng.setText(numberFormat.format(bearing) + "°");
			holder.dist.setText(numberFormat.format(distance/1852) + "nm");
		
		return view;
	}
	
	public void changeDataSet(Route route) {
		this.content = route.getPoints();
		this.notifyDataSetChanged();
	}
	
	public void up(int id) {
		if(id != -1) {
			RoutePoint rp = content.get(id);
			content.remove(rp);
			content.add(id-1, rp);
			
			RouteController routeController = new RouteController(activity);
			//routeController.swap(content.get(id), content.get(id-1));
			notifyDataSetChanged();
		}
	}
	
	public void down(int id) {
		if(id != -1) {
			RouteController routeController = new RouteController(activity);
			routeController.swap(content.get(id), content.get(id+1));
			
			RoutePoint rp = content.get(id);
			content.remove(rp);
			content.add(id+1, rp);
			
			notifyDataSetChanged();
		}
	}
*/

}
