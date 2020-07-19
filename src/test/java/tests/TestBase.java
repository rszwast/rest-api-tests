package tests;

import authentication.AppAuthentication;
import filters.LoggingFilters;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;

import static navigation.AppUrls.BASE_URL;
import static navigation.AppUrls.SALE_PATH;

public class TestBase {

    @BeforeClass
    public void setupConfig(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = SALE_PATH;
        LoggingFilters.getLoggingFilters();
        RestAssured.requestSpecification = new RequestSpecBuilder().setAccept("application/vnd.allegro.public.v1+json").build();
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + AppAuthentication.getAccessToken()).build();
    }
}
