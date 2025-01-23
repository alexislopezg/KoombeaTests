package api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CharacterService {

    public Response getCharacterById(String id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/character/{id}");
    }

    public Response getMultipleCharactersById(String id, String id2) {
        return given()
                .pathParam("id", id)
                .pathParam("id2", id2)
                .get("/character/{id},{id2}", id, id2);
    }

    public Response getAllCharacters() {
        return given()
                .when()
                .get("/character/");
    }

    public Response getCharacterPage(String number) {
        return given()
                .pathParam("number", number)
                .when()
                .get("/character/?page={number}", number);
    }

    public Response getCharactersWithFilters(String name, String status, String species, String type, String gender) {
        Map<String, String> queryParams = new HashMap<>();

        if (isNotNullOrEmpty(name)) queryParams.put("name", name);
        if (isNotNullOrEmpty(status)) queryParams.put("status", status);
        if (isNotNullOrEmpty(species)) queryParams.put("species", species);
        if (isNotNullOrEmpty(type)) queryParams.put("type", type);
        if (isNotNullOrEmpty(gender)) queryParams.put("gender", gender);

        return given()
                .queryParams(queryParams)
                .when()
                .get("/character/");
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

}
