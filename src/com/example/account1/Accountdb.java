package com.example.account1;
import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Accountdb extends SQLiteOpenHelper{
	private final static String DATABASE_NAME = "Account4.db"; 
	private final static int DATABASE_VERSION = 1; 
	private final static String TABLE_NAME = "account_table"; 
	public final static String _ID = "_id"; 
	public final static String REASON = "reason"; 
	public final static String RESULT = "result";
	public final static String _DATE = "_date" ;

	
	public Accountdb(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	//创建table
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + _ID 
				+ " INTEGER primary key autoincrement, " + REASON + " text, "+ RESULT +" text, " + _DATE + " text);"; 
				db.execSQL(sql); 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME; 
		db.execSQL(sql); 
		onCreate(db);
	}
	
	public Cursor select()
	{
		SQLiteDatabase db = this.getReadableDatabase() ;
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null) ;
		return cursor;
		
	}
	//增加操作
	public long insert(String reason ,String result ,String date)
	{
		SQLiteDatabase db = this.getWritableDatabase() ;
		ContentValues cv = new ContentValues() ;
		cv.put(REASON, reason) ;
		cv.put(RESULT, result) ;
		cv.put(_DATE, date) ;
		long row = db.insert(TABLE_NAME, null, cv) ;
		return 0;
	}
	
	//删除操作
	public void delete(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase() ;
		String where = _ID + "= ?" ;
		String[] whereValue = {Integer.toString(id)} ;
		db.delete(TABLE_NAME, where, whereValue) ;
	}
	
	//修改操作 
	public void update(int id, String reason,String result,String date) 
	{ 
		SQLiteDatabase db = this.getWritableDatabase(); 
		String where = _ID + " = ?"; 
		String[] whereValue = { Integer.toString(id) }; 
		ContentValues cv = new ContentValues(); 
		cv.put(REASON, reason); 
		cv.put(RESULT, result); 
		cv.put(_DATE, date) ;
		db.update(TABLE_NAME, cv, where, whereValue); 
	} 
}
