package srt.inz.connectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";

    //Urls
   // String BASE_URL="http://192.168.1.10/Fire_safety/android_api/";
    String BASE_URL="https://inzenjerfire.000webhostapp.com/?dir=android_api/";
        
    String LOGIN_URL=BASE_URL+"mlogin.php?";   
    String NOTIFICATIONFETCH_URL=BASE_URL+"mNotifications.php?";  
    
    String REQUESTGETTER_URL=BASE_URL+"mNotifications.php?";
     

    //Details
    String SMSSERVICE="telSms";
    String ENUMBER="e_num";
    String ENUMBER1="e_num1";
    String ENUMBER2="e_num2";
    String LOGINSTATUS="LoginStatus";
    String MYLOCATON="MyLocation";
    String USERID="UserID";
    
    //SharedPreference
    String PREFERENCE_PARENT="Parent_Pref";

   
}
