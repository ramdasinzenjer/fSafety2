package srt.inz.fsafety;


import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi") public class FireNotificationService extends Service{

	ApplicationPreference applicationPreference;	String response;
	
	private MyThread mythread;
    public boolean isRunning = false; String svhid,parseresult;
    private static String TAG = FireNotificationService.class.getSimpleName();

	public FireNotificationService() {
		super();
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
		applicationPreference=(ApplicationPreference)getApplication();
		 mythread  = new MyThread();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		super.onStart(intent, startId);
			
			if(!isRunning){
	            mythread.start();
	            isRunning = true;
	        }
		Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		
	}
	
	class MyThread extends Thread{
        static final long DELAY = 10000;
        @Override
        public void run(){          
            while(isRunning){
                Log.d("Service","Running");
                try {                   
                	new RequestfetchApiTask().execute();
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    isRunning = false;
                    e.printStackTrace();
                }
            }
        }
         
    }

	public class RequestfetchApiTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters = null;
	        try {
	        	urlParameters="";
	            		
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        response = Connectivity.excutePost(Constants.REQUESTGETTER_URL,
	                urlParameters);
	        Log.e("You are at", "" + response);

	        return response;
			
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(String s) {
			// TODO Auto-generated method stub
			super.onPostExecute(s);
			if(response.contains("success"))
            {	                     
             //Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
              	noti_parser(response);   
            }
			else
			{
				Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
			}
			
		}	
	}

	public void notification(Intent inte,String svd)
	{
		
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); 	
		PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, inte, 0);	
		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the first param to 0
		Notification mNotification = new Notification.Builder(getApplicationContext())		
			.setContentTitle("Fire Safety Alert")
			.setContentText("High temparature warning detected at Trivandum fire section. Staion id: "+svd+ ".")
			.setTicker("New Message Alert!")
			/*.setNumber(++numMessages)*/
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(pIntent)
			.setSound(soundUri)		
			//.addAction(0, "View", pIntent)
			//.addAction(0, "Stop", pIntent)			
			.build();
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		// If you want to hide the notification after it was selected, do the code below
		 mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notificationManager.notify(0, mNotification);
		
		// Toast.makeText(getApplicationContext(), "Message recieved", Toast.LENGTH_LONG).show();
	}
	
	public void noti_parser(String result)
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
				String fid=data1.getString("fid");
				
				String mnotification= fid;
				
				if (temp.equals("high")) {
					
					Intent intentAlarm= new Intent(getBaseContext(), MainHome.class);
					notification(intentAlarm,mnotification);
				} else {

					Log.e("Error", ""+result);
				}
				
			}
		}
			catch (Exception e)         
		{
				System.out.println("Error:"+e);
		}
	}
}
