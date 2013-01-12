package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.models.Route;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditMarkDialogFragment extends DialogFragment {
	
	private Activity activity = null;
	private Mark mark = null;
	private Route route = null;
	//View
	private EditText name = null;
	private EditText lat = null;
	private EditText lon = null;
	
	public EditMarkDialogFragment(Mark mark) {
		this.mark = mark;
		activity = getActivity();
	}
	
	public EditMarkDialogFragment(Route route, Mark mark) {
		this.mark = mark;
		activity = getActivity();
		this.route = route;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.editmark, null);
		
	   	name = (EditText) view.findViewById(R.id.editMarkDialogName);
      	lat = (EditText) view.findViewById(R.id.editMarkDialogLat);
      	lon = (EditText) view.findViewById(R.id.editMarkDialogLon);
      	
      	if(mark != null) {
      		if(mark.name != null) name.setText(mark.name);
      		if(mark.lat != 0) lat.setText(Location.convert(mark.lat * 0.000001, Location.FORMAT_SECONDS));
      		if(mark.lon != 0) lon.setText(Location.convert(mark.lon * 0.000001, Location.FORMAT_SECONDS));
      	} else {
      		mark = new Mark();
      		lat.setText("00:00:00.00");
      		lon.setText("00:00:00.00");
      	}

		Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setMessage(R.string.addEditMark);
		builder.setCancelable(true);

		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mark.name = name.getText().toString();
				mark.lat = (int) (Location.convert(lat.getText().toString().replace(',', '.')) / 0.000001);
				mark.lon = (int) (Location.convert(lon.getText().toString().replace(',', '.')) / 0.000001);
				if(route != null) {
					RouteController routeController = new RouteController(activity);
					routeController.addRoutePoint(route, mark);
				} else if(route == null) {
					MarkController markController = new MarkController(activity);
					if(!markController.alterMark(mark)) {
						markController.addMark(mark);
					}
				}
			}
		});

		builder.setNegativeButton(R.string.negative, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		return builder.create();
	}
}
