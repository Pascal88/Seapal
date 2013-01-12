package en.htwg.seapal.gui.activity;

import en.htwg.seapal.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public abstract class AActivity extends Activity {

	private ActionBar actionBar = null;
	protected CharSequence title = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		initActionBar();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("ItemSelected", "" + item.getItemId());
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
	
	private void initActionBar() {
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(title);
		actionBar.setLogo(R.drawable.actionlogo);
	}
}
