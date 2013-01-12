package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.model.models.Route;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class RouteSpinnerSelectedItemListener implements OnItemSelectedListener {

	private RouteActivity activity = null;
	
	public RouteSpinnerSelectedItemListener(RouteActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Spinner route = activity.getRoute();
		Route r = (Route) route.getAdapter().getItem(position);
		activity.getListView().setRoute(r);
		activity.getListView().update();
		activity.getRouteName().setText(r.name);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
