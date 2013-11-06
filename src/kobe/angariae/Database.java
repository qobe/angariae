package kobe.angariae;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class Database{
	SQLiteDatabase db;
	String dbPath = "/MEDIAYEAH/";
	public Database(){
		SQLiteDatabase.openDatabase(dbPath, null, db.CREATE_IF_NECESSARY);
	}
}
