package api;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EpisodeService {

    public Response getMultipleEpisodes(String id, String id2) {
        return given()
                .pathParam("id", id)
                .pathParam("id2", id2)
                .get("/episode/{id},{id2}", id, id2);
    }

    public Response getEpisode(String id) {
        return given()
                .pathParam("id", id)
                .get("/episode/{id}", id);

    }

    public Response getAllEpisodes() {
        return given()
                .get("/episode/");

    }

    public Response getEpisodesWithFilters(String name, String episode) {
        Map<String, String> queryParams = new HashMap<>();

        if (isNotNullOrEmpty(name)) queryParams.put("name", name);
        if (isNotNullOrEmpty(episode)) queryParams.put("episode", episode);

        return given()
                .queryParams(queryParams)
                .when()
                .get("/episode/");
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
