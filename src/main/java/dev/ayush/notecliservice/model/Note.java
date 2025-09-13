package dev.ayush.notecliservice.model;

import java.time.LocalDateTime;

public class Note {

    private final int id;
    private final LocalDateTime createdAt;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    public Note(int id, LocalDateTime createdAt, String title, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
