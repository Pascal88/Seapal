package en.htwg.seapal.gui.fragment;

import en.htwg.seapal.R;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MarkByCoordinateFragment extends ATab implements ITabFragment {

	private EditText lat, lon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.markbycoordinate, container, false);
	}

	@Override
	public Point getPoint() {
		lat = (EditText) getView().findViewById(R.id.marksLatCoordinatesEdit);
		lon = (EditText) getView().findViewById(R.id.marksLonCoordinatesEdit);
		
		return new Point((int) (Location.convert(lat.getText().toString().replace(',', '.')) / 0.000001),
				(int) (Location.convert(lat.getText().toString().replace(',', '.')) / 0.000001));
	}

	@Override
	public void setPoint(Point point) {
		lat = (EditText) getView().findViewById(R.id.marksLatCoordinatesEdit);
		lon = (EditText) getView().findViewById(R.id.marksLonCoordinatesEdit);

		lat.setText(Location.convert(point.x * 0.000001, Location.FORMAT_SECONDS));
		lon.setText(Location.convert(point.y * 0.000001, Location.FORMAT_SECONDS));
	}

}
