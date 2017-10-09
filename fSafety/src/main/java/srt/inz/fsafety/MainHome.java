package srt.inz.fsafety;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import srt.inz.fsafety.ApplicationPreference;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainHome extends Activity{
	
	String uid,resdb; ApplicationPreference apppref;
	ListView mlist;
	
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	ListAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainhome);
		mlist=(ListView)findViewById(R.id.list_notif);
		apppref=(ApplicationPreference)getApplication();
		
		uid=apppref.getId();
		startService(new Intent(getApplicationContext(), FireNotificationService.class));
		new NotificationFetchApiTask().execute();
		
	}
	
	public class NotificationFetchApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters=null;
			try {
				urlParameters="status="+ URLEncoder.encode("1", "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resdb = Connectivity.excutePost(Constants.NOTIFICATIONFETCH_URL,
                    urlParameters);
			Log.e("MainHome", resdb);
			return resdb;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			keyparser(resdb);
			Toast.makeText(getApplicationContext(), ""+resdb, Toast.LENGTH_SHORT).show();
		}

}
	

	public void keyparser(String result)
	{
		try
		{
			JSONObject  jObject = new JSONObject(result);
			JSONObject  jObject1 = jObject.getJSONObject("Event");
			JSONArray ja = jObject1.getJSONArray("Details"); 
			int length=ja.length();
			for(int i=0;i<length;i++)
			{
				JSONObject data1= ja.getJSONObject(i);
				String date=data1.getString("date");
				String time=data1.getString("time");
				String temp=data1.getString("temp");
				
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("date", date);
	            map.put("time", time);
	            map.put("temp", temp);
	           	            
	            map.put("notification", "Requested on date : "+date+" "+time+"."+
	            "\n Temparature : "+temp);
	        	            
	            oslist.add(map);
	            
	            adapter = new SimpleAdapter(getApplicationContext(), oslist,
	                R.layout.layout_single,
	                new String[] {"notification"}, new int[] {R.id.mtext_single});
	            mlist.setAdapter(adapter);
	            
	            mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	               
				@Override
	               public void onItemClick(AdapterView<?> parent, View view,
	                                            int position, long id) {               
	               Toast.makeText(getApplicationContext(), 
	            		   " "+oslist.get(+position).get("temp"), Toast.LENGTH_LONG).show();	               
	               }
	                });
			}
		}
			catch (Exception e)         
		{
				System.out.println("Error:"+e);
		}
	}

}
