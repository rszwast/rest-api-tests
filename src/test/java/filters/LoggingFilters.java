package filters;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class LoggingFilters {

    public static final void getLoggingFilters() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

}
