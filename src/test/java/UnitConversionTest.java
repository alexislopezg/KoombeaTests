import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.testng.Assert.assertEquals;

public class UnitConversionTest {

    protected AndroidDriver driver;

    @BeforeTest
    public void setUp() {
        //Setup desired capabilities
        DesiredCapabilities capabilities = getDesiredCapabilities();
        //Connect to the Appium server

        try {
            driver = new AndroidDriver(new URI("http://localhost:4723/").toURL(), capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println("App started");
        System.out.println("Current activity: " + driver.currentActivity());

    }

    @AfterTest
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    private static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:platformVersion", "11.0");
        capabilities.setCapability("appium:appPackage", "com.ba.universalconverter");
        capabilities.setCapability("appium:appActivity", "com.ba.universalconverter.MainConverterActivity");
        capabilities.setCapability("appium:app", "/home/alex/Downloads/PreciseUnitConversion.apk");
        return capabilities;
    }

    @DataProvider(name = "unitConversionData")
    public Object[][] getConversionData() {
        return new Object[][]{
                {Converter.UnitType.LENGTH.getDisplayName(), "Inch", "Nanometer", "9", 228600000},
                {Converter.UnitType.VOLUME.getDisplayName(), "Cup", "Milliliter", "5", 1250},
                {Converter.UnitType.AREA.getDisplayName(), "Square meter", "Square yard", "6", 7.1759},
                {Converter.UnitType.SPEED.getDisplayName(), "Kilometer per hour", "Meter per second", "90", 25}
        };
    }

    //Tests unit conversion for Length, Volume, Area and Speed
    @Test(dataProvider = "unitConversionData")
    void testUnitConversion_WhenGivenValidInput_ShouldReturnCorrectResult(String unitType, String sourceUnit,
                                                                          String destinationUnit, String inputNumber,
                                                                          double expectedResult) {
        var converter = new Converter(driver);

        converter.selectUnitType(unitType)
                .selectSourceUnit(sourceUnit)
                .selectDestinationUnit(destinationUnit)
                .clearInput()
                .tapNumber(inputNumber);

        assertEquals(converter.getConversionResult(), expectedResult,
                "Conversion from " + sourceUnit + " to " + destinationUnit + " failed.");
    }

    @Test
    void testUnitConversionSwitch_WhenGivenValidInput_ShouldReturnTheInitialSourceUnit() {
        var converter = new Converter(driver);

        converter.selectUnitType(Converter.UnitType.VOLUME.getDisplayName())
                .selectSourceUnit("Fluid dram")
                .selectDestinationUnit("Bucket")
                .clearInput()
                .tapNumber("56")
                .switchMeasurements();

        assertEquals(converter.getConversionResult(), 286720);
    }
}
