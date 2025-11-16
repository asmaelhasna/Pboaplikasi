package com.siskema.gryffindor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Activity {
    private String id;
    private String name;
    private String organizerName; 
    private String date;
    private String location;
    private String description;
    private ActivityStatus status;
    private List<String> registeredParticipants; 
    public Activity(String name, String organizerName, String date, String location, String description) {
        this.id = UUID.randomUUID().toString(); 
        this.name = name;
        this.organizerName = organizerName;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = ActivityStatus.PENDING_APPROVAL; 
        this.registeredParticipants = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ActivityStatus getStatus() { return status; }
    public void setStatus(ActivityStatus status) { this.status = status; }
    public List<String> getRegisteredParticipants() { return registeredParticipants; }
    public void setRegisteredParticipants(List<String> registeredParticipants) { this.registeredParticipants = registeredParticipants; }

    public void addParticipant(String username) {
        if (!registeredParticipants.contains(username)) {
            registeredParticipants.add(username);
        }
    }
}