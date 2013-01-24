package en.htwg.seapal.gui.activity;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.LogController;
import en.htwg.seapal.controller.LogbookController;
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
		
		//LogController controller = new LogController(this);
		
		cog = (DashboardView) findViewById(R.id.cogDashboardView);
		cog.setSeekBar((SeekBar) findViewById(R.id.seekBarCog));
		//cog.setList(controller.getCog());
		sog = (DashboardView) findViewById(R.id.sogDashboardView);
		sog.setSeekBar((SeekBar) findViewById(R.id.seekBarSog));
		//sog.setList(controller.getSog());
		btm = (DashboardView) findViewById(R.id.btmDashboardView);
		btm.setSeekBar((SeekBar) findViewById(R.id.seekBarBtm));
		//btm.setList(controller.getBtm());
		dtm = (DashboardView) findViewById(R.id.DtmDashboardView);
		dtm.setSeekBar((SeekBar) findViewById(R.id.seekBarDtm));
		//dtm.setList(controller.getDtm());
	}
	
	public void backToMap(View v) {
		Intent intent = new Intent(this, MapViewActivity.class);
		startActivity(intent);
	}
}
