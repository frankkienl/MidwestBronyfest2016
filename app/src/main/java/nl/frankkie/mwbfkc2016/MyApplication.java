package nl.frankkie.mwbfkc2016;

import android.app.Application;
import android.util.Log;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by FrankkieNL on 21-12-2014.
 */
@ReportsCrashes(
        //formKey = "", // This is required for backward compatibility but not used
        formUri = "http://wofje.8s.nl:5984/acra-mwbfkc2016/_design/acra-storage/_update/report", //due to a bug in ACRA, url has to be set now, cannot be done later.
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin = "", //SECRET to be set later
        formUriBasicAuthPassword = "" //SECRET to be set later
)
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v("mwbfkc2016","MyApplication onCreate");

        //Crash reports
        //Set the Secret Password
        SecretAcraKey.providePassword(this);
    }
}