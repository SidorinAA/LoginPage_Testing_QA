package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import page.AccountPage;
import page.LoginPage;
import page.VerificationPage;


import static com.codeborne.selenide.Selenide.open;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ValidFormTest {

    @BeforeAll
    static void setUpAll(){

        SelenideLogger.addListener("allure",new AllureSelenide());
    }

    @Test
    @Order(1)
    void shouldCorrectedUserRegistered() {
        val login = DataHelper.insertFakeUser(DataHelper.getPasswordUser());
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val verificationPage = loginPage.validLogin(login, DataHelper.getPasswordUser());
        val accountPage = verificationPage.validVerify(DataHelper.getVerificationCode(login));
        accountPage.checkIfVisible();
    }

    @Test
    @Order(2)
    void shouldEnteredInvalidLogin() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        loginPage.invalidLogin();

    }



    @Test
    @Order(3)
    void shouldEnteredInvalidPassword() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        loginPage.invalidPassword();

    }



    @Test
    @Order(4)
    void shouldEnteredInvalidVerifyCode() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getLoginUser(), DataHelper.getPasswordUser());
        AccountPage accountPage = verificationPage.invalidVerify();
        accountPage.checkError();

    }


    @AfterAll
    static void cleanBase() {
        DataHelper.cleanTable();
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

}
