package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    // Insert a new message 
    public Message createMessage(Message message){
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0){
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()){
                    message.setMessage_id(generatedKeys.getInt(1));
                    return message;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all messages 
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message";
        try (Connection conn = ConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    // Retrieve a message by its ID
    public Message getMessageById(int messageId) {
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete a message by its ID
    public Message deleteMessage(int messageId) {
        Message messageToDelete = getMessageById(messageId);
        if (messageToDelete != null) {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, messageId);
                stmt.executeUpdate();
                return messageToDelete;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Update a message by its ID
    public Message updateMessage(int messageId, String newText) {
        Message existingMessage = getMessageById(messageId);
        if (existingMessage != null && newText != null && !newText.isBlank() && newText.length() <= 255) {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            try (Connection conn = ConnectionUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newText);
                stmt.setInt(2, messageId);
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    existingMessage.setMessage_text(newText);
                    return existingMessage;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Retrieve all messages from a specific user
    public List<Message> getMessagesByUserId(int userId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message WHERE posted_by = ?";
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
