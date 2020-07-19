package authentication;

import static credentials.ClientCredentials.getClientId;
import static credentials.ClientCredentials.getClientSecret;
import static io.restassured.RestAssured.given;

public class AppAuthentication {

    public static String getAccessToken() {
        String accessToken = given()
                .formParam("grant_type", "client_credentials")
                .auth().preemptive().basic(getClientId(), getClientSecret())
                .contentType("application/x-www-form-urlencoded")
                .when()
                .post("https://allegro.pl/auth/oauth/token")
                .then().extract().jsonPath().getString("access_token");
        return accessToken;
    }
}
