package tests.categories.and.parameters;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import tests.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class CategoriesIdsAndParametersTests extends TestBase {

    @Test
    public void givenWhenGetCategoriesThenReturnListOfAllCategoriesTest() {
        given()
                .when().get("categories")
                .then().statusCode(200)
                .body(containsString("categories"));
    }

    @Test
    public void givenListOfCategoriesWhenGetExistingCategoryIdThanReturnCategoryTest(){
        Response response = given()
                .when().get("/categories")
                .then().statusCode(200)
                .extract().response();

        String categoryId = response.jsonPath().getString("categories[0].id");
        String categoryName = response.jsonPath().getString("categories[0].name");

        given().pathParam("categoryId", categoryId)
                .when().get("/categories/{categoryId}")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("id", equalTo(categoryId),
                "name", equalTo(categoryName));
    }

    @Parameters({"notExistingCategoryId"})
    @Test
    public void givenListOfCategoriesThanGetNotExistingCategoryThanReturnIdDoesNotExistMessageTest(@Optional("notExistingCategoryId") String notExistingCategoryId) {
        given().pathParam("categoryId", notExistingCategoryId)
                .when().get("/categories/{categoryId}}")
                .then().statusCode(404)
                .body(containsString("not found"));
    }

    @Parameters({"emptyCategoryId"})
    @Test
    public void givenListOfCategoriesThanGetCategoryWithEmptyCategoryIdParameterThanReturnNotFoundMessageTest(@Optional("emptyCategoryId") String emptyCategoryId){
        given()
                .pathParam("categoryId", emptyCategoryId)
                .when().get("/categories/{categoryId}}")
                .then().statusCode(404)
                .body(containsString("not found"));
    }

    @Parameters("parentId")
    @Test
    public void givenCategoryIdWhenGetSubcategoriesThenReturnListOfSubcategoriesTest(@Optional("348") String parentId){
        Response response = given()
                .queryParam("parent.id", parentId)
                .when().get("/categories")
                .then().statusCode(200)
                .extract().response();

        String responseParentId = response.jsonPath().getString("categories[0].parent.id");

        assertEquals("348", responseParentId);
    }

    @Test
    public void givenCategoryIdWhenGetParametersThenReturnListOfParametersForCategoryTesty(){
        Response response = given()
                .when().get("/categories")
                .then().statusCode(200)
                .extract().response();

        String categoryId = response.jsonPath().getString("categories[2].id");
        System.out.println("Category id: " + categoryId);

        given()
                .pathParam("categoryId", categoryId)
                .when().get("/categories/{categoryId}/parameters")
                .then().statusCode(200)
                .body(containsString("parameters"));

    }

    @Parameters({"notExistingParameterId"})
    @Test
    public void givenNotExistingCategoryIdWhenGetParametersThenReturnNotFoundMessage(@Optional("notExistingParameterId") String notExistingParameterId){
        given()
                .pathParam("categoryId", notExistingParameterId)
                .when().get("/categories/{categoryId}/parameters")
                .then().statusCode(404)
                .body(containsString("not found"));
    }

    @Parameters({"emptyParameterId"})
    @Test
    public void givenNEmptyCategoryIdWhenGetParametersThenReturnNotFoundMessage(@Optional("") String emptyParameterId){
        given()
                .pathParam("categoryId", emptyParameterId)
                .when().get("/categories/{categoryId}/parameters")
                .then().statusCode(404)
                .body(containsString("Not found"));
    }
}
