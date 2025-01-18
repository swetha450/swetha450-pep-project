package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDao {

    private Connection conn = ConnectionUtil.getConnection();

   
    public Message createMessage(Message message) {
        String query = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Ensure no null fields are passed
            if (message.getMessage_text() == null || message.getPosted_by() == 0) {
                System.err.println("Invalid message data: posted_by or message_text is null/invalid");
                return null;
            }
    
            pstmt.setInt(1, message.getPosted_by());
            pstmt.setString(2, message.getMessage_text());
            pstmt.setLong(3, message.getTime_posted_epoch());
    
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Insert failed, no rows affected.");
                return null;
            }
    
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                message.setMessage_id(generatedKeys.getInt(1));
            } else {
                System.err.println("Failed to retrieve generated key.");
                return null;
            }
    
            return message;
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            return null;
        }
    }
    

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM Message";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving messages: " + e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id) {
        String query = "SELECT * FROM Message WHERE message_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving message by ID: " + e.getMessage());
        }
        return null; // Return null if no message is found
    }
    

    public boolean deleteMessageById(int id) {
        String query = "DELETE FROM Message WHERE message_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Return true if a row was deleted, false otherwise
        } catch (SQLException e) {
            System.err.println("Error deleting message: " + e.getMessage());
            return false; // Return false if an error occurred
        }
    }
    

    public List<Message> getMessagesByUserId(int id) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM Message WHERE posted_by = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id); // Set the user ID in the query
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Create and add each message to the list
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving messages by user ID: " + e.getMessage());
        }
        return messages; // Return the list of messages (empty if none are found)
    }
    

    public Message updateMessage(int id, Message message) {
        String query = "UPDATE Message SET message_text = ? WHERE message_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, message.getMessage_text());
            pstmt.setInt(2, id);
    
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Fetch the updated message from the database to return
                return getMessageById(id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating message: " + e.getMessage());
        }
        return null; // Return null if update failed
    }
    
}
