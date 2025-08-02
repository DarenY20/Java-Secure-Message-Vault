package com.securevault.beans;

import com.securevault.dao.MessageDAO;
import com.securevault.model.Message;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.List;

@ManagedBean(name = "messageBean")
@SessionScoped
public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private MessageDAO dao = new MessageDAO();
    private List<Message> messages;
    private Message selectedMessage;
    private String title;
    private String content;
    private String keyword;
    private String inputKeyword;

    @PostConstruct
    public void init() {
        loadMessages();
    }

    public void loadMessages() {
        messages = dao.findAll();
    }

    public String saveMessage() {
        try {
            Message msg = new Message();
            msg.setTitle(title);
            msg.setEncryptedContent(encrypt(content, keyword));
            msg.setKeywordHash(hashKeyword(keyword));
            dao.save(msg);
            title = content = keyword = null;
            loadMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String prepareToView() {
        this.inputKeyword = "";
        return "viewMessage.xhtml?faces-redirect=true";
    }

    public String deleteMessage() {
        dao.delete(selectedMessage);
        loadMessages();
        return null;
    }

    public String decryptContent() {
        try {
            if (selectedMessage != null && inputKeyword != null) {
                String inputHash = hashKeyword(inputKeyword);
                if (inputHash.equals(selectedMessage.getKeywordHash())) {
                    String decrypted = decrypt(selectedMessage.getEncryptedContent(), inputKeyword);
                    selectedMessage.setEncryptedContent(decrypted);
                } else {
                    // Display an error (you can use a FacesMessage)
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Incorrect keyword"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String encrypt(String strToEncrypt, String keyword) throws Exception {
        // Simplified encryption: in reality you should use AES with proper key handling
        return new String(java.util.Base64.getEncoder().encode(strToEncrypt.getBytes("UTF-8")));
    }

    private String decrypt(String strToDecrypt, String keyword) throws Exception {
        // Simplified decryption
        return new String(java.util.Base64.getDecoder().decode(strToDecrypt), "UTF-8");
    }

    private String hashKeyword(String keyword) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(keyword.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    // === Getters and Setters ===

    public List<Message> getMessages() {
        if (messages == null) {
            loadMessages();
        }
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Message selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInputKeyword() {
        return inputKeyword;
    }

    public void setInputKeyword(String inputKeyword) {
        this.inputKeyword = inputKeyword;
    }
}
