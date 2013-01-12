package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.gui.activity.SelectMarkOnMapActivity;
import en.htwg.seapal.gui.fragment.EditMarkDialogFragment;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.models.Route;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MarkSpinnerItemSelectListener implements OnItemSelectedListener {

	private RouteActivity activity = null;
	private RouteController routeController = null;
	
	public MarkSpinnerItemSelectListener(RouteActivity activity) {
		this.activity = activity;
		this.routeController = new RouteController(activity);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		TextView text = (TextView) view.findViewById(1000);
		
		if(text.getText().equals(activity.getResources().getString(R.string.addMarkDialog))) {
			
		} else if(text.getText().equals("----------")) {
			
		} else if(text.getText().equals(activity.getResources().getString(R.string.addNewMarkOnMap))) {
			Intent intent = new Intent(activity, SelectMarkOnMapActivity.class);
			activity.startActivityForResult(intent, RouteActivity.REQUEST_CODE);
		} else if(text.getText().equals(activity.getResources().getString(R.string.addNewMarkByData))) {
			Spinner routes = activity.getRoute();
			Route route = (Route) routes.getSelectedItem();
			EditMarkDialogFragment dialog = new EditMarkDialogFragment(route, null);
			dialog.show(activity.getFragmentManager(), "AddMark");
		} else {
			Spinner routes = activity.getRoute();
			Route route = (Route) routes.getSelectedItem();
			Mark mark = (Mark) parent.getAdapter().getItem(position);
			routeController.addRoutePoint(route, mark);
		}
		
		parent.setSelection(0);		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// nothing to do		
	}

}
