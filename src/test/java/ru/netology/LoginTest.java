package ru.netology;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.LoginInfo;


import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.LoginRequest.*;

public class LoginTest {
    SelenideElement name = $("[data-test-id=login] .input__control");
    SelenideElement password = $("[data-test-id=password] .input__control");
    SelenideElement button = $("[data-test-id='action-login'] .button__text");

    @BeforeEach
    void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldLoginActive() {
        LoginInfo user = account("active");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldNotLoginBlocked() {
        LoginInfo user = account("blocked");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldShowErrorInvalidLogin() {
        LoginInfo user = account("active");
        name.setValue(user.getLogin() + "s");
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Ошибка")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldShowErrorInvalidPassword() {
        LoginInfo user = account("active");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword() + "s");
        button.submit();
        $(withText("Ошибка")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldChangeUserStatusToActive() {
        LoginInfo user = account("blocked");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindow();
        user.setStatus("active");
        createUser(user);
        open("http://localhost:9999/");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldChangeUserStatusToBlocked() {
        LoginInfo user = account("active");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindow();
        user.setStatus("blocked");
        createUser(user);
        open("http://localhost:9999/");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Test
    public void shouldChangeUserPassword() {
        LoginInfo user = account("active");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindow();
        user.setPassword(user.getPassword() + "5");
        createUser(user);
        open("http://localhost:9999/");
        name.setValue(user.getLogin());
        password.setValue(user.getPassword());
        button.submit();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

}
