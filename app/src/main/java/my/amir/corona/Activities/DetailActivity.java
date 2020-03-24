package my.amir.corona.Activities;

import androidx.appcompat.app.AppCompatActivity;
import my.amir.corona.Global;
import my.amir.corona.Helper.Helper;
import my.amir.corona.Helper.Status;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    Spinner spinner;

    ProgressDialog dialog;

    String countryName = null;
    List<CountryDetail> list;

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
        spinner = findViewById(R.id.spinner);

        main_linearLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.graphItem_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
         String type = (String) parent.getItemAtPosition(pos);
         Status.graphStatus status = Status.getGraphStatus(type);

         if(status != null && list != null){
             graphPlot(status);
         }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

                    fillHistoryList(historyResponse.getStat_by_country());


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

    void fillHistoryList(List<CountryDetail> originList){

        String day = "";
        list = new ArrayList<>();

        for(int i=0; i<originList.size(); i++){
            CountryDetail current = originList.get(i);
            String currentDay = current.getRecord_date().substring(8,10);

            if(!currentDay.equalsIgnoreCase(day)){
                day = currentDay;
                list.add(current);
            }
        }

        graphPlot(Status.graphStatus.cases);


    }

    void graphPlot(Status.graphStatus status){

        Date firstDate = Helper.getDate(list.get(0).getRecord_date());
        Date lastDate = Helper.getDate(list.get(list.size()-1).getRecord_date());

        double firstY;
        double lastY;

        switch (status){
            case cases:
                firstY = Helper.getFormattedString(list.get(0).getTotal_cases());
                lastY = Helper.getFormattedString(list.get(list.size()-1).getTotal_cases());
                break;

            case death:
                firstY = Helper.getFormattedString(list.get(0).getTotal_deaths());
                lastY = Helper.getFormattedString(list.get(list.size()-1).getTotal_deaths());
                break;

            case active:
                firstY = Helper.getFormattedString(list.get(0).getActive_cases());
                lastY = Helper.getFormattedString(list.get(list.size()-1).getActive_cases());
                break;

            case recovered:
                firstY = Helper.getFormattedString(list.get(0).getTotal_recovered());
                lastY = Helper.getFormattedString(list.get(list.size()-1).getTotal_recovered());
                break;
        }

        DataPoint[] dataPoints = new DataPoint[list.size()];

        for(int i=0; i<list.size(); i++){
            CountryDetail current = list.get(i);
            Date date = Helper.getDate(current.getRecord_date());
            if(date != null) {
                DataPoint point = null;

                switch (status){
                    case recovered:
                        point = new DataPoint(date, Helper.getFormattedString(current.getTotal_recovered()));
                        break;

                    case active:
                        point = new DataPoint(date, Helper.getFormattedString(current.getActive_cases()));
                        break;

                    case death:
                        point = new DataPoint(date, Helper.getFormattedString(current.getTotal_deaths()));
                        break;

                    case cases:
                        point = new DataPoint(date, Helper.getFormattedString(current.getTotal_cases()));
                        break;
                }

                dataPoints[i] = point;
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        graph.removeAllSeries();
        graph.addSeries(series);

        graph.setTitle("Total "+status.toString());

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(DetailActivity.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(list.size());
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(135);
        graph.getGridLabelRenderer().setLabelHorizontalHeight(150);
//
        graph.getViewport().setMinX(firstDate.getTime());
        graph.getViewport().setMaxX(lastDate.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

//        graph.getViewport().setMinY(firstY);
//        graph.getViewport().setMaxY(lastY);
//        graph.getViewport().setYAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(true);
    }
}
