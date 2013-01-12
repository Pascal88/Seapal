package en.htwg.seapal.gui.fragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.GeoInformationController;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MarkByDistanceFragment extends Fragment implements ITabFragment{

	private EditText bearing = null;
	private EditText distance = null;
	private Activity activity = null;
	private GeoInformationController geoInformationController = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
		geoInformationController = new GeoInformationController(this.activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.markbydistance, container, false);
	}

	@Override
	public Point getPoint() {
		bearing = (EditText) getActivity().findViewById(R.id.marksBearingDistanceEdit);
		distance = (EditText) getActivity().findViewById(R.id.marksAtDistanceEdit);
		
		String b = bearing.getText().toString();
		b = b.replace("°", "");
		String d = distance.getText().toString();
		d= d.replace("nm", "");
		
		NumberFormat numberFormat = new DecimalFormat("000.00");
		numberFormat.setRoundingMode(RoundingMode.DOWN);
		
		try {
			return geoInformationController.getPointbyDistanceandBearing(Float.parseFloat(""+ numberFormat.parse(d)), Float.parseFloat(""+numberFormat.parse(b)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setPoint(Point point) {
		bearing = (EditText) getActivity().findViewById(R.id.marksBearingDistanceEdit);
		distance = (EditText) getActivity().findViewById(R.id.marksAtDistanceEdit);
		
		NumberFormat numberFormat = new DecimalFormat("000.00");
		numberFormat.setRoundingMode(RoundingMode.DOWN);
		
		bearing.setText(numberFormat.format(geoInformationController.bearingTo(point.x, point.y)) + "°");
		distance.setText(numberFormat.format((geoInformationController.distanceTo(point.x, point.y)/1852)) + "nm");
	}

}
