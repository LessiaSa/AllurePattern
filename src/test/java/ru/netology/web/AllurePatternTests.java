
package ru.netology.web;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.web.DataGenerator.Registration.getUser;
import static ru.netology.web.DataGenerator.getRandomLogin;
import static ru.netology.web.DataGenerator.getRandomPassword;

class AllurePatternTests{

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        open("http://localhost:9999");
        var notRegisteredUser = getUser("active");
        $("[data-test-id = 'login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        open("http://localhost:9999");
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id = 'login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id = 'login'] input").setValue(wrongLogin);
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        open("http://localhost:9999");
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}


