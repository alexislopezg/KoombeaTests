import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Converter extends Page {
    private final WebDriverWait wait;

    public Converter(AndroidDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AndroidFindBy(id = "com.ba.universalconverter:id/target_value")
    private WebElement conversionResult;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id='com.ba.universalconverter:id/select_unit_spinner'])[1]")
    private WebElement sourceUnit;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id='com.ba.universalconverter:id/select_unit_spinner'])[2]")
    private WebElement destinationUnit;

    public Converter selectUnitType(String unit) {
        driver.findElement(AppiumBy.accessibilityId("Open navigation drawer")).click();

        WebElement selectAppDrawerValue = driver.findElement(AppiumBy.androidUIAutomator(
                String.format("new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"%s\"))", unit)
        ));

        selectAppDrawerValue.click();

        return this;
    }

    public Converter selectSourceUnit(String sourceUnit) {
        wait.until(ExpectedConditions.elementToBeClickable(this.sourceUnit)).click();

        WebElement selectListValue = driver.findElement(AppiumBy.androidUIAutomator(
                String.format("new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"%s\"))", sourceUnit)
        ));

        wait.until(ExpectedConditions.elementToBeClickable(selectListValue)).click();
        return this;
    }

    public Converter selectDestinationUnit(String destinationUnit) {
        wait.until(ExpectedConditions.elementToBeClickable(this.destinationUnit)).click();

        WebElement selectListValue = driver.findElement(AppiumBy.androidUIAutomator(
                String.format("new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"%s\"))", destinationUnit)
        ));

        wait.until(ExpectedConditions.elementToBeClickable(selectListValue)).click();
        return this;
    }

    public Converter tapNumber(String digits) {

        for (char digit : digits.toCharArray()) {
            WebElement numberButton = driver.findElement(AppiumBy.androidUIAutomator(
                    String.format("new UiSelector().text(\"%s\")", digit)
            ));
            wait.until(ExpectedConditions.elementToBeClickable(numberButton)).click();
        }
        return this;
    }

    public double getConversionResult() {
        String resultText = wait.until(ExpectedConditions.visibilityOf(conversionResult)).getText();

        try {
            String cleanResultText = resultText.replace(" ", "");
            return Double.parseDouble(cleanResultText);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse conversion result: " + resultText, e);
        }
    }

    public Converter clearInput() {
        WebElement clearButton = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"C\")"));

        wait.until(ExpectedConditions.elementToBeClickable(clearButton)).click();

        return this;
    }

    public void switchMeasurements() {
        WebElement switchUnits = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().resourceId(\"com.ba.universalconverter:id/img_switch\")"));

        wait.until(ExpectedConditions.elementToBeClickable(switchUnits)).click();
    }
}
