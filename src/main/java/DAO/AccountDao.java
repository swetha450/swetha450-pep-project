package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDao {

    private static Connection conn = ConnectionUtil.getConnection();

    public Account createAccount(Account account) {
        String query = "INSERT INTO Account (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setAccount_id(generatedKeys.getInt(1));
            }
            return account;
        } catch (SQLException e) {
            System.err.println("Error saving account: " + e.getMessage());
            return null;
        }
    }

    public Account getAccountByUsername(String username) {
        String query = "SELECT * FROM Account WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Account account = new Account();
            account.setAccount_id(rs.getInt("account_id"));
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
            return account;
        } catch (SQLException e) {
            System.err.println("Error retrieving account: " + e.getMessage());
            return null;
        }
    }

    public static Account getAccountById(int id) {
        String query = "SELECT * FROM Account WHERE account_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Account account = new Account();
            account.setAccount_id(rs.getInt("account_id"));
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password"));
            return account;
        } catch (SQLException e) {
            System.err.println("Error retrieving account: " + e.getMessage());
            return null;
        }
    }

    public Account createAccountAccount(Account account) {
        String query = "INSERT INTO Account (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
    
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return null; // Return null if no rows were affected
            }
    
            // Get the auto-generated ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setAccount_id(generatedKeys.getInt(1)); // Set the generated ID on the account object
            }
            return account; // Return the created account
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return null; // Return null if an exception occurs
        }
    }
    
}
