import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class RickAndMortyApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://rickandmortyapi.com/api";
    }

    @Test
    void givenApiAvailable_whenRequestCharacter_thenStatusCodeIs200() {
        given()
                .when()
                .get("/character/1")
                .then()
                .statusCode(200)
                .log()
                .status();
    }

    @Test
    void givenApiAvailable_whenRequestInvalidCharacter_thenStatusCodeIs404() {
        given()
                .when()
                .get("/character/-1")
                .then()
                .statusCode(404)
                .log()
                .status();
    }

    @Test
    void givenValidCharacterId_whenRetrieveCharacter_thenResponseContainsExpectedPayload() {
        given()
                .when()
                .get("/character/1")
                .then()
                .assertThat()
                .body("name", equalTo("Rick Sanchez"))
                .body("status", equalTo("Alive"))
                .body("species", equalTo("Human"))
                .body("gender", equalTo("Male"));
    }

    @Test
    void givenApiAvailable_whenRetrieveCharacter_thenResponseHeadersAreValid() {
        Response response = given()
                .when()
                .get("/character/1");
        assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
        assertNotNull(response.getHeader("Cache-Control"));


    }
}
