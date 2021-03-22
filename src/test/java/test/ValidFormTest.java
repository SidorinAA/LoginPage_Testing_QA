package test;


import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.AccountPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;


class ValidFormTest {


    @Test
    void shouldCorrectedUserRegistered(){
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getLoginUser(), DataHelper.getPasswordUser());
        AccountPage accountPage = verificationPage.validVerify(DataHelper.getVerificationCode());
        accountPage.checkIfVisible();

    }



}
