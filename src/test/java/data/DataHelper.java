package data;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.javafaker.Faker;
import lombok.val;



import java.sql.*;

public class DataHelper {


    public static String getLoginUser() {
        val getUser = "SELECT login FROM users INNER JOIN auth_codes a ON users.id = a.user_id WHERE created = (SELECT max(created) FROM auth_codes);";
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


    public static String insertFakeUser(String password) {
        val faker = new Faker();
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        val dataSQL = "INSERT INTO users(id, login, password, status) VALUES (?, ?, ?, ?);";
        try (val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             val prepareStmt = connect.prepareStatement(dataSQL)) {
            val login = faker.name().firstName();
            prepareStmt.setString(1, faker.idNumber().valid());
            prepareStmt.setString(2, login);
            prepareStmt.setString(3, bcryptHashString);
            prepareStmt.setString(4, "active");
            prepareStmt.executeUpdate();
            return login;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getUserId(Connection connect, String login) {
        val getUserId = "SELECT id FROM users WHERE login=?;";
        try (val prepareStmt = connect.prepareStatement(getUserId)) {
            prepareStmt.setString(1, login);
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val userId = resultSet.getString(1);
                    return userId;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String getVerificationCode(String login) {
        val requestCode = "SELECT code FROM auth_codes WHERE user_id=? ORDER BY created DESC LIMIT 1;";
        try (val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             val prepareStmt = connect.prepareStatement(requestCode)) {
            prepareStmt.setString(1, getUserId(connect, login));
            try (val resultSet = prepareStmt.executeQuery()) {
                if (resultSet.next()) {
                    val code = resultSet.getString(1);
                    return code;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static void cleanTable() {
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


    public static String getPasswordUser() {
        return "1234";
    }

    public static String getInvalidPasswordUser() {
        return "password";
    }

    public static String getInvalidLoginUser() {
        return "Andry";
    }

    public static String getInvalidCodeVerify() {
        return "12345";
    }






}
