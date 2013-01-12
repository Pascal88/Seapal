package en.htwg.seapal.gui.listener.logbook;

import en.htwg.seapal.gui.activity.LogbookActivity;
import en.htwg.seapal.model.models.Boat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnBoatItemClickListener implements OnItemClickListener {

	private LogbookActivity activity = null;
	
	public OnBoatItemClickListener(LogbookActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
		Boat boat = (Boat) activity.getList().getAdapter().getItem(position);
		activity.setBoat(boat);
		view.setSelected(true);
	}

}
