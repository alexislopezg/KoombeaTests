import api.CharacterService;
import api.EpisodeService;
import api.LocationService;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected CharacterService characterService;
    protected EpisodeService episodeService;
    protected LocationService locationService;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://rickandmortyapi.com/api";

        characterService = new CharacterService();
        episodeService = new EpisodeService();
        locationService = new LocationService();
    }
}
