package en.htwg.seapal.gui.listener;

import en.htwg.seapal.R;
import en.htwg.seapal.gui.fragment.ITabFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.util.Log;

public class TabListener<T extends Fragment> implements ActionBar.TabListener, ITabListener<T> {

	private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;
	
	/** Constructor used each time a new tab is created.
     * @param activity  The host Activity, used to instantiate the fragment
     * @param tag  The identifier tag for the fragment
     * @param clz  The fragment's Class, used to instantiate the fragment
     */
   public TabListener(Activity activity, String tag, Class<T> clz) {
       mActivity = activity;
       mTag = tag;
       mClass = clz;
       mFragment = Fragment.instantiate(mActivity, mClass.getName());
   }
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		Log.d("TabListener", "Reselect");
		 // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            
            ft.add(R.id.tabContent, mFragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.d("TabListener", "Select");
		 // Check if the fragment is already initialized
        if (mFragment != null && !mFragment.isDetached()) {
        	Log.d("TabListener", "Add");
        	ft.add(R.id.tabContent, mFragment, mTag);
        } else if(mFragment != null) {
        	// If it exists, simply attach it in order to show it
        	Log.d("TabListener", "Attach");
            ft.attach(mFragment);
        }
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		Log.d("TabListener", "Unselect");
		if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }		
	}
	
	/* (non-Javadoc)
	 * @see en.htwg.captainslog.gui.ITabListener#getPoint()
	 */
	public Point getPoint() {
		return ((ITabFragment) mFragment).getPoint();
	}
	
	/* (non-Javadoc)
	 * @see en.htwg.captainslog.gui.ITabListener#setPoint(android.graphics.Point)
	 */
	public void setPoint(Point p) {
		((ITabFragment) mFragment).setPoint(p);
	}
}
