package en.htwg.seapal.gui.listener.logbook;

import en.htwg.seapal.controller.LogbookController;
import en.htwg.seapal.gui.activity.LogbookActivity;
import en.htwg.seapal.model.models.Boat;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AddBoatClickListener implements OnClickListener {

	private Context context = null;
	private ArrayAdapter<Boat> adapter = null;
	private LogbookController logbookController = null;
	private LogbookActivity activity = null;
	
	public AddBoatClickListener(Context context, ArrayAdapter<Boat> adapter, LogbookActivity activity) {
		this.context = context;
		this.adapter = adapter;
		this.activity = activity;
		logbookController = new LogbookController(context);
	}
	
	@Override
	public void onClick(View v) {
		Boat boat = activity.getBoat();
		logbookController.addBoat(boat);
		adapter.add(boat);
		
		activity.getList().requestLayout();
		
		CharSequence text = "Added Boat";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
