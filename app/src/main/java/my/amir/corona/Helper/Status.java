package my.amir.corona.Helper;

public class Status {

    public enum graphStatus{
        cases,
        death,
        active,
        recovered
    }

    public static graphStatus getGraphStatus(String status){
        if(status.equalsIgnoreCase("Total cases"))
            return graphStatus.cases;
        else if(status.equalsIgnoreCase("Total active"))
            return graphStatus.active;
        else if(status.equalsIgnoreCase("Total death"))
            return graphStatus.death;
        else if(status.equalsIgnoreCase("Total recovered"))
            return graphStatus.recovered;
        else
            return null;
    }
}
