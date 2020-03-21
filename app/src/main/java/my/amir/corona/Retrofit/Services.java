package my.amir.corona.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static my.amir.corona.Retrofit.Routes.affected;
import static my.amir.corona.Retrofit.Routes.allCountries;
import static my.amir.corona.Retrofit.Routes.historyByCountry;

public interface Services {

    @GET(affected)
    Call<ResponseBody> getAffected();

    @GET(historyByCountry)
    Call<ResponseBody> getCountryHistory(@Query("country") String country);

    @GET(allCountries)
    Call<ResponseBody> getCountries();

}
