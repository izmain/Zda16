package com.den.zda;


import android.app.*;
import android.content.*;
import android.database.sqlite.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import android.view.*;
import android.util.*;

public class ListOperation extends Activity implements OnClickListener
{

	
	
	EditText etOfList;
	ListView lvTime;
	Button btAdd,btDel,btChang,btSave;
	
	DBhelper dbHelp;
	
	
	ArrayList<String> timeList=new ArrayList<String> ();
	private SharedPreferences itemTimeOfTask; //для записи временных параметров
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listop);
		etOfList=(EditText) findViewById(R.id.etOfList);
		lvTime=(ListView) findViewById(R.id.lvTime);
		initz();
		// 
	}

	private void initz()
	{
		// TODO: Implement this method
		itemTimeOfTask=getSharedPreferences("fileSetting" ,Activity.MODE_PRIVATE);
		Intent intnTemp=getIntent();
		etOfList.setText(intnTemp.getStringExtra("time"));
		loadTimeSheets();
		ArrayAdapter<String> timeAdapt= new ArrayAdapter<String>(this,R.layout.my_item,timeList);
		lvTime.setAdapter(timeAdapt);
		
	}
	
	//---------------------------
	//     ОБРАБОТЧИКИ НАЖАТИЙ
	//---------------------------
	
	@Override
	public void onClick(View v)
	{
		ContentValues cv= new ContentValues();
		SQLiteDatabase db= dbHelp.getWritableDatabase();
		
		switch( v.getId()){
			case R.id.bt_add:
				{
					cv.put("name",etOfList.getText().toString());
					
				}
		}
		// TODO: Implement this method
	}
	
	//-------------------------------
	//         МОИ ФУНКЦИИ
	//--------------------------------
	   
	//запись ключа в настройки по формату
	public void saveKey(int nomer,String section, String val)
	{
		String key=section + String.valueOf(nomer);

		itemTimeOfTask.edit().putString(key ,val).commit();
	}

	//чтение ключа по номеру
	public String reedKey(int nomer, String section)
	{
		String key=section + String.valueOf(nomer);

		return(itemTimeOfTask.getString(key,""));
	}

	//извлечение ячейки времени
	private String reedTime(int k)
	{	
		int n=getSizeTimeSection();
		if (k<=n){return(reedKey(k,"time" ));}
		else{return("хуйня "+k+"-"+n);}	
	}

	//получение размера массива дат
	private int getSizeTimeSection()
	{
		int n=0;
		String sizeTimeSection = reedKey(0,"time");
		if (!("".equals(sizeTimeSection))) 
		{n=Integer.valueOf(sizeTimeSection);}
		return n;
	}

	//загрузка массива дат
	private void loadTimeSheets()
	{
		int n=getSizeTimeSection();
		if (0==n) {return;}

		timeList.clear();
		for (int k=1; k<=n; k++)
		{

			timeList.add(reedKey(k, "time"));
		}
	}

	//сохранение массива дат
	private void saveTimeSheets()
	{
		int i=timeList.size();
		for (int n=1; n<=i; n++) 
		{
			saveKey(n,"time",timeList.get(n));
		}
	}
	
	//-------------------------------
	//         DEBAG
	//--------------------------------
	
	public class DBhelper extends SQLiteOpenHelper
	{
		public DBhelper(Context context)
		{
			super (context, "taskDB", null, 1 );
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			//SQLiteDatabase db=DBhelper.getWritableDatabase();
			Log.d("mzda","creat db");
			db.execSQL("");
			
			// TODO: Implement this method
		}

		@Override
		public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
		{
			// TODO: Implement this method
		}

	}

	
	
}

