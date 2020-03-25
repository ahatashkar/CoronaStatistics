package my.amir.corona.JsonClasses.Countries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {

    @SerializedName("country_name")
    @Expose
    String country_name;

    @SerializedName("cases")
    @Expose
    String cases;

    @SerializedName("deaths")
    @Expose
    String deaths;

    @SerializedName("total_recovered")
    @Expose
    String total_recovered;

    @SerializedName("new_deaths")
    @Expose
    String new_deaths;

    @SerializedName("new_cases")
    @Expose
    String new_cases;

    @SerializedName("serious_critical")
    @Expose
    String serious_critical;

    @SerializedName("active_cases")
    @Expose
    String active_cases;

    @SerializedName("total_cases_per_1m_population")
    @Expose
    String total_cases_per_1m_population;

    public Integer numRecovered;
    public Integer numCases;
    public Integer numDeath;

    public Integer getNumRecovered() {
        return numRecovered;
    }

    public Integer getNumCases() {
        return numCases;
    }

    public Integer getNumDeath() {
        return numDeath;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTotal_recovered() {
        return total_recovered;
    }

    public void setTotal_recovered(String total_recovered) {
        this.total_recovered = total_recovered;
    }

    public String getNew_deaths() {
        return new_deaths;
    }

    public void setNew_deaths(String new_deaths) {
        this.new_deaths = new_deaths;
    }

    public String getNew_cases() {
        return new_cases;
    }

    public void setNew_cases(String new_cases) {
        this.new_cases = new_cases;
    }

    public String getSerious_critical() {
        return serious_critical;
    }

    public void setSerious_critical(String serious_critical) {
        this.serious_critical = serious_critical;
    }

    public String getActive_cases() {
        return active_cases;
    }

    public void setActive_cases(String active_cases) {
        this.active_cases = active_cases;
    }

    public String getTotal_cases_per_1m_population() {
        return total_cases_per_1m_population;
    }

    public void setTotal_cases_per_1m_population(String total_cases_per_1m_population) {
        this.total_cases_per_1m_population = total_cases_per_1m_population;
    }




}
