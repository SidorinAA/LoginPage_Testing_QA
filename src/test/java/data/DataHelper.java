package data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.sql.*;

@Data
public class DataHelper {


    public static String getLoginUser() {
        val getUser = "SELECT login FROM users WHERE login='petya';";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUser)) {
                if (resultSet.next()) {
                    val userLogin = resultSet.getString(1);

                    return userLogin;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String getPasswordUser() {
        return "123qwerty";
    }

    @Test
    void  insertFakeUser() throws SQLException {
        val faker = new Faker();
        val dataSQL = "INSERT INTO users(id, login, password, status) VALUES (?, ?, ?, ?);";

        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStmt = connect.prepareStatement(dataSQL);
        ) {
            prepareStmt.setString(1, faker.idNumber().valid());
            prepareStmt.setString(2, faker.name().firstName());
            prepareStmt.setString(3, faker.internet().password());
            prepareStmt.setString(4, "active");
            prepareStmt.executeUpdate();


        }
    }

    public static String getUserId() {
        val getUserId = "SELECT id FROM users WHERE login = 'petya';";
        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val createStmt = connect.createStatement();
        ) {
            try (val resultSet = createStmt.executeQuery(getUserId)) {
                if (resultSet.next()) {
                    val userId = resultSet.getString(1);
                    return userId;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String getVerificationCode() {
        val requestCode = "SELECT code FROM auth_codes WHERE user_id=? ORDER BY created DESC LIMIT 1;";

        try (
                val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                val prepareStmt = connect.prepareStatement(requestCode);
        ) {
            prepareStmt.setString(1, getUserId());
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val code = resultSet.getString(1);
                    return code;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }


    @Test
    void cleanTable() {

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
