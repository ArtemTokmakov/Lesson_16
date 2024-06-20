import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsBYTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void SetUp() {
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("http://mts.by");

        WebElement acceptCookies = driver.findElement(By.xpath("//button[@id='cookie-agree']"));
        acceptCookies.click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях 'Услуги связи'")
    public void emptyFieldsCommunicationServiceTest() {
        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'connection-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'connection-sum']"));
        WebElement emailInputField = driver.findElement(By.xpath("//input[@id = 'connection-email']"));

        assertEquals("Номер телефона", phoneInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("Сумма", sumInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("E-mail для отправки чека", emailInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях 'Домашний интернет'")
    public void emptyFieldsHomeInternetTest() {
        WebElement dropDownServices = driver.findElement(By.xpath("//button[@class = 'select__header']"));
        dropDownServices.click();
        WebElement homeInternet = driver.findElement(By.xpath
                ("//p[@class='select__option'][text()='Домашний интернет']"));
        homeInternet.click();

        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'internet-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'internet-sum']"));
        WebElement emailInputField = driver.findElement(By.xpath("//input[@id = 'internet-email']"));

        assertEquals("Номер абонента", phoneInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("Сумма", sumInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("E-mail для отправки чека", emailInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях 'Рассрочка'")
    public void emptyFieldsInstallmentTest() {
        WebElement dropDownServices = driver.findElement(By.xpath("//button[@class = 'select__header']"));
        dropDownServices.click();
        WebElement installment = driver.findElement(By.xpath("//p[@class='select__option'][text()='Рассрочка']"));
        installment.click();

        WebElement accountNumberInputField = driver.findElement(By.xpath("//input[@id = 'score-instalment']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'instalment-sum']"));
        WebElement emailInputField = driver.findElement(By.xpath("//input[@id = 'instalment-email']"));

        assertEquals("Номер счета на 44", accountNumberInputField.getAttribute("placeholder"),
                "Текст номера счета не совпадает с ожидаемым.");
        assertEquals("Сумма", sumInputField.getAttribute("placeholder"),
                "Текст суммы не совпадает с ожидаемым.");
        assertEquals("E-mail для отправки чека", emailInputField.getAttribute("placeholder"),
                "Текст email не совпадает с ожидаемым.");
    }

    @Test
    @DisplayName("Проверка надписей в незаполненных полях 'Задолженность'")
    public void emptyFieldsArrearsTest() {
        WebElement dropDownServices = driver.findElement(By.xpath("//button[@class = 'select__header']"));
        dropDownServices.click();
        WebElement arrears = driver.findElement(By.xpath("//p[@class='select__option'][text()='Задолженность']"));
        arrears.click();

        WebElement accountNumberInputField = driver.findElement(By.xpath("//input[@id = 'score-arrears']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'arrears-sum']"));
        WebElement emailInputField = driver.findElement(By.xpath("//input[@id = 'arrears-email']"));

        assertEquals("Номер счета на 2073", accountNumberInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("Сумма", sumInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
        assertEquals("E-mail для отправки чека", emailInputField.getAttribute("placeholder"),
                "Текст не совпадает с ожидаемым.");
    }

    @Test
    @DisplayName("Проверка корректности номера телефона в окне оплаты")
    public void PhoneNumberInPaymentWindowTest() {
        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'connection-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'connection-sum']"));
        WebElement continueButton = driver.findElement(By.cssSelector("#pay-connection .button__default"));

        phoneInputField.sendKeys("297777777");
        sumInputField.sendKeys("50");
        continueButton.click();

        WebElement iframe = driver.findElement(By.cssSelector("div.bepaid-app iframe"));
        driver.switchTo().frame(iframe);

        WebElement infoField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//span[@class = 'pay-description__text']")));

        String textInfoField = infoField.getText();
        String expectedNumber = "375297777777";

        assertTrue(textInfoField.contains(expectedNumber),
                "Текст в форме не соответствует номеру телефона : " + expectedNumber);
    }

    @Test
    @DisplayName("Проверка суммы в окне оплаты")
    public void testSumInPaymentWindow() {
        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'connection-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'connection-sum']"));
        WebElement continueButton = driver.findElement(By.cssSelector("#pay-connection .button__default"));

        phoneInputField.sendKeys("297777777");
        sumInputField.sendKeys("50");
        continueButton.click();

        WebElement iframe = driver.findElement(By.cssSelector("div.bepaid-app iframe"));
        driver.switchTo().frame(iframe);

        WebElement sumField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                ("//div[@class = 'pay-description__cost']")));
        String textSumField = sumField.getText();
        String expectedSum = "50.00";
        WebElement payButton = driver.findElement(By.xpath("//button[@class = 'colored disabled']"));
        String textPayButton = payButton.getText();
        String expectedSumInButton = "50.00";

        assertTrue(textSumField.contains(expectedSum),
                "Текст в форме не соответствует сумме : " + expectedSum);
        assertTrue(textPayButton.contains(expectedSumInButton),
                "Текст в форме не соответствует сумме : " + expectedSumInButton);
    }

    @Test
    @DisplayName("Проверка незаполненных полей в окне оплаты")
    public void testBlankFieldsInPaymentWindow() {
        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'connection-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'connection-sum']"));
        WebElement continueButton = driver.findElement(By.cssSelector("#pay-connection .button__default"));

        phoneInputField.sendKeys("297777777");
        sumInputField.sendKeys("50");
        continueButton.click();

        WebElement iframe = driver.findElement(By.cssSelector("div.bepaid-app iframe"));
        driver.switchTo().frame(iframe);

        WebElement inputCardNumber = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//label[text() = 'Номер карты']")));
        WebElement inputExpirationDate = driver.findElement(By.xpath("//label[text() = 'Срок действия']"));
        WebElement inputCVC = driver.findElement(By.xpath("//label[text() = 'CVC']"));
        WebElement inputHolderName = driver.findElement(By.xpath("//label[contains(text(), 'Имя держателя')]"));

        assertEquals("Номер карты", inputCardNumber.getText(), "Текст 'Номер карты' не совпадает");
        assertEquals("Срок действия", inputExpirationDate.getText(), "Текст 'Срок действия' не совпадает");
        assertEquals("CVC", inputCVC.getText(), "Текст 'CVC' не совпадает");
        assertEquals("Имя держателя (как на карте)", inputHolderName.getText(), "Текст 'Имя держателя' не совпадает");
    }

    @Test
    @DisplayName("Проверка иконок платёжных систем в окне оплаты")
    public void testPaymentSystemIconsInPaymentWindow() {
        WebElement phoneInputField = driver.findElement(By.xpath("//input[@id = 'connection-phone']"));
        WebElement sumInputField = driver.findElement(By.xpath("//input[@id = 'connection-sum']"));
        WebElement continueButton = driver.findElement(By.cssSelector("#pay-connection .button__default"));

        phoneInputField.sendKeys("297777777");
        sumInputField.sendKeys("50");
        continueButton.click();

        WebElement iframe = driver.findElement(By.cssSelector("div.bepaid-app iframe"));
        driver.switchTo().frame(iframe);

        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath("//div[contains(@class, 'cards-brands__container')]")));
        List<WebElement> images = container.findElements(By.tagName("img"));
        assertEquals(5, images.size(), "Количество изображений должно быть равно 5");
    }
}
