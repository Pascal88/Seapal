package en.htwg.seapal.gui.listener.distanceOverlayList;

import en.htwg.seapal.gui.activity.IMapViewActivity;
import en.htwg.seapal.gui.overlay.DistanceOverlayList;
import android.view.View;
import android.view.View.OnClickListener;

public class FinishClickListener implements OnClickListener {

	private IMapViewActivity activity = null;
	
	public FinishClickListener(IMapViewActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View v) {
		DistanceOverlayList distanceOverlayList = activity.getDistanceOverlayList();
		distanceOverlayList.finish();
		activity.getDoubleTapDragListener().endDistance();
	}

}
