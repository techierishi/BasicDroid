package in.thelattice.gluconnect.models;

/**
 * Created by Ishan on 03-11-2015.
 */
public class PatientInsulinRowItem {

    private String glucose_reading;
    private String time_of_glucose_reading;
    private String feed_status;


    public String getGlucose_reading() {
        return glucose_reading;
    }

    public void setGlucose_reading(String glucose_reading) {
        this.glucose_reading = glucose_reading;
    }

    public String getTime_of_glucose_reading() {
        return time_of_glucose_reading;
    }

    public void setTime_of_glucose_reading(String time_of_glucose_reading) {
        this.time_of_glucose_reading = time_of_glucose_reading;
    }

    public String getFeed_status() {
        return feed_status;
    }

    public void setFeed_status(String feed_status) {
        this.feed_status = feed_status;
    }
}
