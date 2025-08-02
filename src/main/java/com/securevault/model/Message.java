package com.securevault.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "encrypted_message")
    private String encryptedContent;

    @Column(name = "keyword_hash")
    private String keywordHash;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEncryptedContent() { return encryptedContent; }
    public void setEncryptedContent(String encryptedContent) { this.encryptedContent = encryptedContent; }

    public String getKeywordHash() { return keywordHash; }
    public void setKeywordHash(String keywordHash) { this.keywordHash = keywordHash; }
}
