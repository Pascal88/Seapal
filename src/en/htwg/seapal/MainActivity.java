package en.htwg.seapal;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.Eula;
import en.htwg.seapal.gui.activity.MapViewActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
        Eula.showEulaRequireAcceptance(this);
        Intent goToNextActivity = new Intent(this, MapViewActivity.class);
        startActivity(goToNextActivity);
    }

   
    
}
