package srt.inz.fsafety;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import srt.inz.fsafety.ApplicationPreference;
import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainPage extends Activity {
	
	EditText eun,epas; String sun,spas,rsp;
	Button blg; ApplicationPreference applicationPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        eun=(EditText)findViewById(R.id.edt_unam);
        epas=(EditText)findViewById(R.id.edt_pass);
        blg=(Button)findViewById(R.id.mbt_login);
        applicationPreference=(ApplicationPreference)getApplication();
        
        
        if(applicationPreference.getLoginStatus()==true)
        {
        	Intent i=new Intent(getApplicationContext(),MainHome.class);
        	startActivity(i);
        	finish();
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Please log in", Toast.LENGTH_SHORT).show();
        }
        blg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				sun=eun.getText().toString();
				spas=epas.getText().toString();
				
				applicationPreference.setId(sun);
						new LoginApiTask().execute();	
			}
		});
    }
    
    public class LoginApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	            	
	                urlParameters = "uname=" + URLEncoder.encode(sun, "UTF-8") + "&&"
	                        + "pass=" + URLEncoder.encode(spas, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        rsp = Connectivity.excutePost(Constants.LOGIN_URL,
	                    urlParameters);
	            Log.e("You are at", "" + rsp);

	       return rsp;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	          
	        if(rsp.contains("success"))
	        {
	        	applicationPreference.setLoginStatus(true);
	        Toast.makeText(getApplicationContext(), ""+rsp, Toast.LENGTH_SHORT).show();
	        Intent i=new Intent(getApplicationContext(),MainHome.class);
	        startActivity(i);
	        finish();
	        
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+rsp, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	   
	}
    
}
