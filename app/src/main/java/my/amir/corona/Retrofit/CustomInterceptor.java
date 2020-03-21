package my.amir.corona.Retrofit;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class CustomInterceptor {

    public static OkHttpClient getOkHTTPClient() {

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new okhttp3.Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request().newBuilder()
                                .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                                .addHeader("x-rapidapi-key", "e322792e4fmshadf420c54117d8dp113cbajsn123559557986")
                                .build();

                        return chain.proceed(request);
                    }
                });



        return client.build();

    }


}
