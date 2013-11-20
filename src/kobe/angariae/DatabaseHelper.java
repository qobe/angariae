package kobe.angariae;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "msconns.db";
	public static final String TABLE_NAME = "Connections";
	public static final String LABEL = "Label";
	public static final String SERVER_ADDRESS = "ServerAddress";
	public static final String TYPE = "Type";
	public static final String USER_NAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String[] FIELDS = {"Label","ServerAddress","Type","Username","Password"};
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(String.format("CREATE TABLE %s (%s TEXT,  %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
				TABLE_NAME, LABEL, SERVER_ADDRESS, TYPE, USER_NAME, PASSWORD));
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
