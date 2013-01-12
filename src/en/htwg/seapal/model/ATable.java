package en.htwg.seapal.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class ATable extends SQLiteOpenHelper implements ITable {
	
	public String Table_Create = "";
	public String Table_Name = "";
	protected SQLiteDatabase db;
	
	public ATable(Context activity, Object o) {
		super(activity, DATABASE_NAME, null, DATABASE_VERSION);
		try {
			this.Table_Create = makeCreate(o);
			Create();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public ATable(SQLiteDatabase db, Object o) {
		super(null, DATABASE_NAME, null, DATABASE_VERSION);	
		this.db = db;
		try {
			this.Table_Create = makeCreate(o);
			onCreate(this.db);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void Create() {
		this.db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME + ".sqlite", null);
		onCreate(this.db);
    }

	public void onCreate(SQLiteDatabase db) {
        this.db.execSQL(Table_Create);
    }
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
		onCreate(this.db);
	}
	
	private String makeCreate(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		String create = "CREATE TABLE IF NOT EXISTS ";
		
		Class<?> c = o.getClass();
		String name = c.getName();
		String[] tmp = name.split("\\.");
		this.Table_Name = tmp[tmp.length-1];
		
		create = create + this.Table_Name + " ( ";
		
		Field primary = c.getField("Primary");
		
		if (!Modifier.isStatic(primary.getModifiers())) primary.setAccessible(true);
		
		String primaryKey = primary.get(o).toString();
		create += primaryKey + " INTEGER PRIMARY KEY ASC";
		
		Field[] f = c.getFields();
		
		for(int i = 0; i < f.length; i++) {
			
			if (!Modifier.isStatic(f[i].getModifiers())) f[i].setAccessible(true);
			
			if(f[i].getName().equalsIgnoreCase("Primary") || f[i].getName().equalsIgnoreCase(primaryKey)) continue;
			
			Class<?> clazz = f[i].getType();
			
			if(clazz.equals(Integer.TYPE)){
				create += ", " + f[i].getName() + " NUMERIC DEFAULT 0";
			} else if(clazz.equals(Boolean.TYPE)) {
				create += ", " + f[i].getName() + "NUMERIC DEFAULT 0";
			} else if(clazz.equals(String.class)){
				create += ", " + f[i].getName() + " TEXT";
			} else if(clazz.equals(Date.class)) {
				create += ", " + f[i].getName() + " NUMERIC DEFAULT 0";
			} else if(clazz.equals(Float.TYPE)) {
				create += ", " + f[i].getName() + " REAL DEFAULT 0.0";
			}
		}
		
		create += ")";
		
		return create;
	}
	
	public Cursor select(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		
		Class<?> c = o.getClass();
		Field[] f = c.getFields();
		
		String[] columns = new String[f.length];
		String selection = null;
		int count = 0;
		
		for(int i = 0; i < f.length; i++){
			
			if (!Modifier.isStatic(f[i].getModifiers())) f[i].setAccessible(true);
			
			if(f[i].getName().equalsIgnoreCase("Primary")) continue;
			columns[i] = f[i].getName();
				if(f[i].get(o) != null) {
					if(count == 0) {
						selection = "";
						selection += f[i].getName() + " == " + f[i].get(o).toString();
						count++;
					} else if(count != 0) {
						selection += ", " + f[i].getName() + " == " + f[i].get(o).toString();
						count++;
					}
				}
		}
		
		Cursor cursor = db.query(false,  this.Table_Name, columns, selection, null, null, null, null, null);
		
		//db.close();
		return cursor;
	}
	
	public synchronized void insert(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {	
		
		Class<?> c = o.getClass();
		Field[] f = c.getFields();
		
		Field primary = c.getField("Primary");
		
		if (!Modifier.isStatic(primary.getModifiers())) primary.setAccessible(true);
		
		String primaryKey = primary.get(o).toString();
		ContentValues values = new ContentValues();
		
		for(int i = 0; i < f.length; i++){
			
			if (!Modifier.isStatic(f[i].getModifiers())) f[i].setAccessible(true);
			
			if(f[i].getName().equalsIgnoreCase("Primary") || f[i].getName().equalsIgnoreCase(primaryKey)) continue;
			try {
				f[i].get(o);
			} catch (IllegalAccessException e) {
				continue;
			}
			Class<?> clazz = f[i].getType();
			
			if(clazz.equals(Integer.TYPE)){
				values.put(f[i].getName(), f[i].getInt(o));
			} else if(clazz.equals(Boolean.TYPE)) {
				values.put(f[i].getName(), f[i].getBoolean(o));
			} else if(clazz.equals(String.class)){
				values.put(f[i].getName(), f[i].get(o).toString());
			} else if(clazz.equals(Date.class)) {
				values.put(f[i].getName(), ((Date) f[i].get(o)).getTime());
			} else if(clazz.equals(Float.TYPE)) {
				values.put(f[i].getName(), f[i].getFloat(o));
			}
		}
		
		db.insert(this.Table_Name, null, values);
	    db.close();
	}
	
	public synchronized int update(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Class<?> c = o.getClass();
		Field[] f = c.getFields();
		
		Field primary = c.getField("Primary");
		
		if (!Modifier.isStatic(primary.getModifiers())) primary.setAccessible(true);
		
		String primaryKey = primary.get(o).toString();
		ContentValues values = new ContentValues();
		
		for(int i = 0; i < f.length; i++){
			
			if (!Modifier.isStatic(f[i].getModifiers())) f[i].setAccessible(true);
			
			if(f[i].getName() == "Primary" || f[i].getName() == primaryKey) continue;
			try {
				f[i].get(o);
			} catch (IllegalAccessException e) {
				continue;
			}
			Class<?> clazz = f[i].getType();
			
			if(clazz.equals(Integer.TYPE)){
				values.put(f[i].getName(), f[i].getInt(o));
			} else if(clazz.equals(Boolean.TYPE)) {
				values.put(f[i].getName(), f[i].getBoolean(o));
			} else if(clazz.equals(String.class)){
				values.put(f[i].getName(), f[i].get(o).toString());
			} else if(clazz.equals(Date.class)) {
				values.put(f[i].getName(), f[i].getLong(o));
			}
		}
		return db.update(this.Table_Name, values, c.getField(primaryKey).getName() + " = ?", new String[] {c.getField(primaryKey).get(o).toString()});
	}
	
	public synchronized void delete(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Class<?> c = o.getClass();
		
		Field primary = c.getField("Primary");
		
		if (!Modifier.isStatic(primary.getModifiers())) primary.setAccessible(true);
		
		String primaryKey = primary.get(o).toString();
		
		db.delete(this.Table_Name, c.getField(primaryKey).getName() + " = ?", new String[] {c.getField(primaryKey).get(o).toString()});
	}
	
	public void finalize() {
		db.close();
	}
}
