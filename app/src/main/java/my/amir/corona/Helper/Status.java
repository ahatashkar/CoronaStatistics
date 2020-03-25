package my.amir.corona.Helper;

public class Status {

    public enum status {
        cases,
        death,
        active,
        recovered
    }

    public static status getGraphStatus(String status){
        if(status.equalsIgnoreCase("Total cases"))
            return Status.status.cases;
        else if(status.equalsIgnoreCase("Total active"))
            return Status.status.active;
        else if(status.equalsIgnoreCase("Total death"))
            return Status.status.death;
        else if(status.equalsIgnoreCase("Total recovered"))
            return Status.status.recovered;
        else
            return null;
    }

    public static status getListStatus(String status){
        if(status.equalsIgnoreCase("Cases"))
            return Status.status.cases;
        else if(status.equalsIgnoreCase("Death"))
            return Status.status.death;
        else if(status.equalsIgnoreCase("Recovered"))
            return Status.status.recovered;
        else
            return null;
    }
}
