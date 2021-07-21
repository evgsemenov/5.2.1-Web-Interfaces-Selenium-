package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldPositiveTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79990001122");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIfEnglishLettersInNameTest() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Vasya");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7987999111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIfSymbolsInNameTest() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Василий!#$ @ндреевич*");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7987999111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIfEmptyFieldNameTest() {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7987999111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIfNoPlusSymbolInTelephoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("79990001122");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIf10NumbersInTelephoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7999000112");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIf12NumbersInTelephoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+799900011223");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockIfEmptyFieldTelephoneTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockFirstUncorrectFieldV1Test() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vasya");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockFirstUncorrectFieldV2Test() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вася");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("56448");
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input_invalid")).findElement(By.className("input__sub")).getText();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldBlockFirstUncorrectFieldV3Test() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вася");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("56448");
        driver.findElement(By.className("button")).click();

        // TODO: Сравнить цвет элемента agreement, если он не был нажат

    }
}



