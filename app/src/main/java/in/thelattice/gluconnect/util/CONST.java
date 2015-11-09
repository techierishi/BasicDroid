package in.thelattice.gluconnect.util;

/**
 * Created by techierishi on 8/11/15.
 */
public interface CONST {

    String PREF_NAME = "gluconnect";

    public interface REST_API {
        String BASE_URL = "http://104.236.233.81:8080/GluconnectWebServices/services/GluconnectServices/";
        String API_KEY = "?api_key=gluconnect";
        String LOGIN_URL= "login_user"+API_KEY;
        String GET_PATIENT_LIST = "get_patient_list"+API_KEY;
        String GET_APTIENT_DETAILS = "get_patient_details"+API_KEY;
        String GET_PATIENT_RECORDS="get_patient_records"+API_KEY;
    }
    public interface DB {

        public interface PROFILE {
            String TABLE = "user_profile";
            String USER_ID = "user_id";
            String GENDER = "user_gender";
            String WEIGHT = "user_weight";
            String HEIGHT = "user_height";
            String DOB = "user_dob";


            // Extra
            String USER_NAME = "user_name";
            String FNAME = "f_name";
            String LNAME = "l_name";
            String EMAIL = "user_email";


        }
    }
}
