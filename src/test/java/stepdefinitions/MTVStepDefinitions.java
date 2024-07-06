package stepdefinitions;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MTVStepDefinitions {
    WebDriver driver;

    @Given("kullanıcı dijital.gib.gov.tr adresine gider")
    public void kullanıcı_dijital_gib_gov_tr_adresine_gider() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().clearResolutionCache().setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://dijital.gib.gov.tr");
    }

    @When("Hesaplamalar tab'ine tıklar")
    public void hesaplamalar_tab_ine_tıklar() {
        driver.findElement(By.id("simple-tab-3")).click();
    }

    @When("Motorlu Taşıtlar Vergisi Hesaplama butonuna tıklar")
    public void motorlu_taşıtlar_vergisi_hesaplama_butonuna_tıklar() {
        WebElement element = driver.findElement(By.cssSelector("img[alt='Motorlu Taşıtlar Vergisi Hesaplama']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        // Switch to the new window
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        // Wait for the new page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://dijital.gib.gov.tr/hesaplamalar/MTVHesaplama"));
    }


    @When("Araç tipi olarak {string} seçer")
    public void araç_tipi_olarak_seçer(String aracTipi) {
        // Wait for the dropdown to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[id='aracTipi']")));
        element.click();

        // Wait for the dropdown options to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='" + aracTipi + "']")));

        // Select the desired option
        WebElement dropdownOption = driver.findElement(By.xpath("//li[text()='" + aracTipi + "']"));
        dropdownOption.click();
    }


    @When("Araç Yaşı olarak {string} seçer")
    public void araç_yaşı_olarak_seçer(String araçYaşı) {
        // Wait for the dropdown to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[id='aracYasi']")));
        element.click();

        // Wait for the dropdown options to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='" + araçYaşı + "']")));

        // Select the desired option
        WebElement dropdownOption = driver.findElement(By.xpath("//li[text()='" + araçYaşı + "']"));
        dropdownOption.click();
    }

    @When("Motor Silindir Hacmi olarak {string} seçer")
    public void motor_silindir_hacmi_olarak_seçer(String motorHacmi) {
        // Wait for the dropdown to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[id='motorSilindirHacmi']")));
        element.click();

        // Wait for the dropdown options to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='" + motorHacmi + "']")));

        // Select the desired option
        WebElement dropdownOption = driver.findElement(By.xpath("//li[text()='" + motorHacmi + "']"));
        dropdownOption.click();
    }

    @When("Hesapla butonuna tıklar")
    public void hesapla_butonuna_tıklar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement hesaplaButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        hesaplaButton.click();
    }

    @Then("gelen sonuç ekranının doğruluğunu kontrol eder")
    public void gelen_sonuç_ekranının_doğruluğunu_kontrol_eder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the result table is visible
        WebElement resultTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".MuiTableBody-root")));

        // Fetch the result values
        List<WebElement> resultValues = resultTable.findElements(By.cssSelector(".MuiTableCell-body"));
        List<String> actualValues = new ArrayList<>();
        for (WebElement value : resultValues) {
            actualValues.add(value.getText());
        }

        // Expected values
        List<String> expectedValues = Arrays.asList("₺8.106,00", "₺4.053,00", "₺4.053,00");

        // Verify that the expected values are present
        assertTrue(actualValues.containsAll(expectedValues));
    }


    @When("Temizle butonuna tıklar")
    public void temizle_butonuna_tıklar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement temizleButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("reset")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", temizleButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", temizleButton);
    }



    @Then("sonuç ekranının silindiğini ve veri alanlarının temizlendiğini kontrol eder")
    public void sonuç_ekranının_silindiğini_ve_veri_alanlarının_temizlendiğini_kontrol_eder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Verify the result screen is cleared by checking the absence of the specific element
        boolean isResultPresent;
        try {
            driver.findElement(By.xpath("//div[text()='Toplam Ödenecek Tutar']"));
            isResultPresent = true;
        } catch (NoSuchElementException e) {
            isResultPresent = false;
        }

        assertFalse("The result screen has not been cleared.", isResultPresent);

        // Verify the input fields are cleared
        WebElement aracTipiDropdown = driver.findElement(By.cssSelector("div[id='aracTipi']"));
        assertTrue(aracTipiDropdown.getText().contains("Otomobil - Kaptıkaçtı - Arazi Taşıtı ve Benzerleri"));

        WebElement aracYasiDropdown = driver.findElement(By.cssSelector("div[id='aracYasi']"));
        assertTrue(aracYasiDropdown.getText().contains("Seçiniz"));

        WebElement motorHacmiDropdown = driver.findElement(By.cssSelector("div[id='motorSilindirHacmi']"));
        assertTrue(motorHacmiDropdown.getText().contains("Seçiniz"));

        WebElement ilkTescilYiliDropdown = driver.findElement(By.cssSelector("div[id='ilkIktisap']"));
        assertTrue(ilkTescilYiliDropdown.getText().contains("2017 ve Öncesi"));

    }








}
