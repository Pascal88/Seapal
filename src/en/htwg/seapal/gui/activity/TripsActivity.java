package en.htwg.seapal.gui.activity;

import java.util.Calendar;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.TripsController;
import en.htwg.seapal.gui.adapter.TripsAdapter;
import en.htwg.seapal.gui.listener.trips.DateChangedListener;
import en.htwg.seapal.gui.listener.trips.NewTripClickListener;
import en.htwg.seapal.gui.listener.trips.TripItemClickListener;
import en.htwg.seapal.model.models.Boat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

public class TripsActivity extends AActivity {

	private Boat boat = null;
	private DatePicker from = null;
	private DatePicker to = null;
	private ListView list = null;
	private TripsAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			Log.d("", "asddsa");
			Bundle b = extras.getBundle("Bundle");
			boat = (Boat) b.getSerializable("Boat");
		}
		
		setContentView(R.layout.trips);
		LayoutInflater inflater = getLayoutInflater();
		
		TripsController tripsController = new TripsController(getApplicationContext());
		adapter = new TripsAdapter(this, tripsController.getTrips(boat));
		
		Calendar c = Calendar.getInstance();
		c.setTime(tripsController.getMinDate());
		
		from = (DatePicker) findViewById(R.id.dateFrom);
		to = (DatePicker) findViewById(R.id.dateTo);
		from.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DateChangedListener(this, to));
		c.setTime(tripsController.maxDate());
		to.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DateChangedListener(this, from));
		list = (ListView) findViewById(R.id.tripsList);
		
		list.addHeaderView(inflater.inflate(R.layout.triphead, null), null,	false);
		list.setHeaderDividersEnabled(true);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new TripItemClickListener());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.trips, menu);
	    MenuItem item = menu.findItem(R.id.newTrip);
	    View view = item.getActionView();
	    Button button = (Button) view.findViewById(R.id.newTripButton);
	    button.setOnClickListener(new NewTripClickListener(this, adapter));
	    return true;
	}
	
	public void dateChanged(long first, long second) {
		TripsController tripsController = new TripsController(getApplicationContext());
		adapter.replace(tripsController.getTripsBetween(boat, first, second));
		list.requestLayout();
	}
}
