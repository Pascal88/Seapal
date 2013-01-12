package en.htwg.seapal.gui.activity;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.DashboardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class DashboardActivity extends AActivity {
	
	private DashboardView cog = null;
	private DashboardView sog = null;
	private DashboardView btm = null;
	private DashboardView dtm = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.dasboard);
		this.title = "Dasboard";
		super.onCreate(savedInstanceState);
		
		cog = (DashboardView) findViewById(R.id.cogDashboardView);
		cog.setSeekBar((SeekBar) findViewById(R.id.seekBarCog));
		sog = (DashboardView) findViewById(R.id.sogDashboardView);
		sog.setSeekBar((SeekBar) findViewById(R.id.seekBarSog));
		btm = (DashboardView) findViewById(R.id.btmDashboardView);
		btm.setSeekBar((SeekBar) findViewById(R.id.seekBarBtm));
		dtm = (DashboardView) findViewById(R.id.DtmDashboardView);
		dtm.setSeekBar((SeekBar) findViewById(R.id.seekBarDtm));
	}
	
	public void backToMap(View v) {
		Intent intent = new Intent(this, MapViewActivity.class);
		startActivity(intent);
	}
}
