package en.htwg.seapal.gui.listener.trips;

import en.htwg.seapal.gui.activity.TripEditActivity;
import en.htwg.seapal.gui.adapter.TripsAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TripItemClickListener implements OnItemClickListener {

	private TripsAdapter adapter = null;
	private Context context = null;
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("Trip", adapter.getItem(position));
		Intent intent = new Intent(context, TripEditActivity.class);
		intent.putExtra("Bundle", bundle);
		context.startActivity(intent);
	}

}
