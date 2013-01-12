package en.htwg.seapal.gui.listener.mark;

import en.htwg.seapal.controller.GeoInformationController;
import en.htwg.seapal.model.models.GeoInformation;
import android.app.Activity;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SetPositionClickListener implements OnClickListener {

	private Activity activity = null;
	private EditText lat = null;
	private EditText lon = null;
	private GeoInformationController geoInformationController = null;
	private View v = null;
	
	public SetPositionClickListener(Activity activity, View v, EditText lat, EditText lon) {
		this.activity = activity;
		this.v = v;
		this.lat = lat;
		this.lon = lon;
		geoInformationController = new GeoInformationController(this.activity);
	}
	
	@Override
	public void onClick(View v) {
		GeoInformation geo = geoInformationController.getLast();
		
		this.v.setVisibility(View.VISIBLE);
		lat.setText(Location.convert(geo.latitude * 0.000001, Location.FORMAT_SECONDS));
		lon.setText(Location.convert(geo.longitude * 0.000001, Location.FORMAT_SECONDS));
	}

}
