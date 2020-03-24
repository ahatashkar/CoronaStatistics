package my.amir.corona.JsonClasses.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import my.amir.corona.JsonClasses.Details.CountryDetail;

public class HistoryResponse implements Serializable {

    @SerializedName("stat_by_country")
    @Expose
    List<CountryDetail> stat_by_country;

    public void setStat_by_country(List<CountryDetail> stat_by_country) {
        this.stat_by_country = stat_by_country;
    }

    public List<CountryDetail> getStat_by_country() {
        return stat_by_country;
    }
}
