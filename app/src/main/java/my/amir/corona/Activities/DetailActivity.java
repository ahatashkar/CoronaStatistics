package my.amir.corona.Activities;

import androidx.appcompat.app.AppCompatActivity;
import my.amir.corona.Adapter.CountryAdapter;
import my.amir.corona.Global;
import my.amir.corona.JsonClasses.Countries.CountriesResponse;
import my.amir.corona.JsonClasses.Details.CountryDetail;
import my.amir.corona.JsonClasses.Details.DetailResponse;
import my.amir.corona.JsonClasses.History.HistoryResponse;
import my.amir.corona.R;
import my.amir.corona.Retrofit.CallbackHandler;
import my.amir.corona.Retrofit.Services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    LinearLayout main_linearLayout;
    TextView name_textView;
    TextView cases_textView;
    TextView newCase_textView;
    TextView active_textView;
    TextView death_textView;
    TextView newDeath_textView;
    TextView recovered_textView;
    TextView per1m_textView;
    TextView date_textView;
    GraphView graph;

    ProgressDialog dialog;

    String countryName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        main_linearLayout = findViewById(R.id.main_linearLayout);
        name_textView = findViewById(R.id.name_textView);
        cases_textView = findViewById(R.id.cases_textView);
        newCase_textView = findViewById(R.id.newCase_textView);
        active_textView = findViewById(R.id.active_textView);
        death_textView = findViewById(R.id.death_textView);
        newDeath_textView = findViewById(R.id.newDeath_textView);
        recovered_textView = findViewById(R.id.recovered_textView);
        per1m_textView = findViewById(R.id.per1m_textView);
        date_textView = findViewById(R.id.date_textView);
        graph = findViewById(R.id.graph);

        main_linearLayout.setVisibility(View.GONE);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            countryName = bundle.getString("countryName");
        }

        if(countryName != null) {

            try {
                getSupportActionBar().setTitle(countryName);
            } catch (Exception e){
                e.printStackTrace();
            }

            getCountryDetail();
            getCountryHistory();
        }

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d1,1),
                new DataPoint(d2,4),
                new DataPoint(d3,5),

        });

        graph.addSeries(series);

//        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(DetailActivity.this));
//        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

//        graph.getViewport().setMinX(d1.getTime());
//        graph.getViewport().setMaxX(d3.getTime());
//        graph.getViewport().setXAxisBoundsManual(true);

//        graph.getGridLabelRenderer().setHumanRounding(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    void getCountryDetail() {

        dialog = ProgressDialog.show(DetailActivity.this,"loading","please wait...",false,false);

        Services service = Global.retrofit.create(Services.class);
        Call<ResponseBody> call = service.getLatestStatCountry(countryName);

        call.enqueue(new CallbackHandler<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                String string;
                Gson gson = new Gson();

                DetailResponse detailResponse;

                try {
                    string = response.body().string();
                    detailResponse = gson.fromJson(string, DetailResponse.class);

                    fillData(detailResponse.getLatest_stat_by_country().get(0));


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

    void fillData(CountryDetail detail){

        name_textView.setText(detail.getCountry_name());
        cases_textView.setText(detail.getTotal_cases());
        newCase_textView.setText(detail.getNew_cases());
        active_textView.setText(detail.getActive_cases());
        death_textView.setText(detail.getTotal_deaths());
        newDeath_textView.setText(detail.getNew_deaths());
        recovered_textView.setText(detail.getTotal_recovered());
        per1m_textView.setText(detail.getTotal_cases_per1m());
        date_textView.setText(detail.getRecord_date());

        dialog.dismiss();
        main_linearLayout.setVisibility(View.VISIBLE);

    }

    void getCountryHistory() {

        Services service = Global.retrofit.create(Services.class);
        Call<ResponseBody> call = service.getCountryHistory(countryName);

        call.enqueue(new CallbackHandler<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                String string;
                Gson gson = new Gson();

                HistoryResponse historyResponse;

                try {
                    string = response.body().string();
                    historyResponse = gson.fromJson(string, HistoryResponse.class);


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
