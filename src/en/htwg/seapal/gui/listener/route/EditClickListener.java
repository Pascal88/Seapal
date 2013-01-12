package en.htwg.seapal.gui.listener.route;

import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.activity.RouteActivity;
import en.htwg.seapal.gui.fragment.EditMarkDialogFragment;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.models.Route;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Spinner;

public class EditClickListener implements OnClickListener {

	private RouteActivity activity = null;
	
	public EditClickListener(RouteActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		RouteController routeController = new RouteController(activity);
		Spinner routes = activity.getRoute();
		Route route = (Route) routes.getSelectedItem();
		ListView listView = activity.getListView().getListView();
		Mark mark = routeController.getAsMark((Cursor) listView.getItemAtPosition(activity.getSelected()));
		EditMarkDialogFragment dialog = new EditMarkDialogFragment(route, mark);
		dialog.show(activity.getFragmentManager(), "AddMark");
	}

}
