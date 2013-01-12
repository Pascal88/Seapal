package en.htwg.seapal.model.models;

import java.io.Serializable;

import android.database.Cursor;

public class Boat implements IModel, Serializable{

	public String Primary = "ID";
	public int ID;
	public String name;
	public String typ;
	public String productionYear;
	public String corporateIdNumber;
	public String draftsman;
	public String engine;
	public String sailEmblem;
	public String length;
	public String tankSize;
	public String homePort;
	public String width;
	public String waterTankSize;
	public String yachtClub;
	public String flotationDepth;
	public String sewageWaterTankSize;
	public String owner;
	public String mastHeight;
	public String mainSailSize;
	public String insurance;
	public String displacement;
	public String genoaSize;
	public String callSign;
	public String rigKind;
	public String spiSize;
	
	public Boat() { }
	
	public Boat(Cursor cursor){
		super();
		this.ID = cursor.getInt(cursor.getColumnIndex("ID"));
		this.name = cursor.getString(cursor.getColumnIndex("name"));
		this.typ = cursor.getString(cursor.getColumnIndex("typ"));
		this.productionYear = cursor.getString(cursor.getColumnIndex("productionYear"));
		this.corporateIdNumber = cursor.getString(cursor.getColumnIndex("corporateIdNumber"));
		this.draftsman = cursor.getString(cursor.getColumnIndex("draftsman"));
		this.engine = cursor.getString(cursor.getColumnIndex("engine"));
		this.sailEmblem = cursor.getString(cursor.getColumnIndex("sailEmblem"));
		this.length = cursor.getString(cursor.getColumnIndex("length"));
		this.tankSize = cursor.getString(cursor.getColumnIndex("tankSize"));
		this.homePort = cursor.getString(cursor.getColumnIndex("homePort"));
		this.width = cursor.getString(cursor.getColumnIndex("width"));
		this.waterTankSize = cursor.getString(cursor.getColumnIndex("waterTankSize"));
		this.yachtClub = cursor.getString(cursor.getColumnIndex("yachtClub"));
		this.flotationDepth = cursor.getString(cursor.getColumnIndex("flotationDepth"));
		this.sewageWaterTankSize = cursor.getString(cursor.getColumnIndex("sewageWaterTankSize"));
		this.owner = cursor.getString(cursor.getColumnIndex("owner"));
		this.mastHeight = cursor.getString(cursor.getColumnIndex("mastHeight"));
		this.mainSailSize = cursor.getString(cursor.getColumnIndex("mainSailSize"));
		this.insurance = cursor.getString(cursor.getColumnIndex("insurance"));
		this.displacement = cursor.getString(cursor.getColumnIndex("displacement"));
		this.genoaSize = cursor.getString(cursor.getColumnIndex("genoaSize"));
		this.callSign = cursor.getString(cursor.getColumnIndex("productionYear"));
		this.rigKind = cursor.getString(cursor.getColumnIndex("callSign"));
		this.spiSize = cursor.getString(cursor.getColumnIndex("spiSize"));
	}
	
	public Boat(int iD, String name, String typ,
			String productionYear, String corporateIdNumber, String draftsman,
			String engine, String sailEmblem, String length, String tankSize,
			String homePort, String width, String waterTankSize,
			String yachtClub, String flotationDepth,
			String sewageWaterTankSize, String owner, String mastHeight,
			String mainSailSize, String insurance, String displacement,
			String genoaSize, String callSign, String rigKind, String spiSize) {
		super();
		ID = iD;
		this.name = name;
		this.typ = typ;
		this.productionYear = productionYear;
		this.corporateIdNumber = corporateIdNumber;
		this.draftsman = draftsman;
		this.engine = engine;
		this.sailEmblem = sailEmblem;
		this.length = length;
		this.tankSize = tankSize;
		this.homePort = homePort;
		this.width = width;
		this.waterTankSize = waterTankSize;
		this.yachtClub = yachtClub;
		this.flotationDepth = flotationDepth;
		this.sewageWaterTankSize = sewageWaterTankSize;
		this.owner = owner;
		this.mastHeight = mastHeight;
		this.mainSailSize = mainSailSize;
		this.insurance = insurance;
		this.displacement = displacement;
		this.genoaSize = genoaSize;
		this.callSign = callSign;
		this.rigKind = rigKind;
		this.spiSize = spiSize;
	}
	
	public Boat(String name, String typ,
			String productionYear, String corporateIdNumber, String draftsman,
			String engine, String sailEmblem, String length, String tankSize,
			String homePort, String width, String waterTankSize,
			String yachtClub, String flotationDepth,
			String sewageWaterTankSize, String owner, String mastHeight,
			String mainSailSize, String insurance, String displacement,
			String genoaSize, String callSign, String rigKind, String spiSize) {
		super();
		this.name = name;
		this.typ = typ;
		this.productionYear = productionYear;
		this.corporateIdNumber = corporateIdNumber;
		this.draftsman = draftsman;
		this.engine = engine;
		this.sailEmblem = sailEmblem;
		this.length = length;
		this.tankSize = tankSize;
		this.homePort = homePort;
		this.width = width;
		this.waterTankSize = waterTankSize;
		this.yachtClub = yachtClub;
		this.flotationDepth = flotationDepth;
		this.sewageWaterTankSize = sewageWaterTankSize;
		this.owner = owner;
		this.mastHeight = mastHeight;
		this.mainSailSize = mainSailSize;
		this.insurance = insurance;
		this.displacement = displacement;
		this.genoaSize = genoaSize;
		this.callSign = callSign;
		this.rigKind = rigKind;
		this.spiSize = spiSize;
	}
	public String getPrimary() {
		return Primary;
	}
	public void setPrimary(String primary) {
		Primary = primary;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getProductionYear() {
		return productionYear;
	}
	public void setProductionYear(String productionYear) {
		this.productionYear = productionYear;
	}
	public String getCorporateIdNumber() {
		return corporateIdNumber;
	}
	public void setCorporateIdNumber(String corporateIdNumber) {
		this.corporateIdNumber = corporateIdNumber;
	}
	public String getDraftsman() {
		return draftsman;
	}
	public void setDraftsman(String draftsman) {
		this.draftsman = draftsman;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getSailEmblem() {
		return sailEmblem;
	}
	public void setSailEmblem(String sailEmblem) {
		this.sailEmblem = sailEmblem;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getTankSize() {
		return tankSize;
	}
	public void setTankSize(String tankSize) {
		this.tankSize = tankSize;
	}
	public String getHomePort() {
		return homePort;
	}
	public void setHomePort(String homePort) {
		this.homePort = homePort;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getWaterTankSize() {
		return waterTankSize;
	}
	public void setWaterTankSize(String waterTankSize) {
		this.waterTankSize = waterTankSize;
	}
	public String getYachtClub() {
		return yachtClub;
	}
	public void setYachtClub(String yachtClub) {
		this.yachtClub = yachtClub;
	}
	public String getFlotationDepth() {
		return flotationDepth;
	}
	public void setFlotationDepth(String flotationDepth) {
		this.flotationDepth = flotationDepth;
	}
	public String getSewageWaterTankSize() {
		return sewageWaterTankSize;
	}
	public void setSewageWaterTankSize(String sewageWaterTankSize) {
		this.sewageWaterTankSize = sewageWaterTankSize;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getMastHeight() {
		return mastHeight;
	}
	public void setMastHeight(String mastHeight) {
		this.mastHeight = mastHeight;
	}
	public String getMainSailSize() {
		return mainSailSize;
	}
	public void setMainSailSize(String mainSailSize) {
		this.mainSailSize = mainSailSize;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getGenoaSize() {
		return genoaSize;
	}
	public void setGenoaSize(String genoaSize) {
		this.genoaSize = genoaSize;
	}
	public String getCallSign() {
		return callSign;
	}
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	public String getRigKind() {
		return rigKind;
	}
	public void setRigKind(String rigKind) {
		this.rigKind = rigKind;
	}
	public String getSpiSize() {
		return spiSize;
	}
	public void setSpiSize(String spiSize) {
		this.spiSize = spiSize;
	}
}
