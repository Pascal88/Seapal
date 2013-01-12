package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.TripsController;
import en.htwg.seapal.model.models.Trip;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class NewTripDialogFragment extends DialogFragment {

	private Context context = null;
	private ArrayAdapter<Trip> adapter = null;
	
	private EditText title;
	private EditText skipper;
	private EditText from;
	private EditText to;
	
	public NewTripDialogFragment(Context context, ArrayAdapter<Trip> adapter) {
		this.context = context;
		this.adapter = adapter;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.newtripdialog, null);
		
		title = (EditText) view.findViewById(R.id.title);
		skipper = (EditText) view.findViewById(R.id.skipper);
		from =  (EditText) view.findViewById(R.id.from);
		to = (EditText) view.findViewById(R.id.to);
		
		Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setMessage(R.string.newTrip);
		builder.setCancelable(true);
		
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				TripsController tripsController =  new TripsController(context);
				Trip trip = tripsController.newTrip(
						(title.getText().toString().equals("") == false) ? title.getText().toString() : "", 
						(skipper.getText().toString().equals("") == false) ? skipper.getText().toString() : "", 
						(from.getText().toString().equals("") == false) ? from.getText().toString() : "", 
						(to.getText().toString().equals("") == false) ? to.getText().toString() : "");
				
				adapter.add(trip);
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
