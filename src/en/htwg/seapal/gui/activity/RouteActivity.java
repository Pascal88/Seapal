package en.htwg.seapal.gui.activity;

import java.sql.Date;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import en.htwg.seapal.R;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.controller.RouteController;
import en.htwg.seapal.gui.adapter.MarkSpinnerAdapter;
import en.htwg.seapal.gui.adapter.RouteSpinnerAdapter;
import en.htwg.seapal.gui.fragment.EditMarkDialogFragment;
import en.htwg.seapal.gui.fragment.RoutePointListFragment;
import en.htwg.seapal.gui.listener.route.DownClickListener;
import en.htwg.seapal.gui.listener.route.EditClickListener;
import en.htwg.seapal.gui.listener.route.MarkSpinnerItemSelectListener;
import en.htwg.seapal.gui.listener.route.RouteNameFocusListener;
import en.htwg.seapal.gui.listener.route.RouteSpinnerSelectedItemListener;
import en.htwg.seapal.gui.listener.route.SetClickListener;
import en.htwg.seapal.gui.listener.route.UpClickListener;
import en.htwg.seapal.model.models.Mark;
import en.htwg.seapal.model.models.Route;

public class RouteActivity extends AActivity {

	//Views
	private EditText routeName = null;
	private Spinner routes = null;
	private Spinner marks = null;
	private Button up = null;
	private Button edit = null;
	private Button set = null;
	private Button down = null;
	
	//Fragmente	
	private RoutePointListFragment listViewFragment = null;
	
	//Adapter
	private RouteSpinnerAdapter routeSpinnerAdapter = null;
	private MarkSpinnerAdapter markSpinnerAdapter = null;
	
	//Controller
	private RouteController routeController = null;
	private MarkController markController = null;
	
	private int selected = 0;
	public final static int REQUEST_CODE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.route);//set Layout
		
		this.title = this.getResources().getString(R.string.route);
		
		routeController = new RouteController(this);
		markController = new MarkController(this);
		
		routeName = (EditText) findViewById(R.id.routeNameEdit);// map UI
		routes = (Spinner) findViewById(R.id.routesSpinner);
		marks = (Spinner) findViewById(R.id.addMarkSpinner);
		up = (Button) findViewById(R.id.upButton);
		edit = (Button) findViewById(R.id.editButton);
		set = (Button) findViewById(R.id.setButton);
		down = (Button) findViewById(R.id.downButton);
		
		routeSpinnerAdapter = routeController.getRouteSpinnerAdapter();
		routes.setAdapter(routeSpinnerAdapter);
		routes.setOnItemSelectedListener(new RouteSpinnerSelectedItemListener(this));
		routes.setSelection(0);
		
		markSpinnerAdapter = markController.getMarkSpinnerAdapter();
		marks.setAdapter(markSpinnerAdapter);
		marks.setOnItemSelectedListener(new MarkSpinnerItemSelectListener(this));
		
		routeName.setText(((Route) routeSpinnerAdapter.getItem(0)).name);
		routeName.setOnFocusChangeListener(new RouteNameFocusListener(this));
		
		up.setClickable(true);
		up.setOnClickListener(new UpClickListener(this));
		
		edit.setClickable(true);
		edit.setOnClickListener(new EditClickListener(this));
		
		set.setClickable(true);
		set.setOnClickListener(new SetClickListener());
		
		down.setClickable(true);
		down.setOnClickListener(new DownClickListener(this));
	}
	
	@Override
	protected void onResume() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		listViewFragment = new RoutePointListFragment((Route) routes.getSelectedItem());
		transaction.add(R.id.routeItemList, listViewFragment);
		transaction.commit();		
		super.onStart();
	}

	@Override
	protected void onPause() {
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.remove(listViewFragment);
		transaction.commit();
		super.onStop();
	}
	
	public RoutePointListFragment getListView() {
		return listViewFragment;
	}
	
	public Spinner getRoute() {
		return routes;
	}
	
	public EditText getRouteName(){
		return routeName;
	}
	
	public int getSelected(){
		return selected;
	}
	
	public void setSelected(int i) {
		this.selected = i;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			Route route = (Route) routes.getSelectedItem();
			Bundle extras = data.getExtras();
			Time now = new Time();
			now.setToNow();
			Mark mark = new Mark("", extras.getInt("lat"), extras.getInt("lon"), "", new Date(now.toMillis(false)));
			EditMarkDialogFragment fragment = new EditMarkDialogFragment(route, mark);
			fragment.show(getFragmentManager(), "EDitMark");
		}
	}
	
}
