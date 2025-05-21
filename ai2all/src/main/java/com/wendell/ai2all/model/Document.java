package com.wendell.ai2all.model;

import com.pgvector.PGvector;
import jakarta.persistence.*;


@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(columnDefinition = "vector(768)")
    private PGvector embedding;

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public PGvector getEmbedding() {
        return embedding;
    }
    public void setEmbedding(PGvector embedding) {
        this.embedding = embedding;
    }

}