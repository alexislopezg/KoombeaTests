import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CharacterTest extends BaseTest {

    @Test
    void givenValidCharacterId_whenRequestCharacter_thenResponseShouldBeReturnValidCharacterData() {
        characterService.getCharacterById("1")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("name", equalTo("Rick Sanchez"))
                .body("status", equalTo("Alive"))
                .body("species", equalTo("Human"))
                .body("gender", equalTo("Male"));
    }

    @Test
        //Check
    void givenInvalidCharacterId_whenRequestCharacter_thenResponseShouldBeReturnErrorMessage() {
        characterService.getCharacterById("-1")
                .then()
                .statusCode(404)
                .body("error", equalTo("Character not found"));
    }

    @Test
    void givenValidCharacterIds_whenRequestCharacter_thenResponseShouldBeReturneValidCharacterData() {
        characterService.getMultipleCharactersById("1", "2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("Rick Sanchez"))
                .body("[1].id", equalTo(2))
                .body("[1].name", equalTo("Morty Smith"))
                .body("results.size()", equalTo(2));
    }

    @Test
    void givenPageNumber_whenRequestCharactersByPage_thenResponsShouldReturnCharactersOnSelectedPage() {
        characterService.getCharacterPage("10")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("info.prev", containsString("page=9"))
                .body("info.next", containsString("page=11"))
                .body("results.size()", equalTo(20));
    }

    @Test
    void givenInvalidPageNumber_whenRequestCharactersByPage_thenResponseShouldReturnFirstPage() {
        characterService.getCharacterPage("0")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("info.prev", nullValue())
                .body("info.next", containsString("page=2"));
    }


    @Test
    void givenNoFilters_whenRequestAllCharacters_thenResponseShouldReturnAllCharacters() {
        characterService.getAllCharacters()
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("results[0].status", anyOf(equalTo("Alive"), equalTo("Dead"),
                        equalTo("unknown")))
                .body("results[0].gender", anyOf(equalTo("Male"), equalTo("Female"),
                        equalTo("Genderless"), equalTo("unknown")));
    }

    @Test
    void givenValidFilters_whenRequestCharactersWithFilters_thenResponseShouldReturnAtLeastOneResult() {
        characterService.getCharactersWithFilters("Chris", "Dead", "Alien", "Organic gun", "unknown")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("results.size()", greaterThan(0))
                .body("results[0].name", containsStringIgnoringCase("Chris"));
    }

    @Test
    void givenValidFilter_whenRequestCharactersWithFilters_thenResponseShouldReturnAtLeastOneResult() {
        characterService.getCharactersWithFilters("Rick", "", "", "", "")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Cache-Control", notNullValue())
                .body("results.size()", greaterThan(0))
                .body("results[0].name", containsStringIgnoringCase("Rick"));
    }

    @Test
    void givenNonMatchingFilters_whenRequestCharactersWithFilters_thenResponseShouldReturnErrorMessage() {
        characterService.getCharactersWithFilters("Test", "Dead", "", "", "")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("There is nothing here"));
    }
}
