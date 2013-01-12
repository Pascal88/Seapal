package en.htwg.seapal.gui.listener.routeOverlayList;

import en.htwg.seapal.gui.overlay.RouteOverlayList;
import android.view.View;
import android.view.View.OnClickListener;

public class UndoClickListener implements OnClickListener {

	private RouteOverlayList routeOverlayList = null;
	
	public UndoClickListener(RouteOverlayList routeOverlayList) {
		this.routeOverlayList = routeOverlayList;
	}
	
	@Override
	public void onClick(View v) {
		routeOverlayList.undo();
	}

}
