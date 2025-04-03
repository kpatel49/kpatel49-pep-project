package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Create a new message 
    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() ||
                message.getMessage_text().length() > 255 || message.getPosted_by() <= 0) {
                    return null;
        }
        return messageDAO.createMessage(message);
    }

    // Retrieve all messages
     public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Retrive a message by ID
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    // Delete a message by ID
    public Message deleteMessage(int messageId) {
        return messageDAO.deleteMessage(messageId);
    }

    // Update a message by ID
    public Message updateMessage(int messageId, Message updatedMessage) {
        if (updatedMessage.getMessage_text() == null || updatedMessage.getMessage_text().isBlank() || updatedMessage.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.updateMessage(messageId, updatedMessage.getMessage_text());
    }

    // Retrieve messages by user ID
    public List<Message> getMessagesByUser(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }
    
}
