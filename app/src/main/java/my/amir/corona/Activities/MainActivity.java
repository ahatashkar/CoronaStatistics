package my.amir.corona.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import my.amir.corona.Adapter.CountryAdapter;
import my.amir.corona.Global;
import my.amir.corona.Helper.Helper;
import my.amir.corona.Helper.Status;
import my.amir.corona.JsonClasses.Countries.CountriesResponse;
import my.amir.corona.JsonClasses.Countries.Country;
import my.amir.corona.R;
import my.amir.corona.Retrofit.CallbackHandler;
import my.amir.corona.Retrofit.Services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    SearchView searchView;
    RecyclerView recyclerView;
    CountryAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    LayoutAnimationController animation;
    Spinner spinner;

    CountriesResponse countriesResponse;

    List<Country> filteredList;
    List<Country> searchList;
    boolean isSearch = false;

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
                showAboutDialog();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search);
        recyclerView = findViewById(R.id.my_recycler_view);
        spinner = findViewById(R.id.spinner);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CountryAdapter(new ArrayList<Country>());
        recyclerView.setAdapter(adapter);
        int resId = R.anim.layout_animation_fall_down;
        animation = AnimationUtils.loadLayoutAnimation(MainActivity.this, resId);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.sortItem_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        try {
            getSupportActionBar().setTitle("Analytics");
        }catch (Exception e){
            e.printStackTrace();
        }

        getCountries();

        searchList = new ArrayList<>();


        searchView.setQueryHint("Which country you are looking for?");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do your search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filteredList = new ArrayList<>();

                isSearch = true;

                if(newText.equals("")) {
                    adapter = new CountryAdapter(searchList);
                    recyclerView.setAdapter(adapter);
                    isSearch = false;
                    adapter_itemClickListener();

                    return true;
                }


                for (Country item : searchList) {

                    if (item.getCountry_name().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(item);

                    }

                }

                adapter = new CountryAdapter(filteredList);
                recyclerView.setAdapter(adapter);
                adapter_itemClickListener();

                return false;
            }
        });

    }

    void adapter_itemClickListener(){
        adapter.setOnItemMoreClickListener(new CountryAdapter.onMoreClickListener() {
            @Override
            public void onClickListener(View view, int position) {

                Country country;

                if(isSearch) {
                    country = filteredList.get(position);

                } else {
                    country = searchList.get(position);
                }

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("countryName", country.getCountry_name());
                startActivity(intent);
            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String status = (String) parent.getItemAtPosition(pos);

        Status.status based = Status.getListStatus(status);

        if(based != null && countriesResponse != null)
            fillList(based);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    void getCountries() {

        Services service = Global.retrofit.create(Services.class);
        Call<ResponseBody> call = service.getCountries();

        call.enqueue(new CallbackHandler<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                String string;
                Gson gson = new Gson();


                try {
                    string = response.body().string();
                    countriesResponse = gson.fromJson(string, CountriesResponse.class);

                    for(int i=0; i<countriesResponse.getCountries_stat().size(); i++)
                        searchList.add(countriesResponse.getCountries_stat().get(i));


                    convertStringNumToInt();

                    fillList(Status.status.cases);


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

    void fillList(Status.status based){

        Comparator<Country> countryComparator = null;

        switch (based){
            case cases:
                countryComparator = (Country c1, Country c2) -> c1.getNumCases().compareTo(c2.getNumCases());
                break;

            case death:
                countryComparator = (Country c1, Country c2) -> c1.getNumDeath().compareTo(c2.getNumDeath());
                break;

            case recovered:
                countryComparator = (Country c1, Country c2) -> c1.getNumRecovered().compareTo(c2.getNumRecovered());
                break;
        }

        try {
            Collections.sort(countriesResponse.getCountries_stat(), countryComparator.reversed());
        } catch (Exception e){
            e.printStackTrace();
        }

        adapter = new CountryAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        adapter.updateList(countriesResponse.getCountries_stat());
        recyclerView.setLayoutAnimation(animation);
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


    }

    void convertStringNumToInt(){
        for(int i=0; i<countriesResponse.getCountries_stat().size(); i++){
            Country current = countriesResponse.getCountries_stat().get(i);

            current.numCases = Helper.getFormattedStringToLong(current.getCases());
            current.numDeath = Helper.getFormattedStringToLong(current.getDeaths());
            current.numRecovered = Helper.getFormattedStringToLong(current.getTotal_recovered());
        }
    }

    void showAboutDialog(){

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(true);

        try {

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_about);
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            Window window = dialog.getWindow();
            window.setLayout(width, CardView.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        } catch (Exception e){
            e.printStackTrace();
        }

        dialog.show();

    }

}
