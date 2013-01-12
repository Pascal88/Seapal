package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.model.models.Route;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class RouteNameFocusListener implements OnFocusChangeListener {

	private RouteActivity activity = null;
	private EditText text = null;

	public RouteNameFocusListener(RouteActivity activity) {
		this.activity = activity;
		text = (EditText) this.activity.findViewById(R.id.routeNameEdit);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(!hasFocus) {
			RouteController routeController = new RouteController(activity);
			Spinner routes = activity.getRoute();
			Route route = (Route) routes.getSelectedItem();
			route.name = text.getText().toString();
			routeController.alterRoute(route);
			routes.setAdapter(routeController.getRouteSpinnerAdapter());
			routes.requestLayout();
		}
	}

}
