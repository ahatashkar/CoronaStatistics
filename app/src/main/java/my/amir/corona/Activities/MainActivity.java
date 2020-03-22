package my.amir.corona.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import my.amir.corona.Adapter.CountryAdapter;
import my.amir.corona.Global;
import my.amir.corona.JsonClasses.Countries.CountriesResponse;
import my.amir.corona.JsonClasses.Countries.Country;
import my.amir.corona.R;
import my.amir.corona.Retrofit.CallbackHandler;
import my.amir.corona.Retrofit.Services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about:
                //add the function to perform here
                Toast.makeText(MainActivity.this,"about us",Toast.LENGTH_SHORT).show();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryAdapter(new ArrayList<Country>());
        recyclerView.setAdapter(adapter);

        try {
            getSupportActionBar().setTitle("Analytics");
        }catch (Exception e){
            e.printStackTrace();
        }

        getCountries();


    }

    void getCountries() {

        Services service = Global.retrofit.create(Services.class);
        Call<ResponseBody> call = service.getCountries();

        call.enqueue(new CallbackHandler<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                String string;
                Gson gson = new Gson();

                CountriesResponse countriesResponse;

                try {
                    string = response.body().string();
                    countriesResponse = gson.fromJson(string, CountriesResponse.class);

                    adapter.updateList(countriesResponse.getCountries_stat());

                    adapter.setOnItemMoreClickListener(new CountryAdapter.onMoreClickListener() {
                        @Override
                        public void onClickListener(View view, int position) {

                            try {

                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("countryName", adapter.getList().get(position).getCountry_name());
                                startActivity(intent);

                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });


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
