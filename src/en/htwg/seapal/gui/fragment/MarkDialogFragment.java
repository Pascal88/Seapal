package en.htwg.seapal.gui.fragment;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.gui.activity.MapViewActivity;
import en.htwg.seapal.gui.overlay.IOverlay;
import en.htwg.seapal.gui.overlay.MarkOverlay;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MarkDialogFragment extends DialogFragment {

	private MapView map = null;
	private IOverlay overlay = null;
	private MapViewActivity activity = null;
	private EditText editName = null;
	private EditText editLabel = null;
	private GeoPoint geoPoint = null;

	public MarkDialogFragment(MapViewActivity activity, IOverlay overlay) {
		this.map = activity.getMapView();
		this.overlay = overlay;
		this.activity = activity;
		this.geoPoint = overlay.getGeoPoint();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.markdialog, null);
		
	   	editName = (EditText) view.findViewById(R.id.markNameDialog);
      	editLabel = (EditText) view.findViewById(R.id.markLabelDialog);

		Builder builder = new AlertDialog.Builder(this.getActivity());
		builder.setMessage(R.string.addMarkDialog);
		builder.setCancelable(true);

		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MarkController markController = new MarkController(activity);
				markController.alterMark(geoPoint, editName.getText().toString(), editLabel.getText().toString());
				
				List<Overlay> list = ((MapView) map).getOverlays();
				list .remove(overlay);
				list.add(new MarkOverlay(activity, geoPoint, editName.getText().toString()));
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
