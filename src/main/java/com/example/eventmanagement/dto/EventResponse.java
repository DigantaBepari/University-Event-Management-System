package com.example.eventmanagement.dto;

public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String date;
    private Long categoryId;
    private Long organizerId;
    private long registrationCount;

    public EventResponse(Long id, String title, String description,
                         String location, String date,
                         Long categoryId, Long organizerId,
                         long registrationCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.categoryId = categoryId;
        this.organizerId = organizerId;
        this.registrationCount = registrationCount;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public long getRegistrationCount() {
        return registrationCount;
    }
}