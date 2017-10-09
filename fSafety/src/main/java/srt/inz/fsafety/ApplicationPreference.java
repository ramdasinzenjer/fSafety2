package srt.inz.fsafety;


import srt.inz.connectors.Constants;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits") public class ApplicationPreference extends Application {
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
String Name,Email,Password,Kidcount,Kidsname,id;

    public String getId() {
         String userid=  appSharedPrefs.getString(Constants.USERID,"");
        return userid;
    }

    public void setId(String id) {
        prefsEditor.putString(Constants.USERID, id);
        prefsEditor.commit();
    }

    Boolean LoginStatus;

    public Boolean getLoginStatus() {
        LoginStatus=   appSharedPrefs.getBoolean(Constants.LOGINSTATUS,false);
        return LoginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        prefsEditor.putBoolean(Constants.LOGINSTATUS,loginStatus);
    }

/*    public String getName() {
        Name=appSharedPrefs.getString(Constants.NAME,"");
        return Name;
    }

    public void setName(String name) {
        prefsEditor.putString(Constants.NAME, name);
        prefsEditor.commit();
    }


    public String getPassword() {

        Password=appSharedPrefs.getString(Constants.PASSWORD,"");
        return Password;
    }

    public void setPassword(String password) {
        prefsEditor.putString(Constants.PASSWORD, password);
        prefsEditor.commit();
    }*/

	@Override
    public void onCreate() {
        super.onCreate();
        this.appSharedPrefs = getApplicationContext().getSharedPreferences(
                Constants.PREFERENCE_PARENT, MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

}
