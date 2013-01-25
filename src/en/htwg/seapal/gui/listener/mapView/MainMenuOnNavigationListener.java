package en.htwg.seapal.gui.listener.mapView;

import en.htwg.seapal.gui.activity.IMapViewActivity;
import android.app.ActionBar.OnNavigationListener;

public class MainMenuOnNavigationListener implements OnNavigationListener {
	
	private IMapViewActivity activity = null;
	
	public MainMenuOnNavigationListener(IMapViewActivity activity) {
		this.activity = activity;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long itemid) {
		switch(position) {
		case 0:
			activity.useGoogleMap();
			break;
		case 1:
			activity.useOsmMap();
			break;
		case 2:
			activity.addMark();
			break;
		case 3:
			activity.addRoute();
			break;
		case 4:
			activity.calcDistance();
			break;
		case 5:
			activity.centerMap();
			break;
		case 6:
			activity.startAim();
			break;
		case 7:
			activity.discardTarget();
			break;
		}
		return false;
	}

}
