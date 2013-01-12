package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DropdownFragmentRight extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		registerForContextMenu(container);
		return inflater.inflate(R.layout.rightdropdown, container, false);
	}

}
