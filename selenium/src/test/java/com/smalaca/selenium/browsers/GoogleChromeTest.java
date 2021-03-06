package com.smalaca.selenium.browsers;

import com.smalaca.selenium.properties.ConfigProperties;
import com.smalaca.selenium.properties.ConfigPropertiesFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class GoogleChromeTest {
    private final ConfigProperties properties = new ConfigPropertiesFactory().read();

    @Test
    void shouldFindResults() {
        System.setProperty("webdriver.chrome.driver", properties.getChromeWebDriverPath());

        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.google.com/");

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("let's talk about Java");
        search.submit();

        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(driver.findElements(By.className("g")).size()).isGreaterThan(0));

        driver.close();
    }
}