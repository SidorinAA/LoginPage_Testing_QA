package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;


import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement titleError = $(withText("Ошибка"));


    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }


    public void invalidLogin(){
        loginField.setValue(DataHelper.getInvalidLoginUser());
        passwordField.setValue(DataHelper.getPasswordUser());
        loginButton.click();
        titleError.shouldBe(Condition.visible);
    }

    public void invalidPassword(){
        loginField.setValue(DataHelper.getLoginUser());
        passwordField.setValue(DataHelper.getInvalidPasswordUser());
        loginButton.click();
        titleError.shouldBe(Condition.visible);

    }

    public void error(){
        titleError.shouldBe(Condition.visible);
    }





}
