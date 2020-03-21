package my.amir.corona.Activities;

import androidx.appcompat.app.AppCompatActivity;
import my.amir.corona.Global;
import my.amir.corona.R;
import my.amir.corona.Retrofit.CallbackHandler;
import my.amir.corona.Retrofit.Services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getTransactions();


    }

    void getTransactions() {


        Services service = Global.retrofit.create(Services.class);
        Call<ResponseBody> call = service.getAffected();

        call.enqueue(new CallbackHandler<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                String string;
                Gson gson = new Gson();

//                CategoryServiceResponse categoryServiceResponse;

                try {
                    string = response.body().string();
//                    categoryServiceResponse = gson.fromJson(string, CategoryServiceResponse.class);


                    Log.v("asf","af");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onError(String response) {

                Log.v("error1", response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.v("error1", t.toString());

            }
        });
    }


}
