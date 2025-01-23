import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class EpisodeTest extends BaseTest {

    @Test
    void givenValidEpisodeId_whenRetrieveEpisode_thenResponseShouldReturnValidEpisode() {
        episodeService.getEpisode("1")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("id", equalTo(1))
                .body("name", equalTo("Pilot"))
                .body("episode", equalTo("S01E01"))
                .body("characters", not(empty()));
    }

    @Test
    void givenInvalidEpisodeId_whenRequestEpisode_thenResponseShouldReturnErrorMessage() {
        episodeService.getEpisode("-1")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Episode not found"));
    }

    @Test
    void givenMultipleValidEpisodeIds_whenRequestEpisodes_thenShouldReturnMultipleEpisodes() {
        episodeService.getMultipleEpisodes("1", "2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("Pilot"))
                .body("[1].id", equalTo(2))
                .body("[1].name", equalTo("Lawnmower Dog"))
                .body("results.size()", equalTo(2));
    }

    @Test
    void givenNoFilters_whenRequestAllEpisodes_thenAllEpisodesShouldBeReturned() {
        episodeService.getAllEpisodes()
                .then()
                .assertThat()
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .body("results.size()", equalTo(20))
                .body("info.count", equalTo(51));
    }

    @Test
    void givenMatchingFilters_whenRequestEpisodes_thenShouldReturnResults() {
        episodeService.getEpisodesWithFilters("Pilot", "S01E01")
                .then()
                .assertThat()
                .statusCode(200)
                .body("results.size()", equalTo(1))
                .body("results[0].name", containsString("Pilot"))
                .body("results[0].episode", equalTo("S01E01"));
    }

    @Test
    void givenNonMatchingFilters_whenRequestEpisodes_thenNoMatchingResultsShouldBeFound() {
        episodeService.getEpisodesWithFilters("Pilot", "S01E02")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("There is nothing here"));
    }
}
