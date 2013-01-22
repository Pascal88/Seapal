package en.htwg.seapal.gui.listener.logbook;

import en.htwg.seapal.gui.activity.TripsActivity;
import en.htwg.seapal.model.models.Boat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class OnGotoTripsClickListener implements OnClickListener {

	private Boat boat = null;
	private Context context = null;
	
	public OnGotoTripsClickListener(Context context,Boat boat) {
		this.context = context;
		this.boat = boat;
	}
	
	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("Boat", boat);
		Intent intent = new Intent(context, TripsActivity.class);
		intent.putExtra("Bundle", bundle);
		context.startActivity(intent);
	}

}
