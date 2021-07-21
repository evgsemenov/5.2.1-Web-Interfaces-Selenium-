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
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldPositiveV1Test() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79990001122");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

    @Test
    void shouldNegativeIfEnglishNameTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Vasya");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7987999111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.className("input__sub")).getText();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText.trim(), actualText.trim());
    }

//    @Test
//    void shouldNegativeIfWrongTelephoneTest() {
//        driver.get("http://localhost:9999");
//        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Григорий Иванов-Янковский");
//        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+799900011225");
//        driver.findElement(By.className("checkbox__box")).click();
//        driver.findElement(By.className("button")).click();
//
////        String actualText =
//        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
//        assertEquals(expectedText.trim(), actualText.trim());
//    }

//    @Test
//    void shouldTest() {
//        driver.get("http://localhost:9999");
//        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Григорий");
//        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+7987999111");
//        }

}




