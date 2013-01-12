package en.htwg.seapal.gui.listener.dashboard;

import en.htwg.seapal.gui.DashboardView;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarChangeListener implements OnSeekBarChangeListener {

	private DashboardView view = null;
	
	public SeekBarChangeListener(DashboardView view) {
		this.view = view;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		Log.d("", "progres " + progress);
		if(fromUser == true && progress > 10) {
			view.setNumElements(progress);
			view.invalidate();
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		Log.d("", "start " + seekBar.getProgress());
		if(seekBar.getProgress() > 10) {
			view.setNumElements(seekBar.getProgress());
			view.invalidate();
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Log.d("", "stop " + seekBar.getProgress());
		if(seekBar.getProgress() > 10) {
			view.setNumElements(seekBar.getProgress());
			view.invalidate();
		}
	}

}
