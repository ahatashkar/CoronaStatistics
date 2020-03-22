package my.amir.corona.JsonClasses.Details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DetailResponse implements Serializable {

    @SerializedName("latest_stat_by_country")
    @Expose
    List<CountryDetail> latest_stat_by_country;

    public void setLatest_stat_by_country(List<CountryDetail> latest_stat_by_country) {
        this.latest_stat_by_country = latest_stat_by_country;
    }

    public List<CountryDetail> getLatest_stat_by_country() {
        return latest_stat_by_country;
    }
}
