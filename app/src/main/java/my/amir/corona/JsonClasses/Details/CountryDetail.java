package my.amir.corona.JsonClasses.Details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryDetail implements Serializable {

    @SerializedName("country_name")
    @Expose
    String country_name;

    @SerializedName("total_cases")
    @Expose
    String total_cases;

    @SerializedName("new_cases")
    @Expose
    String new_cases;

    @SerializedName("active_cases")
    @Expose
    String active_cases;

    @SerializedName("total_deaths")
    @Expose
    String total_deaths;

    @SerializedName("new_deaths")
    @Expose
    String new_deaths;

    @SerializedName("total_recovered")
    @Expose
    String total_recovered;

    @SerializedName("total_cases_per1m")
    @Expose
    String total_cases_per1m;

    @SerializedName("record_date")
    @Expose
    String record_date;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getTotal_cases() {
        return total_cases;
    }

    public void setTotal_cases(String total_cases) {
        this.total_cases = total_cases;
    }

    public String getNew_cases() {
        return new_cases;
    }

    public void setNew_cases(String new_cases) {
        this.new_cases = new_cases;
    }

    public String getActive_cases() {
        return active_cases;
    }

    public void setActive_cases(String active_cases) {
        this.active_cases = active_cases;
    }

    public String getTotal_deaths() {
        return total_deaths;
    }

    public void setTotal_deaths(String total_deaths) {
        this.total_deaths = total_deaths;
    }

    public String getNew_deaths() {
        return new_deaths;
    }

    public void setNew_deaths(String new_deaths) {
        this.new_deaths = new_deaths;
    }

    public String getTotal_recovered() {
        return total_recovered;
    }

    public void setTotal_recovered(String total_recovered) {
        this.total_recovered = total_recovered;
    }

    public String getTotal_cases_per1m() {
        return total_cases_per1m;
    }

    public void setTotal_cases_per1m(String total_cases_per1m) {
        this.total_cases_per1m = total_cases_per1m;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }


}
