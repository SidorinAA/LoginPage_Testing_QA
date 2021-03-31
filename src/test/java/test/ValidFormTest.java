package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.*;
import page.AccountPage;
import page.LoginPage;
import page.VerificationPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ValidFormTest {


    @Test
    @Order(1)
     void shouldCorrectedUserRegistered() throws SQLException {
        DataHelper.insertFakeUser();
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getLoginUser(), DataHelper.getPasswordUser());
        AccountPage accountPage = verificationPage.validVerify(DataHelper.getVerificationCode());
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
    static void cleanTable() {
        val codes = "DELETE FROM auth_codes";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStatCode = connect.prepareStatement(codes);
                val prepareStatCard = connect.prepareStatement(cards);
                val prepareStatUser = connect.prepareStatement(users);
        ) {
           prepareStatCode.executeUpdate(codes);
           prepareStatCard.executeUpdate(cards);
            prepareStatUser.executeUpdate(users);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


}
