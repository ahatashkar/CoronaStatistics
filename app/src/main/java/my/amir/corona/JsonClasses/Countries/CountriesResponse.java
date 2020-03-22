package my.amir.corona.JsonClasses.Countries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CountriesResponse implements Serializable {

    @SerializedName("countries_stat")
    @Expose
    List<Country> countries_stat;

    public void setCountries_stat(List<Country> countries_stat) {
        this.countries_stat = countries_stat;
    }

    public List<Country> getCountries_stat() {
        return countries_stat;
    }
}
