import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class LocationTest extends BaseTest {

    @Test
    void givenValidLocationId_whenRetrieveLocation_thenLocationIsReturned() {
        locationService.getLocation("1")
                .then()
                .assertThat()
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("name", equalTo("Earth (C-137)"))
                .body("type", equalTo("Planet"))
                .body("dimension", equalTo("Dimension C-137"))
                .body("residents.size()", greaterThan(0));
    }

    @Test
    void givenInvalidLocationId_whenRequestLocation_thenLocationShouldNotBeFound() {
        locationService.getLocation("-1")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Location not found"));
    }

    @Test
    void givenMultipleLocationIds_whenRequestMultipleLocations_thenResponseShouldBeReturnedBasedOnValidDataIds() {
        locationService.getMultipleLocations("1", "2")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("[0].name", equalTo("Earth (C-137)"))
                .body("[1].name", equalTo("Abadango"))
                .body("results.size()", equalTo(2));
    }

    @Test
    void givenNoFilters_whenRequestAllLocations_thenResponseShouldReturnAllLocations() {
        locationService.getAllLocations()
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("results[0].id", notNullValue())
                .body("results[0].name", notNullValue())
                .body("results[0].type", notNullValue())
                .body("results[0].dimension", notNullValue())
                .body("results[0].residents", not(empty()))
                .body("info.count", equalTo(126));
    }

    @Test
    void givenValidFilters_whenRequestLocations_thenResponseShouldBeFiltered() {
        locationService.getLocationsWithFilters("Earth (C-137)", "Planet", "Dimension C-137")
                .then()
                .assertThat()
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("results[0].name", equalTo("Earth (C-137)"))
                .body("results[0].type", equalTo("Planet"))
                .body("results[0].dimension", equalTo("Dimension C-137"));
    }

    @Test
    void givenNonMatchingFilters_whenRequestLocationWithFilters_thenResponseShouldReturnErrorMessage() {
        locationService.getLocationsWithFilters("asdf", "asdf", "")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("There is nothing here"));
    }
}
