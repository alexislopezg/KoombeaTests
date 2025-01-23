package api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LocationService {
    public Response getMultipleLocations(String id, String id2) {
        return given()
                .pathParam("id", id)
                .pathParam("id2", id2)
                .get("/location/{id},{id2}", id, id2);
    }

    public Response getLocation(String id) {
        return given()
                .pathParam("id", id)
                .get("/location/{id}", id);

    }

    public Response getAllLocations() {
        return given()
                .get("/location/");

    }

    public Response getLocationsWithFilters(String name, String type, String dimension) {
        Map<String, String> queryParams = new HashMap<>();

        if (isNotNullOrEmpty(name)) queryParams.put("name", name);
        if (isNotNullOrEmpty(type)) queryParams.put("status", type);
        if (isNotNullOrEmpty(dimension)) queryParams.put("species", dimension);

        return given()
                .queryParams(queryParams)
                .when()
                .get("/location/");
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}