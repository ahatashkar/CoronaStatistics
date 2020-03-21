package my.amir.corona;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

import androidx.multidex.MultiDex;
import my.amir.corona.Retrofit.CustomInterceptor;
import my.amir.corona.Retrofit.Routes;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static my.amir.corona.Global.okHttpClient;


public class Initializer extends Application {

    private static Application instance;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

//        Global.customFont = FontCache.getTypeface("fonts/IRANSansMobile_FaNum.ttf", getApplicationContext());
//        Global.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration configLang = new Configuration();
        configLang.locale = locale;
        getBaseContext().getResources().updateConfiguration(configLang,
                getBaseContext().getResources().getDisplayMetrics());

        okHttpClient = CustomInterceptor.getOkHTTPClient();

        Global.retrofit = new Retrofit.Builder()
            .baseUrl(Routes.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        instance = this;



    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

}
