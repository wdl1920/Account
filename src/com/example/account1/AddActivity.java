package com.example.account1;

import java.net.DatagramPacket;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.w3c.dom.Text;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity{
	
	private EditText reason1 ;
	private EditText result1 ;
	private Button yes ;
	private Button no ;
	private Accountdb accountdb ;
	private Cursor mCursor; 
	private TextView date ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout) ;
		init() ;
		getCurrentDate() ;
		//ȷ�ϲ���
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add() ;
			}
		});
		
		
		//ȡ������
        no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent() ;
				intent.setClass(AddActivity.this, MainActivity.class) ;
				startActivity(intent) ;
			}
		});
	}
	
	public void init()
	{
		accountdb = new Accountdb(this); 
		mCursor = accountdb.select(); 
		reason1 = (EditText)findViewById(R.id.reason1) ;
		result1 = (EditText)findViewById(R.id.result1) ;
		yes = (Button)findViewById(R.id.yes) ;
		no = (Button)findViewById(R.id.no) ;
		date = (TextView)findViewById(R.id.date) ;
	}
	
	public void add()
	{
		String rea = reason1.getText().toString(); 
		String res = result1.getText().toString(); 
		String dat = date.getText().toString() ;
		//���������߶�����Ϊ�գ������˳� 
		if (rea.equals("") || res.equals("") || dat.equals("")){ 
		return; 
		} 
		accountdb.insert(rea, res ,dat); 
		mCursor.requery(); 
		Toast.makeText(this, "Add Successed!",Toast.LENGTH_SHORT).show(); 
		Intent intent = new Intent() ;
		intent.setClass(AddActivity.this, MainActivity.class) ;
		startActivity(intent) ;
	}
	
	public void getCurrentDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");     
		Date curDate = new Date(System.currentTimeMillis());//��ȡ��ǰ����    
		String str = formatter.format(curDate);
		date.setText(str) ;
	}
}
