package ca.campbell.toggleenfr;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Resources res;
    DisplayMetrics dm;
    Configuration conf;
    TextView current;
    public static final String EN_CA = "en_CA";
    public static final String FR_CA = "fr_CA";
    public static final String TAG  = "DEVCONF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getApplicationContext().getResources();
        dm = res.getDisplayMetrics();
        conf = getApplicationContext().getResources().getConfiguration();
        current = (TextView)findViewById(R.id.current);
        setCurrent();
        Log.d(TAG,"Current SDK " + Build.VERSION.SDK_INT);
    }
    protected void setCurrent() {
        String msg;
        msg = getResources().getString(R.string.current);
        if (Build.VERSION.SDK_INT >= 24) {
            // getLocales() / setLocales() only api level 24 & up
            msg += " " + conf.getLocales().get(0);
        } else {

            msg += " " + res.getString(R.string.current) + " " + conf.locale;
        }
        Log.d(TAG, msg);
        current.setText(msg);
    }
    public void setEnglish(View v) {
        setTheLocale(EN_CA);
    }
    public void setTheLocale(String lang)  {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT < 24 ) {
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
        } else {
            conf.setLocale(locale);
        }
        Log.d(TAG, "set locale to "+lang);
        setCurrent();
        recreate();   //Activity reload
    }
    public void setFrancais(View v) {
        setTheLocale(FR_CA);
    }
    public void setOtherLanguage(View v) {
        startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
    }

    public void onConfigurationChanged(Configuration config)
    {
        Log.d(TAG, "onConfigurationChanged()");

        super.onConfigurationChanged(config);
    }

}
