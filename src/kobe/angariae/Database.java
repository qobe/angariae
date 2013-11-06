package kobe.angariae;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class Database{
	public SQLiteDatabase db;
	private final String path = "pathtodb";
	public Database(){
		SQLiteDatabase.openDatabase(path, null, db.CREATE_IF_NECESSARY);
	}
}
