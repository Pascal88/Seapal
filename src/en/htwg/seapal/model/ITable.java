package en.htwg.seapal.model;

import java.lang.reflect.InvocationTargetException;

import android.database.Cursor;

public interface ITable {
	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "SeaPal";
	public static final String DATABASE_PATH = "/data/data/en.htwg.seapal/";
	public void Create();
	public Cursor select(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException;
	public void insert(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException;
	public int update(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException;
	public void delete(Object o) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException;
}
