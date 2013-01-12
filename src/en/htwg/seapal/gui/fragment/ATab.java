package en.htwg.seapal.gui.fragment;

import android.app.Fragment;
import android.util.Log;

public abstract class ATab extends Fragment {

	public int convertFromDegMinSec(String in) {
		String[] tmp = in.split("°");
		int Deg = Integer.parseInt(tmp[0]) * 1000000;
		
		tmp = tmp[1].split("'");
		int Min = Integer.parseInt(tmp[0]) * 1000000;
		
		tmp = tmp[1].split("\"");
		int Sec = Integer.parseInt(tmp[0]) * 1000000;
		
		int out = Deg/1 + Min/60 + Sec/3600;
		Log.d("markac", ""+out);
		return out;
	}
	
	public String convertToDegMinSec(int in){
		
		int Deg = (int)(in*0.000001);
		int Min = (int) (((in*0.000001) - Deg) * 60);
		int Sec = (int) (((((in*0.000001) - Deg) * 60) - Min) * 60);
		
		return Deg + "°"+ Min + "'" + Sec + "\"";
	}
}
