package en.htwg.seapal.gui.activity;

import android.os.Bundle;
import en.htwg.seapal.R;
import en.htwg.seapal.model.models.Trip;

public class TripEditActivity extends AActivity {

	private Trip trip = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			trip = (Trip) extras.getSerializable("Trip");
		}
		
		setContentView(R.layout.edit_trip);
	}
}
