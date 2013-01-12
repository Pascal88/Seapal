package en.htwg.seapal.gui.activity;

import en.htwg.seapal.R;
import en.htwg.seapal.controller.LogbookController;
import en.htwg.seapal.gui.adapter.BoatAdapter;
import en.htwg.seapal.gui.listener.logbook.AddBoatClickListener;
import en.htwg.seapal.gui.listener.logbook.OnBoatItemClickListener;
import en.htwg.seapal.model.models.Boat;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class LogbookActivity extends AActivity {

	private LogbookController logbookController = null;
	private ListView list;
	private ArrayAdapter<Boat> adapter = null;	
	private EditText name;
	private EditText typ;
	private EditText productionYear;
	private EditText corporateIdNumber;
	private EditText draftsman;
	private EditText engine;
	private EditText sailEmblem;
	private EditText length;
	private EditText tankSize;
	private EditText homePort;
	private EditText width;
	private EditText waterTankSize;
	private EditText yachtClub;
	private EditText flotationDepth;
	private EditText sewageWaterTankSize;
	private EditText owner;
	private EditText mastHeight;
	private EditText mainSailSize;
	private EditText insurance;
	private EditText displacement;
	private EditText genoaSize;
	private EditText callSign;
	private EditText rigKind;
	private EditText spiSize;
	private ImageButton add;
	private ImageButton delete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.logbook);
		this.title = "LogBook";
		super.onCreate(savedInstanceState);
		logbookController = new LogbookController(this);
		
		name = (EditText) findViewById(R.id.editBoatName);
		typ = (EditText) findViewById(R.id.editType);
		productionYear = (EditText) findViewById(R.id.editProductionYear);
		corporateIdNumber = (EditText) findViewById(R.id.editIdNumber);
		draftsman = (EditText) findViewById(R.id.editDraftsman);
		engine = (EditText) findViewById(R.id.editEngine);
		sailEmblem = (EditText) findViewById(R.id.editSailEmblem);
		length = (EditText) findViewById(R.id.editLength);
		tankSize = (EditText) findViewById(R.id.editTankSize);
		homePort = (EditText) findViewById(R.id.editHomePort);
		width = (EditText) findViewById(R.id.editWidth);
		waterTankSize = (EditText) findViewById(R.id.editWaterTankSize);
		yachtClub = (EditText) findViewById(R.id.editYachtClub);
		flotationDepth = (EditText) findViewById(R.id.editFlotationDepth);
		sewageWaterTankSize = (EditText) findViewById(R.id.editSewageWaterTankSize);
		owner = (EditText) findViewById(R.id.editOwner);
		mastHeight = (EditText) findViewById(R.id.editMastHeight);
		mainSailSize = (EditText) findViewById(R.id.editMainSailSize);
		insurance = (EditText) findViewById(R.id.editInsurance);
		displacement = (EditText) findViewById(R.id.editDisplacement);
		genoaSize = (EditText) findViewById(R.id.editGenoaSize);
		callSign = (EditText) findViewById(R.id.editCallSign);
		rigKind = (EditText) findViewById(R.id.editRigKind);
		spiSize = (EditText) findViewById(R.id.editSpiSize);
		
		list = (ListView) findViewById(R.id.boatlist);
		adapter = new BoatAdapter(this, logbookController.getBoats());
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.boatlisthead, null);
		list.addHeaderView(view);
		list.setHeaderDividersEnabled(true);
		list.setSelectionAfterHeaderView();
		list.setOnItemClickListener(new OnBoatItemClickListener(this));
		list.setAdapter(adapter);
		
		add = (ImageButton) findViewById(R.id.addButton);
		add.setOnClickListener(new AddBoatClickListener(getApplicationContext(), adapter, this));
		delete = (ImageButton) findViewById(R.id.deleteButton);
	}
	
	public void backToMap(View v) {
		Intent intent = new Intent(this, MapViewActivity.class);
		startActivity(intent);
	}
	
	public void deleteBoat(View v) {
		notImplemented();
	}
	
	public void saveBoat(View v) {
		notImplemented();
	}
	
	public void latestBoat(View v) {
		notImplemented();
	}
	
	public void prevBoat(View v) {
		notImplemented();
	}
	
	public void nextBoat(View v) {
		notImplemented();
	}
	
	public void notImplemented() {
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(getApplicationContext(), R.string.notImplemented, duration);
		toast.show();
	}
	
	public Boat getBoat() {
		return new Boat(
				name.getText().toString(), 
				typ.getText().toString(), 
				productionYear.getText().toString(), 
				corporateIdNumber.getText().toString(), 
				draftsman.getText().toString(), 
				engine.getText().toString(), 
				sailEmblem.getText().toString(), 
				length.getText().toString(), 
				tankSize.getText().toString(), 
				homePort.getText().toString(), 
				width.getText().toString(), 
				waterTankSize.getText().toString(), 
				yachtClub.getText().toString(), 
				flotationDepth.getText().toString(), 
				sewageWaterTankSize.getText().toString(), 
				owner.getText().toString(), 
				mastHeight.getText().toString(), 
				mainSailSize.getText().toString(), 
				insurance.getText().toString(), 
				displacement.getText().toString(), 
				genoaSize.getText().toString(), 
				callSign.getText().toString(), 
				rigKind.getText().toString(), 
				spiSize.getText().toString());
	}
	
	public void setBoat(Boat boat) {
		if(!boat.name.isEmpty()) name.setText(boat.name); else name.setText(""); 
		if(!boat.typ.isEmpty()) typ.setText(boat.typ); else typ.setText("");
		if(!boat.productionYear.isEmpty()) productionYear.setText(boat.productionYear); else productionYear.setText("");
		if(!boat.corporateIdNumber.isEmpty()) corporateIdNumber.setText(boat.corporateIdNumber); else corporateIdNumber.setText("");
		if(!boat.draftsman.isEmpty()) draftsman.setText(boat.draftsman); else draftsman.setText(""); 
		if(!boat.engine.isEmpty()) engine.setText(boat.engine); else engine.setText(""); 
		if(!boat.sailEmblem.isEmpty()) sailEmblem.setText(boat.sailEmblem); else sailEmblem.setText("");  
		if(!boat.length.isEmpty()) length.setText(boat.length); else length.setText(""); 
		if(!boat.tankSize.isEmpty()) tankSize.setText(boat.tankSize); else tankSize.setText("");  
		if(!boat.homePort.isEmpty()) homePort.setText(boat.homePort); else homePort.setText("");
		if(!boat.width.isEmpty()) width.setText(boat.width); else width.setText(""); 
		if(!boat.waterTankSize.isEmpty()) waterTankSize.setText(boat.waterTankSize); else waterTankSize.setText(""); 
		if(!boat.yachtClub.isEmpty()) yachtClub.setText(boat.yachtClub); else yachtClub.setText(""); 
		if(!boat.flotationDepth.isEmpty()) flotationDepth.setText(boat.flotationDepth); else flotationDepth.setText("");  
		if(!boat.sewageWaterTankSize.isEmpty()) sewageWaterTankSize.setText(boat.sewageWaterTankSize); else sewageWaterTankSize.setText(""); 
		if(!boat.owner.isEmpty()) owner.setText(boat.owner); else owner.setText(""); 
		if(!boat.mastHeight.isEmpty()) mastHeight.setText(boat.mastHeight); else mastHeight.setText(""); 
		if(!boat.mainSailSize.isEmpty()) mainSailSize.setText(boat.mainSailSize); else mainSailSize.setText(""); 
		if(!boat.insurance.isEmpty()) insurance.setText(boat.insurance); else insurance.setText(""); 
		if(!boat.displacement.isEmpty()) displacement.setText(boat.displacement); else displacement.setText("");  
		if(!boat.genoaSize.isEmpty()) genoaSize.setText(boat.genoaSize); else genoaSize.setText(""); 
		if(!boat.callSign.isEmpty()) callSign.setText(boat.callSign); else callSign.setText(""); 
		if(!boat.rigKind.isEmpty()) rigKind.setText(boat.rigKind); else rigKind.setText(""); 
		if(!boat.spiSize.isEmpty()) spiSize.setText(boat.spiSize); else spiSize.setText(""); 
	}

	public ListView getList() {
		return list;
	}
	
}
