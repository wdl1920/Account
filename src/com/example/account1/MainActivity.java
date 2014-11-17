package com.example.account1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{
	private ListView listview ;
	private Button add ; //记一笔
	private ArrayList<HashMap<String ,String>> list ;
	private Accountdb accountdb ;
	private Cursor cursor ;
	private TextView yearView ;
	private TextView monthView ;
	private EditText dateEt=null;
	private int year ;
	private int month ;
	private int day ;
	Calendar mycalendar ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init() ;
		showListView() ;
		//初始化Calendar日历对象
        mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date();
        mycalendar.setTime(mydate);
        year = mycalendar.get(Calendar.YEAR);
        month = mycalendar.get(Calendar.MONTH);
        day = mycalendar.get(Calendar.DAY_OF_MONTH);
        yearView.setText(year+"年") ;
        monthView.setText(month+"月") ;
        monthView.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v){
				// TODO Auto-generated method stub
				DatePickerDialog dpd=new DatePickerDialog(MainActivity.this,Datelistener,year,month,day);
                dpd.show();//显示DatePickerDialog组件
			}
		}) ;
		//记一笔的响应事件
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 	Intent intent = new Intent() ;
			 	intent.setClass(MainActivity.this, AddActivity.class) ;
			 	startActivity(intent) ;
			}
		}) ;
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				final HashMap<String, String> map =list.get(position);
				new AlertDialog.Builder(MainActivity.this)    
				.setMessage(map.get("reason") + "-支出-" + map.get("result") + "元")  
				.show();
			}
		});	
	}
	private DatePickerDialog.OnDateSetListener Datelistener=new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int myyear, int monthOfYear,int dayOfMonth) {
            year=myyear;
            month=monthOfYear;
            day=dayOfMonth;
            //更新日期
            updateDate();
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate()
        {
        	yearView.setText(year+"年") ;
            monthView.setText(month+"月") ;
        }
    };
	//初始化
	public void init()  
	{
		accountdb = new Accountdb(this) ;
		cursor = accountdb.select() ;
		listview = (ListView)findViewById(R.id.listview) ;
		add = (Button)findViewById(R.id.add) ;
		yearView = (TextView)findViewById(R.id.yearView) ;
		monthView = (TextView)findViewById(R.id.monthView) ;
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy年");     
		Date curDate = new Date(System.currentTimeMillis());//获取当前日期    
		String str = formatter.format(curDate);
		yearView.setText(str) ;
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("MM月");     
		Date curDate1 = new Date(System.currentTimeMillis());//获取当前日期    
		String str1 = formatter1.format(curDate1);
		monthView.setText(str1) ;	*/
	}
	
	public void showListView()
	{
		list = new ArrayList<HashMap<String,String>>() ;
		String temp = new String() ;
		if(cursor.moveToNext())
		{
			HashMap<String,String> map1 = new HashMap<String, String>() ;
			map1.put("reason", (String) cursor.getString(3).subSequence(8, 10)+"日") ;
			map1.put("result", " ") ;
			list.add(map1) ;
			HashMap<String,String> map = new HashMap<String, String>() ;
			map.put("reason", "   "+cursor.getString(1)) ;
			map.put("result", cursor.getString(2)) ;
			list.add(map) ;
			temp = "" + cursor.getString(3).subSequence(8, 10) ;
		}
		while(cursor.moveToNext())
		{
			if(!(cursor.getString(3).subSequence(8, 10).equals(temp)))
			{
				HashMap<String,String> map1 = new HashMap<String, String>() ;
				map1.put("reason", (String) cursor.getString(3).subSequence(8, 10)+"日") ;
				map1.put("result", " ") ;
				list.add(map1) ;
			}
			HashMap<String,String> map3 = new HashMap<String, String>() ;
			map3.put("reason", "   "+cursor.getString(1)) ;
			map3.put("result", cursor.getString(2)) ;
			temp = ""+cursor.getString(3).subSequence(8, 10) ;
			list.add(map3) ;
		}
		SimpleAdapter adapter=new SimpleAdapter(MainActivity.this, list, R.layout.item1, new String[] {"reason","result"}, new int[]{R.id.reason,R.id.result});
		listview.setAdapter(adapter);	
	}
}
