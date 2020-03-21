package my.amir.corona.Retrofit;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CallbackHandler<T> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        int code = response.code();

        if (response.isSuccessful() && response.body() != null) {

            onSuccess(response);

        } else{

            if(response.errorBody() != null) {
                Gson gson = new Gson();

                String errorBody = null;

                try {
                    errorBody = response.errorBody().string();
                } catch (Exception e){
                    e.printStackTrace();
                }


                onError(errorBody);

            }


        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    public void onSuccess(Response<T> response) {

    }

    public void onError(String errorResponse){

    }


}


