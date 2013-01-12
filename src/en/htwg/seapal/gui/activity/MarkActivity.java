package en.htwg.seapal.gui.activity;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.MarkController;
import en.htwg.seapal.gui.adapter.MarkAdapter;
import en.htwg.seapal.gui.fragment.MarkByCoordinateFragment;
import en.htwg.seapal.gui.fragment.MarkByCrossBearingFragment;
import en.htwg.seapal.gui.fragment.MarkByDistanceFragment;
import en.htwg.seapal.gui.listener.ITabListener;
import en.htwg.seapal.gui.listener.TabListener;
import en.htwg.seapal.gui.listener.mark.AddMarkClickListener;
import en.htwg.seapal.model.models.Mark;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MarkActivity extends Activity {

	private ListView listView = null;
	private MarkController markController = null;
	private ActionBar actionBar = null;
	private MarkAdapter adapter = null;
	protected CharSequence title = "Marks";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marks);

		listView = (ListView) findViewById(R.id.markListView);
		markController = new MarkController(this);
		actionBar = getActionBar();

		initListView();
		initActionBar();
	}


	@Override
	protected void onResume() {
		inititialTabContent();
		super.onResume();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MapViewActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initListView() {
		LayoutInflater inflater = getLayoutInflater();
		adapter = new MarkAdapter(this, markController.getAllMarks());

		listView.addHeaderView(inflater.inflate(R.layout.marklist, null), null,
				false);
		listView.setHeaderDividersEnabled(true);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Tab selected = actionBar.getSelectedTab();
				ITabListener<?> tab = (ITabListener<?>) selected.getTag();
				Mark mark = (Mark) parent.getAdapter().getItem(position);

				tab.setPoint(new Point(mark.getLat(), mark.getLon()));
				
				setNameAndLabel(mark.getName(), mark.getLabel());
			}
		});
	}

	private void initActionBar() {
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setLogo(R.drawable.actionlogo);


		Resources res = getResources();

		TabListener<MarkByCoordinateFragment> tabListenerCoordinate = new TabListener<MarkByCoordinateFragment>(
				(Activity) this, "MarkByCoordinate",
				MarkByCoordinateFragment.class);

		Tab tabCoordinates = actionBar.newTab()
				.setText(res.getString(R.string.byCoordinate))
				.setTabListener(tabListenerCoordinate)
				.setTag(tabListenerCoordinate);

		actionBar.addTab(tabCoordinates);

		TabListener<MarkByCrossBearingFragment> tabListenerCross = new TabListener<MarkByCrossBearingFragment>(
				(Activity) this, "MarkByCrossBearing",
				MarkByCrossBearingFragment.class);

		Tab tabCrossBearing = actionBar.newTab()
				.setText(res.getString(R.string.byCrossBearing))
				.setTabListener(tabListenerCross).setTag(tabListenerCross);

		actionBar.addTab(tabCrossBearing);

		TabListener<MarkByDistanceFragment> tabListenerDistance = new TabListener<MarkByDistanceFragment>(
				(Activity) this, "MarkByDistance", MarkByDistanceFragment.class);

		Tab tabDistance = actionBar.newTab()
				.setText(res.getString(R.string.byDistance))
				.setTabListener(tabListenerDistance)
				.setTag(tabListenerDistance);

		actionBar.addTab(tabDistance);
	}
	
	private void setNameAndLabel(String name, String label) {
		EditText editName = (EditText) findViewById(R.id.editMarkName);
		EditText editLabel = (EditText) findViewById(R.id.editMarkLabel);
		
		editName.setText(name);
		editLabel.setText(label);
	}
	
	private void inititialTabContent(){
		Tab selected = actionBar.getSelectedTab();
		ITabListener<?> tab = (ITabListener<?>) selected.getTag();
		Mark mark = (Mark) listView.getAdapter().getItem(1);

		tab.setPoint(new Point(mark.getLat(), mark.getLon()));
		
		setNameAndLabel(mark.getName(), mark.getLabel());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add, menu);
	    MenuItem item = menu.findItem(R.id.addMarkItem);
	    View view = item.getActionView();
	    Button button = (Button) view.findViewById(R.id.addEditMarkButton);
	    button.setOnClickListener(new AddMarkClickListener(this, adapter));
	    return true;
	}
}
